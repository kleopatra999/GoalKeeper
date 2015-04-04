package com.yaropav.goalkeeper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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

        ((TextView) v.findViewById(R.id.checkbox_title)).setText(mChain.getName());
        ((CheckBox) v.findViewById(R.id.checkbox)).setChecked(true);

        ((TextView) v.findViewById(R.id.seekbar_title)).setText("Weekly goals");
        SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekbar);
        seekBar.setMax(7);

        EditText name = (EditText) v.findViewById(R.id.name_edittext);
        name.setText(mChain.getName());

        return v;
    }
}

