package com.staaworks.customui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.oadex.app.R;

/**
 * Created by Ahmad Alfawwaz on 9/29/2016
 */
public class LoadingCircle extends RelativeLayout {

    private View root;
    ProgressBar bar;
    public LoadingCircle(Context context) {
        super(context);
        initialize(context);
    }

    public LoadingCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        root = inflate(context, R.layout.loading_circle, this);
        bar = (ProgressBar) root.findViewById(R.id.progressBar);
        bar.setVisibility(VISIBLE);
    }

    @Override
    public void setVisibility(int v) {
        super.setVisibility(v);
        bar.setVisibility(v);
    }


    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }


    public void dismiss() {
        setVisibility(INVISIBLE);
    }


    public void show() {
        setVisibility(VISIBLE);
    }
}
