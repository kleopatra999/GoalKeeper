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
public abstract class AbstractDialog extends DialogFragment {
    private static final String DAY_KEY = "day";

    public static <T extends AbstractDialog>AbstractDialog newInstance(Day day, Class<T> clazz) {
        Bundle bundle = new Bundle();
        AbstractDialog fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        bundle.putSerializable(DAY_KEY, day);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected Day mDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(getLayoutId(), null);
        mDay = (Day) getArguments().getSerializable(DAY_KEY);
        configureLayout(v);
        Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
        dialog.setContentView(v);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));

        return dialog;
    }

    protected abstract int getLayoutId();
    protected abstract void configureLayout(View v);
}
