package com.staaworks.News;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oadex.app.R;

import java.util.List;


public class NewsCategorySelectionFragment extends Fragment {

    private static final String FunaabNewsURL = "urlString";


    private String urlString = "http://rss.cnn.com/rss/edition_space.rss";
    private Activity activity;
    //private List<String> list =



    private OnFragmentInteractionListener mListener;


    public NewsCategorySelectionFragment() {}



    public static NewsCategorySelectionFragment newInstance(String urlString) {
        NewsCategorySelectionFragment fragment = new NewsCategorySelectionFragment();
        Bundle args = new Bundle();
        args.putString(FunaabNewsURL, urlString);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urlString = getArguments().getString(FunaabNewsURL);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_category_selection, container, false);
    }





    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() != null)
            activity = getActivity();

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





    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
