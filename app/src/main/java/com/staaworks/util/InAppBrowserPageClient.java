package com.staaworks.util;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oadex.app.R;
import com.staaworks.customui.LoadingCircle;

/**
 * Created by Ahmad Alfawwaz on 9/29/2016
 */
public class InAppBrowserPageClient extends WebViewClient {

    boolean loadingFinished = true;
    boolean redirect = false;
    private WebSettings settings;
    private WebView webView;
    private LoadingCircle loadingCircle;


    public InAppBrowserPageClient(@NonNull WebView webView) {

        loadingCircle = (LoadingCircle) webView.getRootView().findViewById(R.id.loading_circle);
        this.webView = webView;
        settings = webView.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDisplayZoomControls(false);

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
        if (!loadingFinished) {
            redirect = true;
        }

        loadingFinished = false;
        webView.loadUrl(urlNewString);
        return true;
    }

    @Override
    public void onPageStarted(
            WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        loadingFinished = false;
        view.setVisibility(View.INVISIBLE);
        loadingCircle.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(!redirect){
            loadingFinished = true;
        }

        if(loadingFinished && !redirect){
            loadingCircle.setVisibility(View.INVISIBLE);
            view.setVisibility(View.VISIBLE);
        } else{
            redirect = false;
        }
    }

}
