package app.funaab;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

/**
 * Created by Malik on 8/18/2016.
 */
public class TimeTableHelper extends SQLiteOpenHelper
{
    private static final String databaseName = "TimeTableDatabase";
    private static final String tableName = "TimeTable";
    private static final String courseCode = "_id";
    private static final String courseTitle = "CourseTitle";
    private static final String venue = "Venue";
    private static final String day = "Day";
    private static final String time = "Time";
    private static final int databaseVersion = 1;


    public TimeTableHelper(Context context)
    {
        super(context, databaseName, null, databaseVersion);
        Log.i("TimeTableHelper", "initialise constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + tableName + " (" + courseCode + " TEXT PRIMARY KEY, " + courseTitle + " TEXT, " + venue + " TEXT, "+ day + " TEXT, " + time + " TEXT " + ")");
        Log.i("TimeTableHelper", "on create database");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + tableName);
        onCreate(db);
        Log.i("TimeTableHelper", "upgrade database");

    }

    public void insert(String courseCode, String courseTitle,String venue, String day, String time)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.courseCode,courseCode);
        contentValues.put(this.courseTitle,courseTitle);
        contentValues.put(this.venue,venue);
        contentValues.put(this.day,day);
        contentValues.put(this.time,time);

        getWritableDatabase().insert(tableName,null,contentValues);

        Log.i("TimeTableHelper", "inserting into database");


    }

    public void update(String courseCode, String courseTitle,String venue, String day, String time)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(this.courseCode,courseCode);
        contentValues.put(this.courseTitle,courseTitle);
        contentValues.put(this.venue,venue);
        contentValues.put(this.day,day);
        contentValues.put(this.time,time);
        getWritableDatabase().update(tableName,contentValues,"_id = ? AND Day = ?",new String[]{courseCode,day});
        Log.i("TimeTableHelper", "updating data");
    }

    public void delete(String courseCode, String day)
    {
        getReadableDatabase().delete(tableName,"_id = ? AND Day = ?",new String[]{courseCode,day});
        Log.i("TimeTableHelper", "deleting data ");

    }

    public Cursor getCourses()
    {
        Log.i("TimeTableHelper", "getDayCourse");
        Cursor cursor = getReadableDatabase().query(tableName,new String[]{courseCode,courseTitle,venue,this.day,time},null,null,null,null,null);

        return  cursor;
    }
}