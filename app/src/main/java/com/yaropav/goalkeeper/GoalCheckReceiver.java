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
        ArrayList<Chain> chains = serializer.loadList(Chain.class, MainActivity.CHAINS_PREF_KEY);
        if(chains != null && !chains.isEmpty()) {
            for(Chain chain : chains) {
                /*if (chain.isFailed()) continue;

                ArrayList<Day> days = chain.getDays();
                Day today = days.get(days.size()-1);
                if(!DateUtils.isToday(today.getTimeStamp())) {
                    today = new Day("You miserably failed this day", Calendar.getInstance().getTimeInMillis(), false);
                    days.add(today);
                }

                ArrayList<Day> week = new ArrayList<>();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(today.getTimeStamp());
                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); //todo probably it won't work for some locales
                for (int i = 0; i < dayOfWeek; i++ )  week.add(days.get(days.size() - 1 - i));

                int allowedNumOfSkips = 7 - chain.getWeeklySkips();
                int failsThisWeek = 0;

                for(Day day : week) {
                    if(!day.isCompleted()) failsThisWeek++;
                }

                if(failsThisWeek > allowedNumOfSkips) {
                    chain.setFailed(true);
                    notifyFail(context, chain);
                }*/

                int daysPassed = 0, tasksFailed = 0;
                ArrayList<Day> days = chain.getDays();
                if (days.isEmpty()) continue;
                Calendar firstWeekDay = Calendar.getInstance();
                firstWeekDay.set(Calendar.HOUR_OF_DAY, 0);
                firstWeekDay.clear(Calendar.MINUTE);
                firstWeekDay.clear(Calendar.SECOND);
                firstWeekDay.clear(Calendar.MILLISECOND);
                firstWeekDay.set(Calendar.DAY_OF_WEEK, firstWeekDay.getFirstDayOfWeek());
                Calendar yesterday = Calendar.getInstance();
                yesterday.add(Calendar.DATE, -1);
                while(firstWeekDay.get(Calendar.DATE) != yesterday.get(Calendar.DATE)) {
                    yesterday.add(Calendar.DATE, -1);
                    daysPassed++;
                    int dayIndex = days.size()-daysPassed;
                    if(dayIndex >= 0 && !days.get(dayIndex).isCompleted()) tasksFailed++;
                    if (tasksFailed > chain.getWeeklySkips()) {
                        days.get(dayIndex).setNote(context.getString(R.string.note_failed));
                        chain.setFailed(true);
                        notifyFail(context, chain);
                        break;
                    }
                }
            }
            Calendar today = Calendar.getInstance();
            for(int i = 0; i < chains.size(); i++) {
                chains.get(i).getDays().add(new Day(today.getTimeInMillis()));
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
