package com.staaworks.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by Ahmad Alfawwaz on 8/17/2016.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    public static int position = 0;

    private List<Fragment> fragments;




    public FragmentAdapter(FragmentManager fm, List<Fragment> myFrags) {
        super(fm);
        fragments = myFrags;
    }


    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }





    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);

        String PageTitle = "";

        switch(position)
        {
            case 0:
                PageTitle = "News";
                break;
            case 1:
                PageTitle = "Events";
                break;
        }
        return PageTitle;
    }


    public static int getPos() {
        return position;
    }

    public static void setPos(int pos) {
        FragmentAdapter.position = pos;
    }
}
