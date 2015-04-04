package com.yaropav.goalkeeper.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.yaropav.goalkeeper.R;

/**
 * Created by Ярик on 04.04.2015.
 */
public class AddChainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_addchain, container, false);
        ImageButton addButton = (ImageButton) v.findViewById(R.id.add_chain);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Add", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
