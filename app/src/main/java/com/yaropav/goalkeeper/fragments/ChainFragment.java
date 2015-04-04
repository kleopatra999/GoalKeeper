package com.yaropav.goalkeeper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.data.Chain;

/**
 * Created by Ярик on 04.04.2015.
 */
public class ChainFragment extends Fragment {

    private static final String CHAIN_KEY = "chain";

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
        View v = inflater.inflate(R.layout.fragment_addchain, container, false);

        return v;
    }
}
