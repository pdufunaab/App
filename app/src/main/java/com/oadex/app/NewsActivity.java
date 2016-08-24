package com.oadex.app;

import android.support.design.widget.TabLayout;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import com.staaworks.Events.EventFragment;
import com.staaworks.util.FragmentAdapter;
import com.staaworks.News.NewsFragment;


import java.util.List;
import java.util.Vector;

public class NewsActivity extends AppCompatActivity

        //Implementations
        implements
            NewsFragment.OnFragmentInteractionListener,
            EventFragment.OnFragmentInteractionListener {

    private static final String funaabNewsURL = "http://feeds.nytimes.com/nyt/rss/HomePage";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentPagerAdapter mPagerAdapter;
    List<Fragment> fragments = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        mViewPager = (ViewPager)findViewById(R.id.viewpager);

        Bundle newsBundle = new Bundle();
        newsBundle.putString("urlString", funaabNewsURL);


        // **TODO    *Remove Event Name*    **//

        Bundle eventsBundle = new Bundle();
        eventsBundle.putString("eventName", "My Sample Event Name");

        fragments.add(Fragment.instantiate(this, NewsFragment.class.getName(),newsBundle));
        fragments.add(Fragment.instantiate(this, EventFragment.class.getName(), eventsBundle));

        this.mPagerAdapter  = new FragmentAdapter(super.getSupportFragmentManager(), fragments);

        mViewPager.setAdapter(this.mPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);


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
