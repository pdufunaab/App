package com.oadex.app;

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

    private static final String funaabNewsURL = "http://www.feedforall.com/sample.xml";

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentPagerAdapter mPagerAdapter;
    List<Fragment> fragments = new Vector<>();
    List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        mViewPager = (ViewPager)findViewById(R.id.viewpager);

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

    @Override
    public void onFragmentInteraction(Uri uri) {}



}
