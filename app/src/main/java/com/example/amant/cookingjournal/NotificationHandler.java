package com.example.amant.cookingjournal;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHandler extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel";

    private NotificationManager manager;
    public NotificationHandler(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID,channelName, NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLightColor(R.color.colorPrimary);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager(){
        if(manager == null){
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String message){
        Intent clickIntent = new Intent(this,MainActivity.class);
        PendingIntent clickPendingIntent = PendingIntent.getActivity(this, 1, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setContentIntent(clickPendingIntent);
    }
}
