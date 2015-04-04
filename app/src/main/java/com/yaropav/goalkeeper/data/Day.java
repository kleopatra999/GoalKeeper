package com.yaropav.goalkeeper.data;

import java.util.Date;

/**
 * Created by Ярик on 04.04.2015.
 */
public class Day {
    private String mNote;
    private Date mDate;
    private boolean mIsCompleted;
    public Day(String text, Date date, boolean isCompleted) {
        mNote = text;
        mDate = date;
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

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
