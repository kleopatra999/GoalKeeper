package com.yaropav.goalkeeper.calendar;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yaropav.goalkeeper.MainActivity;
import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;
import com.yaropav.goalkeeper.data.Day;

import java.util.ArrayList;

/**
 * Created by mrbimc on 05.04.15.
 */
public class ChainView {

    GridLayout holder;
    Chain chain;
    Context context;

    public ChainView(GridLayout holder, Chain chain, Context context) {
        this.holder = holder;
        this.chain = chain;
        this.context = context;

        ArrayList<Day> days = chain.getDays();

        int counter = 0;

        for (int i = 0; i < days.size(); i++) {
            if (counter == 0) {
                //draw left edge
            } else if (counter == 6) {
                // draw right edge
                counter = 0;
            }

            drawDay(i);

            counter++;
        }
    }

    void drawDay(int i) {
        LinearLayout cell = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.chain_cell, holder, false);

        TextView cellTextView = (TextView) cell.findViewById(R.id.cell);

        int indicator = i+1;
        cellTextView.setText("" + indicator);

        boolean isCompleted = chain.getDays().get(i).isCompleted();
        if(isCompleted) cellTextView.setBackgroundResource(R.drawable.circle_bg_green);
        else cellTextView.setBackgroundResource(R.drawable.circle_bg_red);

        holder.addView(cell);

        Log.d(getClass().getSimpleName(), "day # i");

    }

}
