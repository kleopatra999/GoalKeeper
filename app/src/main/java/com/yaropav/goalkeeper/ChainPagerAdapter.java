package com.yaropav.goalkeeper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.fragments.AddChainFragment;
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
    public CharSequence getPageTitle(int position) {
        return position < mData.size()
                ? mData.get(position).getName().toUpperCase() : "ADD CHAIN";
    }

    @Override
    public Fragment getItem(int position) {
        return position < mData.size()
                ? ChainFragment.newInstance(mData.get(position)) : new AddChainFragment();
    }

    @Override
    public int getCount() {
        return mData.size()+1;
    }
}
