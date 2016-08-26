package com.oadex.app;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import app.funaab.TimeTableAdapter;
import app.funaab.TimeTableHelper;
import app.funaab.TimeTableManagementActivity;

public class TimetableActivity extends AppCompatActivity {

    Bundle bundle;
    TimeTableHelper timeTableHelper;
    private ListView timeTableListView;
    private TimeTableAdapter timeTableAdapter;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bundle = new Bundle();
        timeTableHelper = new TimeTableHelper(this);
        timeTableAdapter = new TimeTableAdapter(this,timeTableHelper.getCourses());
        timeTableListView  = (ListView) findViewById(R.id.timeTableListView);
        timeTableListView.setAdapter(timeTableAdapter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timeTableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {
                putValues(bundle,position);
                Toast.makeText(getApplicationContext(),"Editing " +bundle.getString("courseCode"),Toast.LENGTH_LONG).show();
                editCourse();
                return true;
            }
        });


    }

    public void putValues(Bundle bundle, int position)
    {
        Cursor cursor = (Cursor)timeTableListView.getItemAtPosition(position);
        bundle.putString("courseCode",cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        bundle.putString("courseTitle",cursor.getString(cursor.getColumnIndexOrThrow("CourseTitle")));
        bundle.putString("venue",cursor.getString(cursor.getColumnIndexOrThrow("Venue")));
        bundle.putString("day",cursor.getString(cursor.getColumnIndexOrThrow("Day")));
        bundle.putString("time",cursor.getString(cursor.getColumnIndexOrThrow("Time")));
        bundle.putString("alert",cursor.getString(cursor.getColumnIndexOrThrow("Alert")));
        bundle.putString("dayIndex",cursor.getString(cursor.getColumnIndexOrThrow("DayIndex")));
    }

    public void editCourse()
    {
        intent = new Intent(this, TimeTableManagementActivity.class);
        intent.putExtra("bundle",bundle);
        intent.putExtra("fragment","Edit");
        startActivity(intent);
    }

    public void addNewCourse(View view)
    {
        intent = new Intent(this, TimeTableManagementActivity.class);
        intent.putExtra("fragment","Add");
        startActivity(intent);
    }
}

