package com.staaworks.news;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.staaworks.storage.FeedDBA;

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
    private Feeds loadedFeeds = new Feeds(), storedFeeds;
    private ListView listView;
    private Activity activity;
    private ProgressDialog progressDialog;
    FeedDBA storage;
    private FeedDBA.Categories category;
    private int total;



    public FeedLoader(Activity activity, @NonNull ListView listView, FeedDBA.Categories category) {
        this.listView = listView;
        this.activity = activity;
        storage = new FeedDBA(this.activity);
        this.category = category;
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
        loadedFeeds.clear();

        XmlPullParser parser;
        InputStream inputStream;


        storage.open();


        storedFeeds = storage.getAll();
        total = storedFeeds.size();


        String title = "Default Title";
        String link = "http://google.com";
        String description = "Default Text For Feed Description";
        String imageURL = "http://google.com";
        String pubDate = "13/8/2016";
        String rating = "3";
        FeedDBA.Categories category = FeedDBA.Categories.general;




        int event;
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

                    Feed feed = new Feed(title, link, description, imageURL, title, pubDate, rating, category.name());

                    if (!storedFeeds.contains(feed)) {
                        System.out.println("Feed About To Be Stored : picked");
                        storage.addFeed(feed);
                        total += 1;
                        System.out.println("Feed Stored : picked");
                    }


                    insideItem = false;
                }


                event = parser.next();
            }


        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            Log.e("Exception Picked, size:", loadedFeeds.size() + "");
            loadedFeeds.clear();

            loadedFeeds.add(new Feed("Oops! An error Occurred while reading news from website ", "ERROR", description, imageURL, title, pubDate, rating, this.category.name()));
            return loadedFeeds;
        }






        loadedFeeds = storage.getNextSet(this.category);

        if (loadedFeeds.isEmpty())
            loadedFeeds.add(new Feed("Oops! There is no news in " + this.category + " category", "ERROR", description, imageURL, title, pubDate, rating, this.category.name()));


        System.out.println("FEEDSIZE : " + loadedFeeds.size());
        storage.close();
        return loadedFeeds;
    }


    @Override
    protected void onPostExecute(Feeds feeds) {
        feedAdapter = new FeedAdapter(activity, feeds, new Loader(), total);
        listView.setAdapter(feedAdapter);

        if (progressDialog.isShowing() && progressDialog != null) {
            progressDialog.dismiss();
        }
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
            for (Feed feed : storage.getNextSet(category)) {
                if (!loadedFeeds.contains(feed))
                    loadedFeeds.add(feed);
            }
            feedAdapter.notifyDataSetChanged();
            storage.close();
        }

    }

}
