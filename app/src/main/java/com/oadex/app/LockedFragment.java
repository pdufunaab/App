package com.oadex.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

/**
 * Created by Oseni Adekunle on 11/08/2016.
 */
public class LockedFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.lockedfragment,container,false);
          GridView gridview = (GridView) view.findViewById(R.id.gridview);
        gridview.setAdapter(new LockedImageAdapter(view.getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
              //  Toast.makeText(MainActivity.this, "" + position,
                //        Toast.LENGTH_SHORT).show();
                if(position == 0){
                    Toast.makeText(getActivity(),"You need to be logged in",Toast.LENGTH_SHORT).show();
                }


            }
        });
        return view;
    }
}
