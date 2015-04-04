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

import com.viewpagerindicator.TitlePageIndicator;
import com.yaropav.goalkeeper.data.Chain;
import com.yaropav.goalkeeper.data.DataSerializer;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    public static final String CHAINS_PREF_KEY = "pref_key_chains";

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
        TitlePageIndicator pageIndicator = (TitlePageIndicator) pagerIndicatorView.findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(pager);
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

}
