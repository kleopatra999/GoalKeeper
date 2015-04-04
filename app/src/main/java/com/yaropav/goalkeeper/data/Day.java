package com.yaropav.goalkeeper.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Ярик on 04.04.2015.
 */
public class Day {
    private String mNote;
    private long mTimeStamp;
    private boolean mIsCompleted;

    public Day() { }

    public Day(String text, long timeStamp, boolean isCompleted) {
        mNote = text;
        mTimeStamp = timeStamp;
        mIsCompleted = isCompleted;
    }

    public boolean isCompleted() {
        return mIsCompleted;
    }

    public void setIsCompleted(boolean mIsCompleted) {
        this.mIsCompleted = mIsCompleted;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String text) {
        mNote = text;
    }

    public long getTimeStamp() { return mTimeStamp; }

    public void setmTimeStamp(long mTimeStamp) { this.mTimeStamp = mTimeStamp; }

}
