package app.funaab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Malik on 8/27/2016.
 */
public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        System.out.println("BROADCAST RECIEVED");
        Toast.makeText(context,"Alarm Received",Toast.LENGTH_LONG).show();
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        Ringtone ringtone = RingtoneManager.getRingtone(context, uri);
        ringtone.play();
    }
}
