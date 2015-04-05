package com.yaropav.goalkeeper.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by Ярик on 04.04.2015.
 */
public class Chain implements Serializable {

    private boolean mIsFailed;
    private int mWeeklySkips;
    private String mName;
    private ArrayList<Day> mDays;
    private boolean mWantNotes;

    public Chain() {
        mDays = new ArrayList<>();
    }
    public Chain(String name) {
        mName = name;
        mDays = new ArrayList<>();
        mDays.add(new Day(Calendar.getInstance().getTimeInMillis()));
    }

    public boolean isFailed() {
        return mIsFailed;
    }

    public void setFailed(boolean isFailed) {
        mIsFailed = isFailed;
    }

    public int getWeeklySkips() {
        return mWeeklySkips;
    }

    public void setWeeklySkips(int weeklySkips) {
        mWeeklySkips = weeklySkips;
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

    public boolean isWantNotes() {
        return mWantNotes;
    }

    public void setWantNotes(boolean wantNotes) {
        mWantNotes = wantNotes;
    }
}
