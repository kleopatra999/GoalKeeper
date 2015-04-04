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


        Log.d(getClass().getSimpleName(), "num of days in chain =" + days.size());

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

        cellTextView.setText("" + i + 1);

        holder.addView(cell);

        Log.d(getClass().getSimpleName(), "day i");

    }

}
