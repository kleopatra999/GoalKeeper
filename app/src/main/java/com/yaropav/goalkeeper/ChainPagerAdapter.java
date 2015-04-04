package com.yaropav.goalkeeper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.fragments.ChainFragment;

import java.util.ArrayList;

/**
 * Created by Ярик on 04.04.2015.
 */
public class ChainPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Chain> mData;

    public ChainPagerAdapter(ArrayList<Chain> data, FragmentManager fm) {
        super(fm);
        mData = data;
    }

    @Override
    public Fragment getItem(int position) {
        return ChainFragment.newInstance(mData.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
