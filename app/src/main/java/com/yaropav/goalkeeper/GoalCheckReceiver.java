package com.yaropav.goalkeeper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;

import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;
import com.yaropav.goalkeeper.data.Day;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ярик on 04.04.2015.
 */
public class GoalCheckReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        DataSerializer<Chain> serializer = new DataSerializer<>(context);
        ArrayList<Chain> chains = serializer.loadList(Chain.class, "chains");
        if(chains != null && !chains.isEmpty()) { //todo skip failed chains
            for(Chain chain : chains) {
                ArrayList<Day> days = chain.getDays();
                Day today = days.get(days.size());

                if(!DateUtils.isToday(today.getDate().getTime())) {
                    today = new Day("You miserably failed this day", new Date(), false);
                }

                ArrayList<Day> week = new ArrayList<>();
                for (int i = 0; i < today.getDate().getDay(); i++ ) { week.add(days.get(days.size() - i)); }

                int allowedNumOfSkips = 7 - chain.getWeekAim();
                int failsThisWeek = 0;

                for(Day day : week) {
                    if(!day.isCompleted()) failsThisWeek++;
                }

                if(failsThisWeek > allowedNumOfSkips) chain.setFailed(true);
            }
        }
    }
}
