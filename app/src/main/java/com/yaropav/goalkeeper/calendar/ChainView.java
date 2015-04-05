package com.yaropav.goalkeeper.calendar;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.yaropav.goalkeeper.MainActivity;
import com.yaropav.goalkeeper.R;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;
import com.yaropav.goalkeeper.data.Day;
import com.yaropav.goalkeeper.fragments.InfoDialog;

import java.util.ArrayList;

/**
 * Created by mrbimc on 05.04.15.
 */
public class ChainView implements View.OnClickListener {

    private GridLayout holder;
    private Chain chain;
    private Context context;

    public ChainView(GridLayout holder, Chain chain, Context context) {
        this.holder = holder;
        this.chain = chain;
        this.context = context;

        ArrayList<Day> days = chain.getDays();

        for (int i = 0; i < days.size(); i++) {
            drawDay(i);
        }

        
    }

    void drawDay(int i) {
        LinearLayout cell = (LinearLayout) LayoutInflater.from(context)
                .inflate(R.layout.chain_cell, holder, false);
        TextView cellTextView = (TextView) cell.findViewById(R.id.cell);
        cell.setOnClickListener(this);
        int indicator = i+1;
        cell.setTag(i);
        cellTextView.setText("" + indicator);
        boolean isCompleted = chain.getDays().get(i).isCompleted();
        if(isCompleted) cellTextView.setBackgroundResource(R.drawable.circle_bg_green);
        else cellTextView.setBackgroundResource(R.drawable.circle_bg_red);
        holder.addView(cell);
    }

    @Override
    public void onClick(View view) {
        Day day = chain.getDays().get((Integer) view.getTag());
        InfoDialog.newInstance(day)
                .show(((ActionBarActivity) context).getSupportFragmentManager(), "info");
    }
}
