package com.example.amant.cookingjournal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {
public NotificationHandler notificationHandler;
    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("recipe");
        notificationHandler = new NotificationHandler(context);
        NotificationCompat.Builder builder = notificationHandler.getChannelNotification("It's Time To Cook "+message,"Your Recipe is Ready.");
        notificationHandler.getManager().notify(1,builder.build());
    }
}
