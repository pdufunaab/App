package com.staaworks.news;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.oadex.app.R;
import com.staaworks.storage.FeedDBA;
import com.staaworks.util.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class NewsDisplayActivity extends AppCompatActivity implements NewsFragment.OnFragmentInteractionListener {


    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FragmentPagerAdapter mPagerAdapter;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new Vector<>();
    private FeedDBA.Categories category;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_display);

        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        Bundle arguments = getIntent().getExtras();
        category = (FeedDBA.Categories) arguments.get("Category");

        Bundle args = new Bundle();
        args.putBundle("extras", arguments);


        if (category != null) {
            titles.add(category.getTabTitle());
            System.out.println(category.getTabTitle());
        }
        else titles.add("NEWS");


        Fragment FeedDisplayPage = Fragment.instantiate(this, NewsFragment.class.getName(), args);
        fragments.add(FeedDisplayPage);

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
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}


}
