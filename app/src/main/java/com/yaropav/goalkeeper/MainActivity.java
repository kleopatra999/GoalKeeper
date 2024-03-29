package com.yaropav.goalkeeper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.viewpagerindicator.CirclePageIndicator;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;
import com.yaropav.goalkeeper.data.Day;
import com.yaropav.goalkeeper.fragments.AbstractDialog;
import com.yaropav.goalkeeper.fragments.AddNoteDialog;
import com.yaropav.goalkeeper.fragments.InfoDialog;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    public static final String CHAINS_PREF_KEY = "pref_key_chains";
    public static final String CHAIN_EXTRA = "current chain";
    public static final String REDRAW_INTENT = "com.yaropav.goalkeeper.redraw_chain";

    private TextView mChainHeader;
    private ViewPager mPager;
    private FloatingActionButton mAddFab, mDeleteFab, mCheckFab;
    private FloatingActionMenu mMenuFab;
    private CirclePageIndicator mPageIndicator;

    private ArrayList<Chain> mChains;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleAlarm();
        DataSerializer<Chain> serializer = new DataSerializer<>(this);
        mChains = serializer.loadList(Chain.class, CHAINS_PREF_KEY);
        verifyChains();
        setLayout();
        Chain currentChain = (Chain) getIntent().getSerializableExtra(CHAIN_EXTRA);
        if (currentChain != null) {
            mPager.setCurrentItem(mChains.indexOf(currentChain));
        }
    }

    private void verifyChains() {
        for (Chain chain : mChains) {
            int daysPassed = 0, tasksFailed = 0;
            ArrayList<Day> days = chain.getDays();
            if (days.isEmpty()) continue;
            Calendar firstWeekDay = Calendar.getInstance();
            firstWeekDay.set(Calendar.HOUR_OF_DAY, 0);
            firstWeekDay.clear(Calendar.MINUTE);
            firstWeekDay.clear(Calendar.SECOND);
            firstWeekDay.clear(Calendar.MILLISECOND);
            firstWeekDay.set(Calendar.DAY_OF_WEEK, firstWeekDay.getFirstDayOfWeek());
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -1);
            if (days.size() != 1 && days.size() > chain.getWeeklySkips())
                while (firstWeekDay.get(Calendar.DATE) != yesterday.get(Calendar.DATE)) {
                    yesterday.add(Calendar.DATE, -1);
                    daysPassed++;
                    int dayIndex = days.size() - daysPassed;
                    if (dayIndex >= 0 && !days.get(dayIndex).isCompleted()) tasksFailed++;
                    if (tasksFailed > chain.getWeeklySkips()) {
                        days.get(dayIndex).setNote(getString(R.string.note_failed));
                        chain.setFailed(true);
                        break;
                    }
                }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataSerializer<Chain> serializer = new DataSerializer<>(this);
        serializer.save(mChains, CHAINS_PREF_KEY);
    }

    private void setLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.darkPrimaryColor));
        }

        ChainPagerAdapter adapter = new ChainPagerAdapter(mChains, getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);
        View pagerIndicatorView = getLayoutInflater().inflate(R.layout.pager_indicator, null);
        mPageIndicator = (CirclePageIndicator) pagerIndicatorView.findViewById(R.id.page_indicator);
        mChainHeader = (TextView) pagerIndicatorView.findViewById(R.id.chain_name);
        mPageIndicator.setViewPager(mPager);

        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(pagerIndicatorView);

        mMenuFab = (FloatingActionMenu) findViewById(R.id.fam);
        mCheckFab = (FloatingActionButton) findViewById(R.id.check_day_fab);
        mAddFab = (FloatingActionButton) findViewById(R.id.add_chain_fab);
        mDeleteFab = (FloatingActionButton) findViewById(R.id.delete_chain_fab);
        if (mChains.isEmpty()) {
            mDeleteFab.setVisibility(View.GONE);
            mCheckFab.setVisibility(View.GONE);
        }
        mAddFab.setOnClickListener(this);
        mDeleteFab.setOnClickListener(this);
        mCheckFab.setOnClickListener(this);

        mPageIndicator.setOnPageChangeListener(new ChainChangeListener(mPager.getCurrentItem()));
    }

    private void scheduleAlarm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 5);
        calendar.add(Calendar.DATE, 1);
        Intent intent = new Intent(this, GoalCheckReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, broadcast);
    }

    public void updateHeader() {
        mChainHeader.setText(mChains.get(mPager.getCurrentItem()).getName().toUpperCase());
    }

    public void hideFloatingMenu() {
        mMenuFab.close(true);
    }

    private void createChain() {
        mMenuFab.close(true);
        ChainPagerAdapter adapter = (ChainPagerAdapter) mPager.getAdapter();
        mChains.add(new Chain(getString(R.string.default_chain_name)));
        adapter.notifyDataSetChanged();
        int index = mChains.size() - 1;
        mPager.setCurrentItem(index);
        mChainHeader.setText(mChains.get(index).getName().toUpperCase());
        mPageIndicator.requestLayout();
        mCheckFab.setVisibility(View.VISIBLE);
        mDeleteFab.setVisibility(View.VISIBLE);
        mPager.setVisibility(View.VISIBLE);
    }

    private void deleteChain() {
        mChains.remove(mPager.getCurrentItem());
        mPager.getAdapter().notifyDataSetChanged();
        mPageIndicator.requestLayout();
        if (mChains.isEmpty()) {
            mCheckFab.setVisibility(View.GONE);
            mDeleteFab.setVisibility(View.GONE);
            mCheckFab.setLabelVisibility(View.GONE);
            mDeleteFab.setLabelVisibility(View.GONE);
            mPager.setVisibility(View.GONE);
            mChainHeader.setText(R.string.no_chains);
        }
        mMenuFab.close(true);
    }

    private void checkDay() {
        Chain chain = mChains.get(mPager.getCurrentItem());
        Day day = chain.getDays().get(chain.getDays().size() - 1);
        day.setIsCompleted(true);
        Intent redrawIntent = new Intent(REDRAW_INTENT);
        redrawIntent.putExtra(CHAIN_EXTRA, chain);
        if (chain.isWantNotes()) {
            AbstractDialog.newInstance(day, AddNoteDialog.class)
                    .show(getSupportFragmentManager(), "add_note");
        } else {
            day.setNote(getString(R.string.default_note));
        }
        mPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_chain_fab:
                createChain();
                break;
            case R.id.delete_chain_fab:
                deleteChain();
                break;
            case R.id.check_day_fab:
                checkDay();
                break;
        }
    }

    private class ChainChangeListener implements ViewPager.OnPageChangeListener {

        public ChainChangeListener(int current) {
            onPageSelected(current);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mMenuFab.close(true);
        }

        @Override
        public void onPageSelected(int position) {
            if (mChains.isEmpty()) {
                mChainHeader.setText(getString(R.string.no_chains));
                return;
            }
            Chain chain = mChains.get(position);
            mChainHeader.setText(chain.getName().toUpperCase());
            ArrayList<Day> days = chain.getDays();
            if (!days.isEmpty() && days.get(days.size() - 1).isCompleted()) {
                mCheckFab.setEnabled(false);
            } else mCheckFab.setEnabled(true);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
