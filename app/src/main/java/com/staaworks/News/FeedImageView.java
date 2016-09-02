package com.staaworks.news;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.oadex.app.R;
import com.squareup.picasso.Picasso;

public class FeedImageView extends Activity {

    String imageURL;
    String imageTitle;
    ImageView imageView;
    TextView imageLinkTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_image_view);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageLinkTextView = (TextView) findViewById(R.id.imageLinkTextView);
        imageURL = getIntent().getExtras().getString("imageURL");
        imageTitle = getIntent().getExtras().getString("imageTitle");
        imageLinkTextView.setText(imageTitle);
        Picasso.with(this).load(imageURL).into(imageView);
    }

/*
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
    */
}
