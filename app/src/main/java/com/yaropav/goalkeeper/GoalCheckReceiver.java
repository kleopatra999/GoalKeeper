package com.yaropav.goalkeeper;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.text.format.DateUtils;

import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;
import com.yaropav.goalkeeper.data.Day;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ярик on 04.04.2015.
 */
public class GoalCheckReceiver extends BroadcastReceiver {

    private static final int NOTIF_ID = 9775;

    @Override
    public void onReceive(Context context, Intent intent) {
        DataSerializer<Chain> serializer = new DataSerializer<>(context);
        ArrayList<Chain> chains = serializer.loadList(Chain.class, "chains");
        if(chains != null && !chains.isEmpty()) { //todo skip failed chains
            for(Chain chain : chains) {
                if (chain.isFailed()) continue;
                ArrayList<Day> days = chain.getDays();
                Day today = days.get(days.size());

                if(!DateUtils.isToday(today.getTimeStamp())) {
                    today = new Day("You miserably failed this day", Calendar.getInstance().getTimeInMillis(), false);
                    days.add(today);
                }

                ArrayList<Day> week = new ArrayList<>();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(today.getTimeStamp());
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); //todo probably it won't work for some locales
                for (int i = 0; i < dayOfWeek; i++ )  week.add(days.get(days.size() - i));

                int allowedNumOfSkips = 7 - chain.getWeeklySkips();
                int failsThisWeek = 0;

                for(Day day : week) {
                    if(!day.isCompleted()) failsThisWeek++;
                }

                if(failsThisWeek > allowedNumOfSkips) {
                    chain.setFailed(true);
                    notifyFail(context, chain);
                }
            }
        }
        serializer.save(chains, MainActivity.CHAINS_PREF_KEY);
    }

    private void notifyFail(Context context, Chain chain) {
        Intent toMain = new Intent(context, MainActivity.class);
        toMain.putExtra(MainActivity.CHAIN_EXTRA, chain);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, toMain, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(context.getString(R.string.chain_failed))
                .setContentText(context.getString(R.string.you_failed) + " " + chain.getName())
                .setContentIntent(contentIntent)
                .setSmallIcon(android.R.drawable.sym_def_app_icon);
        NotificationManager nm = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTIF_ID, builder.build());
    }
}
