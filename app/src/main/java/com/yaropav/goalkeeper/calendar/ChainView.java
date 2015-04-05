package com.yaropav.goalkeeper.calendar;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.Day;
import com.yaropav.goalkeeper.fragments.AbstractDialog;
import com.yaropav.goalkeeper.fragments.InfoDialog;

import java.util.ArrayList;

/**
 * Created by mrbimc on 05.04.15.
 */
public class ChainView implements View.OnClickListener {

    private GridLayout mHolder;
    private Chain mChain;
    private Context mContext;

    public ChainView(GridLayout holder, Chain chain, Context context) {
        mHolder = holder;
        mChain = chain;
        mContext = context;
        draw();
    }

    private void draw() {
        ArrayList<Day> days = mChain.getDays();
        if (days.isEmpty()) return;
        for (int i = 0; i < days.size()-1; i++) {
            drawDay(i);
        }
        drawToday(days.get(days.size()-1));
    }

    private void drawToday(Day day) {
        LinearLayout cell = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.chain_cell, mHolder, false);
        TextView cellTextView = (TextView) cell.findViewById(R.id.cell);
        cellTextView.setText(String.valueOf(mChain.getDays().size()));
        if (day.isCompleted()) {
            cellTextView.setBackgroundResource(R.drawable.circle_bg_green);
            cell.setTag(mChain.getDays().size()-1);
            cell.setOnClickListener(this);
        } else cellTextView.setBackgroundResource(R.drawable.circle_bg_today);
        mHolder.addView(cell);
    }

    private void drawDay(int i) {
        LinearLayout cell = (LinearLayout) LayoutInflater.from(mContext)
                .inflate(R.layout.chain_cell, mHolder, false);
        TextView cellTextView = (TextView) cell.findViewById(R.id.cell);
        cell.setOnClickListener(this);
        int indicator = i+1;
        cell.setTag(i);
        cellTextView.setText(String.valueOf(indicator));
        boolean isCompleted = mChain.getDays().get(i).isCompleted();
        if(isCompleted) cellTextView.setBackgroundResource(R.drawable.circle_bg_green);
        else cellTextView.setBackgroundResource(R.drawable.circle_bg_red);
        mHolder.addView(cell);
    }

    @Override
    public void onClick(View view) {
        Day day = mChain.getDays().get((Integer) view.getTag());
        AbstractDialog.newInstance(day, InfoDialog.class)
                .show(((ActionBarActivity) mContext).getSupportFragmentManager(), "info");
    }
}
