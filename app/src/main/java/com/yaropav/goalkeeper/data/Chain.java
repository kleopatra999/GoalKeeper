package com.yaropav.goalkeeper.data;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ярик on 04.04.2015.
 */
public class Chain {
    private boolean mIsFailed;
    private int mWeekAim;
    private String mName;
    private ArrayList<Day> mDays;

    public boolean isFailed() {
        return mIsFailed;
    }

    public void setFailed(boolean isFailed) {
        mIsFailed = isFailed;
    }

    public int getWeekAim() {
        return mWeekAim;
    }

    public void setWeekAim(int weekAim) {
        mWeekAim = weekAim;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Day> getDays() {
        ArrayList<Day> days = mDays == null ? new ArrayList<Day>() : mDays;
        return days;
    }

    public void markThisDay(Day day){
        getDays().add(day);
    }
}
