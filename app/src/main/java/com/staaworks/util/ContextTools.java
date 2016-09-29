package com.staaworks.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.oadex.app.NewsActivity;

/**
 * Created by Ahmad Alfawwaz on 9/29/2016
 */
public class ContextTools {

    private enum constants {
        newsSP("staaNSP"),
        eventsSP("staaESP"),
        eventAttSP("staaEASP");

        protected String value;
        constants(String value) {
            this.value = value;
        }

    }

    public static Context applicationContext = NewsActivity.getAppContext();

    public static SharedPreferences newsCategorySharedPreferences = NewsActivity.getCategoriesSharedPreferences();

    public static SharedPreferences newsSharedPreferences = applicationContext.getSharedPreferences(constants.newsSP.value, Context.MODE_PRIVATE);

    public static SharedPreferences eventsSharedPreferences = applicationContext.getSharedPreferences(constants.eventsSP.value, Context.MODE_PRIVATE);

    public static SharedPreferences eventAttendeeSharedPreferences = applicationContext.getSharedPreferences(constants.eventAttSP.value, Context.MODE_PRIVATE);
}
