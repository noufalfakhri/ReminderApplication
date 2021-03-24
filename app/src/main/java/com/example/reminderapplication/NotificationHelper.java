package com.example.reminderapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper{

    public static final String NOTIFICATION1_ID = "notification1-id" ;
    public static final String NOTIFICATION1 = "notification1" ;

    public static final String NOTIFICATION2_ID = "notification2-id" ;
    public static final String NOTIFICATION2 = "notification2" ;

    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            createchannels();
        }}

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createchannels(){

        NotificationChannel channel1 = new NotificationChannel(NOTIFICATION1_ID,NOTIFICATION1,NotificationManager. IMPORTANCE_HIGH);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.design_default_color_on_primary);

        getManager().createNotificationChannel(channel1);

        ////////////////////////////////
        System.out.println("created notficiation");

        NotificationChannel channel2 = new NotificationChannel(NOTIFICATION2_ID,NOTIFICATION2,NotificationManager.IMPORTANCE_LOW);
        channel2.enableLights(true);
        channel2.enableVibration(true);
        channel2.setLightColor(R.color.design_default_color_on_primary);

        getManager().createNotificationChannel(channel2);
    }

    public NotificationManager getManager(){

        if(mManager==null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager ;
    }

    public NotificationCompat.Builder getChannel1Notification(String title,String message, int id ){
        Intent notificationIntent = new Intent(getApplicationContext() ,  MainActivity. class ) ;
//        notificationIntent.putExtra( "id" , id ) ;
//        Bundle dataBundle = new Bundle();
//        dataBundle.putInt("id", id);
//        notificationIntent.putExtras(dataBundle);
       // notificationIntent.putExtra("id",id);

        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent pendingIntent = PendingIntent. getActivity ( this, 0 , notificationIntent , 0 ) ;

        return new NotificationCompat.Builder(getApplicationContext(),NOTIFICATION1_ID)
                .setContentTitle(title)
                .setContentText(message).setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_one);

    }

    public NotificationCompat.Builder getChanne21Notification(String title,String message, int id ){
        Intent notificationIntent = new Intent(getApplicationContext() ,  MainActivity. class ) ;
//        notificationIntent.putExtra( "id" , id ) ;
//        Bundle dataBundle = new Bundle();
//        dataBundle.putInt("id", id);
//        //notificationIntent.putExtras("id",id);
//        notificationIntent.putExtras(dataBundle);

        notificationIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP | Intent. FLAG_ACTIVITY_SINGLE_TOP ) ;
        PendingIntent pendingIntent = PendingIntent. getActivity ( this, 0 , notificationIntent , 0 ) ;

        return new NotificationCompat.Builder(getApplicationContext(),NOTIFICATION2_ID)
                .setContentTitle(title)
                .setContentText(message).setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_two);

    }
}