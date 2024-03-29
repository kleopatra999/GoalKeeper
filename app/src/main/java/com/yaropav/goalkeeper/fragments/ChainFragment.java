package com.yaropav.goalkeeper.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yaropav.goalkeeper.MainActivity;
import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.calendar.ChainView;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.Day;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by mrbimc on 04.04.2015.
 */
public class ChainFragment extends Fragment {

    private static final String CHAIN_KEY = "chain";

    private boolean mInEditText;
    private EditText mNameEdit;
    private InputMethodManager mInputManger;

    public static ChainFragment newInstance(Chain chain) {
        Bundle bundle = new Bundle();
        ChainFragment fragment = new ChainFragment();
        bundle.putSerializable(CHAIN_KEY, chain);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Chain mChain;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chain, container, false);

        mChain = (Chain) getArguments().getSerializable(CHAIN_KEY);

        mInputManger = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        LinearLayout holder = (LinearLayout) v.findViewById(R.id.chain_info_holder);
        mNameEdit = (EditText) v.findViewById(R.id.name_edittext);

        holder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((MainActivity) getActivity()).hideFloatingMenu();
                mInputManger.hideSoftInputFromWindow(mNameEdit.getWindowToken(), 0);
                return true;
            }
        });

        setAskForNote(v);
        setSeekbarPreference(v);
        setEditName(v);

        setChainView(v);

        return v;
    }

    private void setEditName(View v) {
        RelativeLayout holder = (RelativeLayout) v.findViewById(R.id.edit_text_holder);
        mNameEdit = (EditText) v.findViewById(R.id.name_edittext);
        if (mChain.isFailed()) {
            mNameEdit.setEnabled(false);
            holder.setEnabled(false);
        }

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNameEdit.requestFocus();
                mInputManger.showSoftInput(mNameEdit, InputMethodManager.SHOW_IMPLICIT);
                mNameEdit.setSelection(mNameEdit.getText().length());

            }
        });
        mNameEdit.setText(mChain.getName());
        mNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mInEditText) return;
                mInEditText = true;
                if (!s.toString().equals("")) mChain.setName(s.toString());
                mInEditText = false;
            }

            @Override
            public void afterTextChanged(Editable editable) {
                ((MainActivity) getActivity()).updateHeader();
            }
        });
    }

    private void setAskForNote(View v) {
        View holder = v.findViewById(R.id.checkbox_holder);
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox);
        if (mChain.isFailed()) {
            checkBox.setEnabled(false);
            holder.setEnabled(false);
        }

        checkBox.setChecked(mChain.isWantNotes());
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox.toggle();
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mChain.setWantNotes(isChecked);
            }
        });
    }

    private void setSeekbarPreference(View v) {
        SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekbar);
        if (mChain.isFailed()) seekBar.setEnabled(false);
        seekBar.setMax(6);
        seekBar.setProgress(mChain.getWeeklySkips());

        final TextView seekBarValue = (TextView) v.findViewById(R.id.seekbar_value);
        seekBarValue.setText(mChain.getWeeklySkips() + " days");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(progress + " days");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mChain.setWeeklySkips(seekBar.getProgress());
            }
        });
    }

    private void setChainView(View v) {
        TextView title = (TextView) v.findViewById(R.id.grid_title);

        ArrayList<Day> days = mChain.getDays();
        if(days != null && !days.isEmpty()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(days.get(0).getTimeStamp());
            String time = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(calendar.getTime());
            title.setText(title.getText() + " " + time);
        }
        else {title.setVisibility(View.GONE);}

        GridLayout chainHost = (GridLayout) v.findViewById(R.id.chain_view);
        new ChainView(chainHost, mChain, getActivity());
    }
}

