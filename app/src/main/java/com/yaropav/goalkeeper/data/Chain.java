package com.yaropav.goalkeeper.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Ярик on 04.04.2015.
 */
public class Chain implements Serializable {
    private boolean mIsFailed;
    private int mWeekAim;
    private int mCompleted;
    private String mName;
    private ArrayList<Note> mNotes;
    private Date mStartingDate;

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

    public int getCompleted() {
        return mCompleted;
    }

    public void setCompleted(int completed) {
        mCompleted = completed;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ArrayList<Note> getNotes() {
        return mNotes;
    }

    public void setNotes(ArrayList<Note> notes) {
        mNotes = notes;
    }

    public Date getStartingDate() {
        return mStartingDate;
    }

    public void setStartingDate(Date startingDate) {
        mStartingDate = startingDate;
    }
}
