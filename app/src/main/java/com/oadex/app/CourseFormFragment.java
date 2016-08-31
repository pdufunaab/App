package com.oadex.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import app.funaab.CourseFormDisplay;

/**
 * Created by Malik on 8/31/2016.
 */
public class CourseFormFragment extends Fragment
{
    Bundle bundle;
    Spinner courseSessionSpinner;
    ArrayAdapter<String> spinnerAdapter;
    FloatingActionButton floatingActionButton;

    public CourseFormFragment()
    {

    }

    public CourseFormFragment newInstance(Bundle bundle)
    {
        CourseFormFragment courseFormFragment = new CourseFormFragment();
        courseFormFragment.setArguments(bundle);
        return  courseFormFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        courseSessionSpinner = (Spinner)view.findViewById(R.id.select_courseSession_spinner);
        floatingActionButton = (FloatingActionButton)view.findViewById(R.id.courseForm_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                bundle.putString("session",courseSessionSpinner.getSelectedItem().toString());
                displayCourseForm(bundle);
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.prof_course,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        bundle = new Bundle();
        spinnerAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.session_array));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSessionSpinner.setAdapter(spinnerAdapter);
    }

    public  void displayCourseForm(Bundle bundle)
    {
        Intent intent = new Intent(getContext(), CourseFormDisplay.class);
        intent.putExtra("bundle",bundle);
        startActivity(intent);
    }
}
