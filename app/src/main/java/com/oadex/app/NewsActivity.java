package com.oadex.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.staaworks.events.EventFragment;
import com.staaworks.news.NewsCategorySelectionFragment;
import com.staaworks.util.FragmentAdapter;
import com.staaworks.news.NewsFragment;


import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NewsActivity extends AppCompatActivity

        //Connected Fragments
        implements
        NewsCategorySelectionFragment.OnFragmentInteractionListener,
            NewsFragment.OnFragmentInteractionListener,
            EventFragment.OnFragmentInteractionListener {

    private static final String funaabNewsURL = "http://rss.cnn.com/rss/edition.rss";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private static Context appContext;
    private FragmentPagerAdapter mPagerAdapter;
    List<Fragment> fragments = new Vector<>();
    List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        appContext = getApplicationContext();

        // News Bundle
        Bundle newsBundle = new Bundle();
        newsBundle.putString("urlString", funaabNewsURL);

        //Events Bundle for instantiation of events
        Bundle eventsBundle = new Bundle();
        eventsBundle.putString("eventName", "My Sample Event Name");


        Fragment newsFragment = Fragment.instantiate(this, NewsCategorySelectionFragment.class.getName(),newsBundle);
        Fragment eventsFragment = Fragment.instantiate(this, EventFragment.class.getName(), eventsBundle);

        fragments.add(newsFragment); titles.add("NEWS");
        fragments.add(eventsFragment); titles.add("EVENTS");
        this.mPagerAdapter  = new FragmentAdapter(super.getSupportFragmentManager(), fragments, titles);

        mViewPager.setAdapter(this.mPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);


        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });



        if (mViewPager != null && mTabLayout != null){
            mTabLayout.setupWithViewPager(mViewPager);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static SharedPreferences getCategoriesSharedPreferences() {
        return appContext.getSharedPreferences(constants.spname.value, Context.MODE_PRIVATE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

    private enum constants {
        spname("staaCatSP");


        public final String value;
        constants(String value) {
            this.value = value;
        }
    }


}
