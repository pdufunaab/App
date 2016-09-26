package com.staaworks.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.oadex.app.NewsActivity;

/**
 * Created by Ahmad Alfawwaz on 9/26/2016
 */
public class Network {

    private static Context context = NewsActivity.getAppContext();

    public static boolean isConnected() {

        if (context != null) {

            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();

        }
        return false;
    }
}
