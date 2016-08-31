package app.funaab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Malik on 8/27/2016.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    TimeTableHelper timeTableHelper;

    Alarm alarm;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    Intent intent;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            alarm = new Alarm(context);
            Cursor cursor = timeTableHelper.getCoursesAlert(true);
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                String day = cursor.getString(cursor.getColumnIndexOrThrow("Day"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("Time"));
                int requestCode = cursor.getInt(cursor.getColumnIndexOrThrow("RequestCode"));
                alarm.setAlarm(day,time,requestCode);
                cursor.moveToNext();
            }
        }

        Toast.makeText(context,"Alarm Received",Toast.LENGTH_LONG).show();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
    }


}
