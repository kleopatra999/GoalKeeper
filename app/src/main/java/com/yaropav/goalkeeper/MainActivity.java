package com.yaropav.goalkeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;
import com.yaropav.goalkeeper.fragments.AddChainFragment;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements AddChainFragment.OnChainCreatedListener,
        View.OnClickListener {

    public static final String CHAINS_PREF_KEY = "pref_key_chains";
    public static final String CHAIN_EXTRA = "current chain";

    private TextView mChainHeader;
    private ViewPager mPager;
    private FloatingActionMenu mFloatingMenu;
    private CirclePageIndicator mPageIndicator;
    private LinearLayout mHolder;

    private ArrayList<Chain> mChains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleAlarm();

        DataSerializer<Chain> serializer = new DataSerializer<>(this);
        mChains = serializer.loadList(Chain.class, CHAINS_PREF_KEY);
        for (int i = 0; i < 5; i++) {
            mChains.add(new Chain("Chain " + i));
        }
        setLayout();

        Chain currentChain = (Chain) getIntent().getSerializableExtra(CHAIN_EXTRA);
        if (currentChain != null) {
            mPager.setCurrentItem(mChains.indexOf(currentChain));
        }
    }

    private void setLayout() {
        ChainPagerAdapter adapter = new ChainPagerAdapter(mChains, getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);
        View pagerIndicatorView = getLayoutInflater().inflate(R.layout.pager_indicator, null);
        mPageIndicator = (CirclePageIndicator) pagerIndicatorView.findViewById(R.id.page_indicator);
        mChainHeader = (TextView) pagerIndicatorView.findViewById(R.id.chain_name);
        mPageIndicator.setViewPager(mPager);
        mPageIndicator.setOnPageChangeListener(new ChainChangeListener(mPager.getCurrentItem()));

        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(pagerIndicatorView);

        mFloatingMenu = (FloatingActionMenu) findViewById(R.id.fam);
        FloatingActionButton checkFab, addFab, deleteFab;
        checkFab = (FloatingActionButton) findViewById(R.id.check_day_fab);
        addFab = (FloatingActionButton) findViewById(R.id.add_chain_fab);
        deleteFab = (FloatingActionButton) findViewById(R.id.delete_chain_fab);
        if (mChains.isEmpty()) {
            deleteFab.setEnabled(false);
            checkFab.setEnabled(false);
        }
        mHolder = (LinearLayout) findViewById(R.id.holder);
        addFab.setOnClickListener(this);
    }

    private void scheduleAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        Intent intent = new Intent(this, GoalCheckReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                broadcast);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onChainCreated() {
        ChainPagerAdapter adapter = (ChainPagerAdapter)mPager.getAdapter();
        mChains.add(new Chain("My awesome chain"));
        adapter.notifyDataSetChanged();
        int index = mChains.size()-1;
        mPager.setCurrentItem(index);
        mChainHeader.setText(mChains.get(index).getName().toUpperCase());
        getSupportActionBar().getCustomView().invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_chain_fab:
                onChainCreated();
                break;
        }
    }

    private class ChainChangeListener implements ViewPager.OnPageChangeListener {

        public ChainChangeListener(int current) {
            onPageSelected(current);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mChainHeader.setText(position < mChains.size()
                    ? mChains.get(position).getName().toUpperCase() : "No chains");
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
