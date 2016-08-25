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

        //Cursor cursor = timeTableHelper.getReadableDatabase().rawQuery("select * from TimeTable",null);
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
                TextView course = (TextView)view.findViewById(R.id.courseView);
                Toast.makeText(getApplicationContext(),course.getText().toString(),Toast.LENGTH_LONG).show();
                editCourse();
                return true;
            }
        });


    }

    public void editCourse()
    {
        intent = new Intent(this, TimeTableManagementActivity.class);
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

