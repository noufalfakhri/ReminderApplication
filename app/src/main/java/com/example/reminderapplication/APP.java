package com.example.reminderapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class APP extends Application {

    public static final String HIGH = "channel_high";
    public static final String LOW = "channel_low";

    @Override
    public void onCreate(){
        super.onCreate();
     //   createNotificationChannel();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) == false)
            return;
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channelHigh = new NotificationChannel(HIGH, "High Importance", NotificationManager.IMPORTANCE_HIGH);
            channelHigh.setDescription("this Channel is of high importance");

        NotificationChannel channelLow = new NotificationChannel(LOW, "Low Importance", NotificationManager.IMPORTANCE_LOW);
        channelHigh.setDescription("this Channel is of low importance");
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channelHigh);
        notificationManager.createNotificationChannel(channelLow);


    }
}
