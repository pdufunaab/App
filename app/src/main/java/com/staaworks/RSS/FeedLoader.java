package com.staaworks.RSS;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ListView;
import android.widget.Toast;

import com.staaworks.RSS.storage.FeedDBA;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ahmad Alfawwaz on 8/9/2016
 */
public class FeedLoader extends AsyncTask<URL, Feeds, Feeds> {


    FeedAdapter feedAdapter;
    public final int MS = 1000;
    public volatile boolean parsingComplete = true;
    private Feeds feeds = new Feeds();
    private ListView listView;
    private Activity activity;
    private ProgressDialog progressDialog;
    FeedDBA storage;



    public FeedLoader(Activity activity,@NonNull ListView listView) {
        this.listView = listView;
        this.activity = activity;
        storage = new FeedDBA(this.activity);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (activity == null) System.out.println("NullActivityTag: Activity is null");
        else {
            progressDialog = ProgressDialog.show(activity, "Loading", "Please wait, News from FUNAAB are currently being loaded", true, true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    activity.finish();
                }
            });
        }
    }

    /**
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Feeds doInBackground(URL... params) {
        URL url = params[0];

        Log.i("URL status: null?", "" + (url == null));
        feeds.clear();

        XmlPullParser parser;
        InputStream inputStream;


        storage.open();


        String title = "Default Title";
        String link = "http://google.com";
        String description = "Default Text For Feed Description";
        String imageURL = "http://i.imgur.com/IXELLM8.jpg";
        String pubDate = "13/8/2016";
        String rating = "3";



        int event;
        String text = null;
        Boolean insideItem = false;



        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setReadTimeout(60 * MS);
            connection.setConnectTimeout(60 * MS);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            inputStream = connection.getInputStream();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();




            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);

            event = parser.getEventType();

            while (event != XmlPullParser.END_DOCUMENT)
            {

                String name = parser.getName();

                if (event == XmlPullParser.START_TAG) {

                    switch (name) {


                        case "item":
                            insideItem = true;
                            break;

                        case "title":
                            if (insideItem) {
                                title = parser.nextText();
                                Log.i("Title Picked", title);
                            }
                            break;

                        case "link":
                            if (insideItem) {
                                link = parser.nextText();
                            }
                            break;
                        case "description":
                            if (insideItem) {
                                description = parser.nextText();
                            }
                            break;
                        case "pubDate":
                            if (insideItem) {
                                pubDate = parser.nextText();
                            }

                            break;
                        case "media:content":
                            if (insideItem) {
                                imageURL = parser.getAttributeValue(null, "url");
                            }

                            break;

                        case "rating":
                            if (insideItem) {
                                rating = parser.nextText();
                            }

                    }

                }
                    else if (event == XmlPullParser.END_TAG && parser.getName().equalsIgnoreCase("item")) {
                        addIfAddable(new Feed(title, link, description,imageURL,title, pubDate, rating), feeds);
                        insideItem = false;
                    }


                event = parser.next();
            }


        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            Log.e("Exception Picked, size:", feeds.size() + "");
            feeds.clear();

            feeds.add(new Feed("Oops! The news site is either blank or invalid", "ERROR", description, imageURL, title, pubDate, rating));
            return feeds;
        }


        feeds = sortFeeds(feeds);


        for (Feed feed : feeds) {
            storage.addFeed(feed);
            System.out.println(storage.getAll() + " DFRED");
        }



        feeds = storage.getNextSet();
        System.out.println("FEEDSIZE : " + feeds.size());
        storage.close();
        return feeds;
    }


    @Override
    protected void onPostExecute(Feeds feeds) {
        feedAdapter = new FeedAdapter(activity, feeds, new Loader());
        listView.setAdapter(feedAdapter);

        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
    }



    private void addIfAddable(Feed feed, Feeds feeds) {
        Boolean addable = true;
        if (feeds.isEmpty()) {
            feeds.add(feed);
        }
        else {
            for (int i = 0; i < feeds.size(); i++) {
                if (feeds.get(i).getLink().equals(feed.getLink())) {
                    addable = false;
                }
            }
            if (addable) {
                feeds.add(feed);
            }

        }
    }



    private Feeds sortFeeds(Feeds feeds) {
        Feeds finalFeeds = new Feeds();
        for (int i = 0; i < feeds.size(); i++) {
            if (feeds.get(i).isValid() && feeds.get(i).isNew()) {
                addIfAddable(feeds.get(i), finalFeeds);
            }
            else if (feeds.get(i).isValid() || feeds.get(i).isNew()) {
                addIfAddable(feeds.get(i), finalFeeds);
            }
            else {
                addIfAddable(feeds.get(i), finalFeeds);
            }
        }
        return finalFeeds;
    }

    private class Loader implements View.OnClickListener {


        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            storage.open();
            int sizeBefore = feeds.size(), sizeAfter;
            for (Feed feed : storage.getNextSet()) {
                addIfAddable(feed, feeds);
                sizeAfter = feeds.size();
                if ((sizeAfter - sizeBefore) == 0) {
                    Toast.makeText(activity, "All Feeds Have Been Successfully Loaded", Toast.LENGTH_LONG).show();
                }
            }
            feedAdapter.notifyDataSetChanged();
            storage.close();
        }

    }

}
