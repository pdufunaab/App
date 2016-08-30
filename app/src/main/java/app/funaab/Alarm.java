package app.funaab;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Malik on 8/27/2016.
 */
public class Alarm
{
    Intent intent;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    private  Context context;
    private  int requestCode;
    public Alarm(Context context, int requestCode)
    {
        this.context = context;
        this.requestCode = requestCode;
    }
    public void setAlarm(String day,String time)
    {
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK,getWeekDay(day));
        calendar.set(Calendar.HOUR,Integer.valueOf(timeArray[0]));
        calendar.set(Calendar.MINUTE,Integer.valueOf(timeArray[1]));
        calendar.set(Calendar.AM_PM, getAM_PM(timeArray[2]));

        intent = new Intent(context,AlarmReceiver.class);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY * 7,pendingIntent);
        Toast.makeText(context,"Alarm Set",Toast.LENGTH_LONG).show();
    }

    public  void cancelAlarm(int requestCode)
    {
        intent = new Intent(context,AlarmReceiver.class);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context,requestCode,intent,0);
        alarmManager.cancel(pendingIntent);
        Toast.makeText(context,"Alarm Canceled",Toast.LENGTH_LONG).show();
    }

    public  int getAM_PM(String am)
    {
        int format = 0;
        switch (am)
        {
            case "AM":
                format = Calendar.AM;
                break;
            case "PM":
                format = Calendar.PM;
                break;
        }
        return  format;
    }

    public int getWeekDay(String day)
    {
        int dayOfWeek = 0;
        switch (day)
        {
            case "Monday":
                dayOfWeek = Calendar.MONDAY;
                break;
            case "Tuesday":
                dayOfWeek = Calendar.TUESDAY;
                break;
            case "Wednesday":
                dayOfWeek = Calendar.WEDNESDAY;
                break;
            case "Thursday":
                dayOfWeek = Calendar.THURSDAY;
                break;
            case "Friday":
                dayOfWeek = Calendar.FRIDAY;
                break;
            case "Saturday":
                dayOfWeek = Calendar.SATURDAY;
                break;
            case "Sunday":
                dayOfWeek = Calendar.SUNDAY;
                break;
        }
        return dayOfWeek;
    }
}
