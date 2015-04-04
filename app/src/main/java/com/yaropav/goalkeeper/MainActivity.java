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
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    public static final String CHAINS_PREF_KEY = "pref_key_chains";

    private TextView mChainHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleAlarm();

        DataSerializer<Chain> serializer = new DataSerializer<>(this);
        ArrayList<Chain> chains = serializer.loadList(Chain.class, CHAINS_PREF_KEY);
        for (int i = 0; i < 4; i++) {
            chains.add(new Chain("Chain " + i));
        }
        ChainPagerAdapter adapter = new ChainPagerAdapter(chains, getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        View pagerIndicatorView = getLayoutInflater().inflate(R.layout.pager_indicator, null);
        CirclePageIndicator pageIndicator = (CirclePageIndicator) pagerIndicatorView.findViewById(R.id.page_indicator);
        mChainHeader = (TextView) pagerIndicatorView.findViewById(R.id.chain_name);
        pageIndicator.setViewPager(pager);
        pageIndicator.setOnPageChangeListener(new ChainChangeListener(chains, pager.getCurrentItem()));
        setSupportActionBar((Toolbar) findViewById(R.id.app_bar));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(pagerIndicatorView);

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

    private class ChainChangeListener implements ViewPager.OnPageChangeListener {

        private ArrayList<Chain> mData;

        public ChainChangeListener(ArrayList<Chain> data, int current) {
            mData = data;
            onPageSelected(current);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mChainHeader.setText(position < mData.size()
                    ? mData.get(position).getName() : "ADD CHAIN");
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

}
