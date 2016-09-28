package com.staaworks.News;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.oadex.app.R;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.staaworks.util.Network;

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

        boolean connected = Network.isConnected();

        if (connected) Picasso.with(this).load(imageURL).placeholder(R.mipmap.rss).error(R.mipmap.rss).into(imageView);
        else Picasso.with(this).load(imageURL).networkPolicy(NetworkPolicy.OFFLINE).into(imageView);
    }
}
