
package com.staaworks.news;

import android.content.Context;
import android.content.Intent;
import android.system.ErrnoException;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oadex.app.R;
import com.squareup.picasso.Picasso;


/**
 * Created by Ahmad Alfawwaz on 8/12/2016
 */
public class FeedAdapter extends ArrayAdapter<Feed> {


    private Feeds feeds;
    private Context context;
    private View.OnClickListener loader;
    private int total;

    public FeedAdapter(Context context, Feeds objects, View.OnClickListener loadEarlier, int total) {
        super(context, R.layout.feed_view, R.id.titleView, objects);
        feeds = objects;
        this.context = context;
        loader = loadEarlier;
        this.total = total;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View row = super.getView(position, convertView, parent);

        String title = getItem(position).getTitle();

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

            TextView loadEarlier = (TextView) row.getTag(R.id.earlierFeeds);
            if (loadEarlier == null) {
                loadEarlier = (TextView) row.findViewById(R.id.earlierFeeds);
                row.setTag(R.id.earlierFeeds);
            }
            loadEarlier.setOnClickListener(loader);

            loadEarlier.setText(R.string.err_load_earlier_prompt);

            if(getCount() != 1)
                remove(getItem(position));

            return row;
        }

        else {


            /**
             *  Instantiate the base container for the custom layout
             *
             */

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






            TextView loadEarlier = (TextView) row.getTag(R.id.earlierFeeds);
            if (loadEarlier == null) {
                loadEarlier = (TextView) row.findViewById(R.id.earlierFeeds);
                row.setTag(R.id.earlierFeeds);
            }
            loadEarlier.setOnClickListener(loader);
            System.out.println("FDSZ: The size of the feeds being read by the adapter is " + feeds.size());







            if ((position - (total - 1)) == 0) {
                feedPane.setMinimumHeight(feedPane.getHeight() - loadEarlier.getHeight());
                loadEarlier.setVisibility(View.GONE);
            }

            else if (position == feeds.size() - 1) {
                loadEarlier.setVisibility(View.VISIBLE);
            }

            else {
                if (loadEarlier.getVisibility() == View.VISIBLE) {
                    feedPane.setMinimumHeight(feedPane.getHeight() - loadEarlier.getHeight());
                    loadEarlier.setVisibility(View.GONE);
                }
            }


            TextView descriptionView = (TextView) row.getTag(R.id.descriptionView);
            if (descriptionView == null) {
                descriptionView = (TextView) row.findViewById(R.id.descriptionView);
                row.setTag(R.id.descriptionView, descriptionView);
            }
            descriptionView.setText(getItem(position).getDescription());


            titleView.setText(title);

            titleClicked t = new titleClicked(descriptionView, feedPane);
            titleView.setOnClickListener(t);

            descriptionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(context, InAppBrowserPage.class);
                    in.putExtra("URL", feeds.get(position).getLink());
                    context.startActivity(in);
                }
            });




            ImageView imageView = (ImageView) row.getTag(R.id.feedImage);
            if (imageView == null) {
                imageView = (ImageView) row.findViewById(R.id.feedImage);
                row.setTag(R.id.feedImage, imageView);
                if (imageView == null)
                    Log.e("Null Image View Error", "The image view is still null");
            }
            Picasso.with(context).load(feeds.get(position).getImageURL()).into(imageView);


            //Set Image View Listener
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in = new Intent(context, FeedImageView.class);
                    in.putExtra("imageURL", feeds.get(position).getImageURL());
                    in.putExtra("imageTitle", feeds.get(position).getImageTitle());
                    context.startActivity(in);
                }
            });

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
