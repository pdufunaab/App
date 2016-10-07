package com.staaworks.news;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.oadex.app.R;

import java.util.ArrayList;


public class NewsCategorySelectionFragment extends Fragment {

    private static final String FunaabNewsURL = "urlString";


    private String urlString = "http://rss.cnn.com/rss/edition_space.rss";
    private Activity activity;
    private ListView listView;
    ArrayList<String> namesList = new ArrayList<>(), attrList = new ArrayList<>();


    private OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            urlString = getArguments().getString(FunaabNewsURL);
            Log.i("URL_STRING", urlString);
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



        for (Category categories: Category.loadAll()) {
            namesList.add(categories.name());
            attrList.add(categories.getAttr());
        }

        listView = (ListView) activity.findViewById(R.id.listView);
        listView.setOnItemClickListener(new ListListener());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,R.layout.simple_list_item, R.id.simple_list_item, attrList);
        listView.setAdapter(adapter);
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



    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }




    private class ListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent in = new Intent(activity, NewsDisplayActivity.class);
            in.putExtra("Category", namesList.get(position));
            in.putExtra("urlString", urlString);
            activity.startActivity(in);
        }
    }
}
