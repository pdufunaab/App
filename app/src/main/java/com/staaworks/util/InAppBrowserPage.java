package com.staaworks.util;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.oadex.app.R;


/**
 * Created by Ahmad Alfawwaz on 8/9/2016
 */
public class InAppBrowserPage extends AppCompatActivity {
    private WebView webview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);
        String URL = getIntent().getExtras().getString("URL");

        initWebView();
        setWebViewClient();
        loadUrl(URL);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

    }



    private void initWebView() {
        webview = (WebView)findViewById(R.id.webView);
    }

    private void setWebViewClient() {

        InAppBrowserPageClient client = new InAppBrowserPageClient(webview);

        if (webview != null) {
            webview.setWebViewClient(client);
        }
        else {
            Toast.makeText(this, "Error Loading Page", Toast.LENGTH_LONG).show();
        }

        webview.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onReceivedTitle(WebView view, String title) {
                getWindow().setTitle(title);
            }
        });

    }

    private void loadUrl(String URL) {
        webview.loadUrl(URL);
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

    @Override
    public void onBackPressed() {
        if (webview.canGoBack()) {
            webview.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
