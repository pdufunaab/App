package com.oadex.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import app.funaab.TimeTableAdapter;
import app.funaab.TimeTableHelper;
import app.funaab.TimeTableManagementActivity;

public class TimetableActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    Bundle bundle;
    TimeTableHelper timeTableHelper;
    private ListView timeTableListView;
    private TimeTableAdapter timeTableAdapter;
    PopupMenu popupMenu;
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
                popupMenu = new PopupMenu(TimetableActivity.this,view);
                MenuInflater menuInflater = popupMenu.getMenuInflater();
                menuInflater.inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(TimetableActivity.this);
                popupMenu.show();
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

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.add:
                addNewCourse(timeTableListView);
                break;
            case R.id.edit:
                editCourse();
                break;
            case R.id.delete:
                deleteDialog(bundle.getString("courseCode"),bundle.getString("day"));
                Toast.makeText(this,"Do u want to delete this course", Toast.LENGTH_LONG).show();
                break;
            case R.id.details:
                Toast.makeText(this,"Do u want to view the details of this course", Toast.LENGTH_LONG).show();
                break;

        }
        return true;
    }

    public void deleteDialog(final String courseCode, final String day)
    {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure,You want to delete this course");
        alertDialogBuilder.setTitle("Confirm Delete");
        alertDialogBuilder.setIcon(android.R.drawable.ic_menu_delete);

        alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener(

        )
        {
            @Override
            public void onClick(DialogInterface arg0, int arg1)
            {
                timeTableHelper.delete(courseCode,day);
                timeTableAdapter.changeCursor(timeTableHelper.getCourses());
                timeTableListView.setAdapter(timeTableAdapter);
                Toast.makeText(TimetableActivity.this,"Course Deleted",Toast.LENGTH_LONG).show();
            }
        });

        alertDialogBuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(TimetableActivity.this,"Delete Canceled",Toast.LENGTH_LONG).show();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

