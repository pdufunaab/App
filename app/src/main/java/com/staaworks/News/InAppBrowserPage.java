package com.staaworks.News;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.oadex.app.R;


/**
 * Created by Ahmad Alfawwaz on 8/9/2016.
 */
public class InAppBrowserPage extends Activity {
    private String URL;//"http://google.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_main);
        URL = getIntent().getExtras().getString("URL");

        WebView webview=(WebView)findViewById(R.id.webView);
        webview.getSettings().setJavaScriptEnabled(false);
        webview.loadUrl(URL);
        webview.setWebViewClient(new WebViewClient());

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*
    private class  MyWebViewClient extends WebViewClient {

            @SuppressWarnings("deprecation")
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

        }

    }
    */

}
