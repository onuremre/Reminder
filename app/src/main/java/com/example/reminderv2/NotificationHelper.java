package com.example.reminderv2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import com.example.reminderv2.Activity.LoginActivity;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

/**
 * Bildirim için gerekli sınıf
 */
public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "channelName";

    private NotificationManager mManager;


    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager(){
        if(mManager == null){
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message){
        Intent intent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_alarm)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
    }
}
