
package com.example.reminderapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.app.NotificationManager;

import androidx.core.app.NotificationCompat;

//import static com.example.reminderapplication.SecondActivity.getTit();
public class NotificationPublisher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);

        String title = SecondActivity.getTit();
        int priority = SecondActivity.getImportance();

        int id = SecondActivity.getID();
        System.out.println("id is"+  id);
        if(priority==1) {
            NotificationCompat.Builder nb = notificationHelper.getChannel1Notification(title, "high priority",id);
            notificationHelper.getManager().notify(1, nb.build());
        }
        else {
            NotificationCompat.Builder nb = notificationHelper.getChanne21Notification(title,"Low priority" ,id);
            notificationHelper.getManager().notify(2,nb.build());

        }
    }



}
