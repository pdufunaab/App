
package com.staaworks.News;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oadex.app.R;
import com.squareup.picasso.Picasso;
import com.staaworks.storage.FeedDBA;


/**
 * Created by Ahmad Alfawwaz on 8/12/2016.
 */
public class FeedAdapter extends ArrayAdapter<Feed> {


    private Feeds feeds;
    private Activity activity;
    private View.OnClickListener loader;
    private FeedDBA storage;

    public FeedAdapter(Activity context, Feeds objects, View.OnClickListener loadEarlier) {
        super(context, R.layout.feed_view, R.id.titleView, objects);
        feeds = objects;
        activity = context;
        loader = loadEarlier;
        storage = new FeedDBA(activity);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        storage.open();

        View row = super.getView(position, convertView, parent);

        if (feeds.get(position).getLink().equals("ERROR")) {
            TextView t = (TextView) row.getTag(R.id.titleView);
            if (t == null) {
                t = (TextView) row.findViewById(R.id.titleView);
                row.setTag(R.id.titleView, t);
            }
            t.setText(getItem(position).getTitle());

            TextView d = (TextView) row.getTag(R.id.descriptionView);
            if (d == null) {
                d = (TextView) row.findViewById(R.id.descriptionView);
                row.setTag(R.id.descriptionView, d);
            }
            d.setVisibility(View.GONE);
            return row;
        }

        else {

            RelativeLayout feedPane = (RelativeLayout) row.getTag(R.id.feedPane);
            if (feedPane == null) {
                feedPane = (RelativeLayout) row.findViewById(R.id.feedPane);
                row.setTag(R.id.feedPane, feedPane);
            }

            TextView titleView = (TextView) row.getTag(R.id.titleView);
            if (titleView == null) {
                titleView = (TextView) row.findViewById(R.id.titleView);
                row.setTag(R.id.titleView, titleView);
            }
            titleView.setText(getItem(position).getTitle());


            TextView loadEarlier = (TextView) row.getTag(R.id.earlierFeeds);
            if (loadEarlier == null) {
                loadEarlier = (TextView) row.findViewById(R.id.earlierFeeds);
                row.setTag(R.id.earlierFeeds);
            }
            loadEarlier.setOnClickListener(loader);
            System.out.println("FDSZ: The size of the feeds being read by the adapter is " + feeds.size());



            if (position == feeds.size() - 1) {
                loadEarlier.setVisibility(View.VISIBLE);
            }

            else if ((position - (storage.getAll().size() - 1)) == 0) {
                feedPane.setMinimumHeight(feedPane.getHeight() - loadEarlier.getHeight());
                loadEarlier.setVisibility(View.GONE);
            }

            else {
                if (loadEarlier.getVisibility() == View.VISIBLE) {
                    feedPane.setMinimumHeight(feedPane.getHeight() - loadEarlier.getHeight());
                    loadEarlier.setVisibility(View.GONE);
                }
            }



            // TODO **  *Indicate New* | *get picture online today* | *Fix in feed_view.xml* | *id:newIndicator* **
            if (feeds.get(position).isNew()) {
                Log.i("New Feed:", feeds.get(position).getTitle());
                //TextView newIndicator = (TextView) row.findViewById(R.id.newIndicator);
                //newIndicator.setVisibility(View.VISIBLE);
            }

            // TODO **  *Indicate Important * | *get picture online today* | *Fix in feed_view.xml* | *id:importantIndicator* **
            if (feeds.get(position).getRating() == 5) {
                Log.i("Important Feed:", feeds.get(position).getTitle());
                //TextView importantIndicator = (TextView) row.findViewById(R.id.importantIndicator);
                //importantIndicator.setVisibility(View.VISIBLE);
            }


            TextView descriptionView = (TextView) row.getTag(R.id.descriptionView);
            if (descriptionView == null) {
                descriptionView = (TextView) row.findViewById(R.id.descriptionView);
                row.setTag(R.id.descriptionView, descriptionView);
            }
            descriptionView.setText(getItem(position).getDescription());


            //Set Title View Listener
            titleClicked t = new titleClicked(descriptionView, feedPane);
            titleView.setOnClickListener(t);

            descriptionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(activity, InAppBrowserPage.class);
                    in.putExtra("URL", feeds.get(position).getLink());
                    activity.startActivity(in);
                }
            });




            ImageView imageView = (ImageView) row.getTag(R.id.feedImage);
            if (imageView == null) {
                imageView = (ImageView) row.findViewById(R.id.feedImage);
                row.setTag(R.id.feedImage, imageView);
                if (imageView == null)
                    Log.e("Null Image View Error", "The image view is still null");
            }
            Picasso.with(activity).load(feeds.get(position).getImageURL()).into(imageView);


            //Set Image View Listener
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(activity, FeedImageView.class);
                    in.putExtra("imageURL", feeds.get(position).getImageURL());
                    in.putExtra("imageTitle", feeds.get(position).getImageTitle());
                    activity.startActivity(in);
                }
            });

            storage.close();
            return row;
        }
    }

    private class titleClicked implements View.OnClickListener {

        private RelativeLayout feedPane;
        private TextView descriptionView;

        protected titleClicked(TextView descriptionView, RelativeLayout feedPane) {
            this.descriptionView = descriptionView;
            this.feedPane = feedPane;
        }

        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {

            if (descriptionView.getVisibility() == View.GONE) {
                descriptionView.setVisibility(View.VISIBLE);
                feedPane.setMinimumHeight(feedPane.getHeight() + descriptionView.getHeight());
            }
            else {
                descriptionView.setVisibility(View.GONE);
                feedPane.setMinimumHeight(feedPane.getHeight() - descriptionView.getHeight());
            }
        }
    }


}
