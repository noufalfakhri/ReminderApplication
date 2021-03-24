package com.example.reminderapplication;

import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;

import androidx.core.app.NotificationCompat;


public class AlertReceiver1 extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper mNotificationHelper = new NotificationHelper(context);
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        int id = intent.getIntExtra("id",0);

        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title,message,id);
        mNotificationHelper.getManager().notify(1,nb.build());
    }
}