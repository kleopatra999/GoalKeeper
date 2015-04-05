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
public class InfoDialog extends DialogFragment {

    private static final String DAY_KEY = "day";

    public static InfoDialog newInstance(Day day) {
        Bundle bundle = new Bundle();
        InfoDialog fragment = new InfoDialog();
        bundle.putSerializable(DAY_KEY, day);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Day mDay;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog, null);
        mDay = (Day) getArguments().getSerializable(DAY_KEY);
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mDay.getTimeStamp());
        ((TextView) v.findViewById(R.id.date)).setText(formatter.format(calendar.getTime()));
        if (!mDay.getNote().equals(""))
            ((TextView) v.findViewById(R.id.note)).setText(mDay.getNote());
        Dialog dialog = new Dialog(getActivity(), R.style.FullHeightDialog);
        dialog.setContentView(v);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.dialog_background));

        return dialog;
    }
}
