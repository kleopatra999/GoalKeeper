package com.yaropav.goalkeeper.data;

import java.util.Date;

/**
 * Created by Ярик on 04.04.2015.
 */
public class Note {
    private String mText;
    private Date mDate;
    public Note(String text, Date date) {
        mText = text;
        mDate = date;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }
}
