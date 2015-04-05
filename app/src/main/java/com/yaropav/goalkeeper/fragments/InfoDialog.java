package com.yaropav.goalkeeper.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.TextView;

import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.data.Day;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Ярик on 05.04.2015.
 */
public class InfoDialog extends AbstractDialog {

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_info;
    }

    @Override
    protected void configureLayout(View v) {
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mDay.getTimeStamp());
        ((TextView) v.findViewById(R.id.date)).setText(formatter.format(calendar.getTime()));
        if (!mDay.getNote().equals(""))
            ((TextView) v.findViewById(R.id.note)).setText(mDay.getNote());
    }
}
