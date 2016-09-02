package com.staaworks.news;

import android.app.Activity;
import android.content.Context;
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
import com.staaworks.storage.FeedDBA;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {

    private FeedLoader task;
    private String urlString = "http://rss.cnn.com/rss/edition_space.rss";
    private SwipeRefreshLayout swipeContainer;

    private ListView feedsListView;


    private Activity activity;
    private URL url;

    private FeedDBA.Categories category;

    private OnFragmentInteractionListener mListener;

    public NewsFragment() {}



    /**
     *
     * @param urlString Feed URL.
     * @return A new instance of fragment NewsFragment.
     */
    public static NewsFragment newInstance(String urlString) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString("urlString", urlString);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            Bundle args = getArguments().getBundle("extras");
            if (args!= null) {
                urlString = args.getString("urlString");
                category = (FeedDBA.Categories) args.get("Category");
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

        task = new FeedLoader(activity, feedsListView, category);
        setURL(urlString);
        task.execute(url);
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
        // TODO: Update argument type and name
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
