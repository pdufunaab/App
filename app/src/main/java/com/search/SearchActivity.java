package com.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.oadex.app.R;

/**
 * Created by ADEKOYA759 on 16-Sep-16.
 */
public class SearchActivity extends Activity{
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private EditText mEditText;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_engine);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mEditText = (EditText)findViewById(R.id.search);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressStatus < 100){
                            progressStatus += 4;
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressStatus);
                                }
                            });
                            try {
                                Thread.sleep(300);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }
        });



    }
    class MyProgressBar extends ProgressBar{
        Context mContext;
        public  MyProgressBar(Context context){
            super(context);
            this.mContext = context;
        }

        @Override
        public void setProgress(int progress) {
            super.setProgress(progress);
            if (progress == this.getMax()){
                Intent intent = new Intent(mContext, SearchEngineActivity.class);
                startActivity(intent);
            }
        }
    }
}
