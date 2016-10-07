package com.staaworks.news;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.oadex.app.R;
import com.staaworks.util.InfiniteScrollListener;

import java.net.MalformedURLException;
import java.net.URL;


public class NewsFragment extends Fragment {

    private enum constants {

        savedStateKey("staaNSS"),
        //lastSavedKey("staaNSPLastSaved"),
        feedSPName("staaNSP");

        public final String value;
        constants(String value) {
            this.value = value;
        }
    }


    private FeedLoader task;
    private String urlString = "http://rss.cnn.com/rss/edition.rss";
    private SwipeRefreshLayout swipeContainer;

    private ListView feedsListView;

    private SharedPreferences preferences;

    private Activity activity;
    private URL url;

    private Category category;

    private OnFragmentInteractionListener mListener;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments().getBundle("extras");
            if (args!= null) {
                urlString = args.getString("urlString");
                category = Category.getCategoryFromName(args.getString("Category", "general"));
            }
            Log.i("URL String", urlString);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_news, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity() != null)
            activity = getActivity();



        swipeContainer = (SwipeRefreshLayout) activity.findViewById(R.id.swipeContainer);
        feedsListView = (ListView) activity.findViewById(R.id.feedsListView);




        preferences = activity.getSharedPreferences(constants.feedSPName.value, Context.MODE_PRIVATE);


        if (savedInstanceState != null && !savedInstanceState.isEmpty()) {
            task = new FeedLoader(activity, feedsListView, category);
            setURL(urlString);
            task.parse_Store_Display(savedInstanceState.getString(constants.savedStateKey.value));
            System.out.println("FEED SAVED INSTANCE EXECUTED");
        }
/*

        else if (preferences != null && preferences.contains(constants.lastSavedKey.value)) {
            task = new FeedLoader(activity, feedsListView, category);
            setURL(urlString);
            task.parse_Store_Display(preferences.getString(constants.lastSavedKey.value, ""));
            System.out.println("FEED SHARED PREFERENCES EXECUTED");
        }

*/
        else {
            task = new FeedLoader(activity, feedsListView, category);
            setURL(urlString);
            task.execute(url);
            System.out.println("FEED NORMAL EXEC USED");
        }


        feedsListView.setOnScrollListener(new InfiniteScrollListener() {
            @Override
            public void loadMore(int page, int totalItemsCount) {
                task.load();
            }
        });



        feedsListView.requestFocus();

        // Set Refresh Action

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {
                task = new FeedLoader(activity, feedsListView, category);
                task.execute(url);
                feedsListView.requestFocus();
                swipeContainer.setRefreshing(false);
            }

        });

        // Configure the refreshing colors

        swipeContainer.setColorSchemeResources(
                android.R.color.holo_blue_bright,

                android.R.color.holo_green_light,

                android.R.color.holo_orange_light,

                android.R.color.holo_red_light);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String inputStreamAsString = task.getString();
        outState.putString(constants.savedStateKey.value, inputStreamAsString);
        /*SharedPreferences.Editor editor = preferences.edit();
        editor.putString(constants.lastSavedKey.value, inputStreamAsString);
        editor.apply();*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void setURL(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

}
