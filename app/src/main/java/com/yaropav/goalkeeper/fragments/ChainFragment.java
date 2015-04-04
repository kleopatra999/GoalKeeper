package com.yaropav.goalkeeper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.data.Chain;

/**
 * Created by mrbimc on 04.04.2015.
 */
public class ChainFragment extends Fragment {

    private static final String CHAIN_KEY = "chain";

    public static Fragment newInstance(Chain chain) {
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

        setAskForNote(v);
        setSeekbarPreference(v);
        setEditName(v);

        return v;
    }

    private void setEditName(View v) {
        EditText name = (EditText) v.findViewById(R.id.name_edittext);
        name.setText(mChain.getName());
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    private void setAskForNote(View v) {
        final CheckBox checkBox = (CheckBox) v.findViewById(R.id.checkbox);
        View holder = v.findViewById(R.id.checkbox_holder);
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
        seekBar.setMax(6);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });

        final TextView seekBarValue = (TextView) v.findViewById(R.id.seekbar_value);
        seekBarValue.setText(mChain.getWeekAim() + " days");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValue.setText(progress + " days");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mChain.setWeekAim(seekBar.getProgress());
            }
        });
    }
}

