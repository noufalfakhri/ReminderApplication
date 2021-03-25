//package com.example.reminderapplication;
//
//import android.app.AlarmManager;
//import android.app.AlertDialog;
//import android.app.DatePickerDialog;
//import android.app.Dialog;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.TimePickerDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
////
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
//
//import java.sql.Time;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//public class SecondActivity extends AppCompatActivity {
//
//    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
//    private final static String default_notification_channel_id = "default" ;
//
//    Button backbuttonView, saveButtonView, deleteTaskView;
//    private DatePicker datePicker;
//    private TimePicker timePicker;
//    private EditText titleView;
//    private String tit;
//
//    public int importance;
//    private RadioGroup radioGroup;
//    private RadioButton highPriority , lowPriority;
//    DatabaseHelper db;
//    private NotificationHelper mNotificationHelper;
//
//
//    private Calendar calendar;
//    private TextView dateView;
//    private TextView timeView;
//    private int id=0;
//
//    private int year, month, day, hour ,min ;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.second_activity);
//        Toast.makeText(getApplicationContext(), "in second activity", Toast.LENGTH_SHORT).show();
//
//        db= new DatabaseHelper(this);
//        titleView = (EditText) findViewById(R.id.editTitle);
//        dateView = (TextView) findViewById(R.id.dateView);
//        timeView = (TextView) findViewById(R.id.timeView);
//        radioGroup = (RadioGroup) findViewById(R.id.PriorityRadioGroup);
//        saveButtonView = (Button) findViewById(R.id.saveReminderButton);
//        deleteTaskView =(Button) findViewById(R.id.deleteTaskButton);
//        lowPriority = (RadioButton) findViewById(R.id.lowPriority);
//        highPriority = (RadioButton) findViewById(R.id.lowPriority);
//
//        calendar = Calendar.getInstance();
//
//
//        year = calendar.get(Calendar.YEAR);
//
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//        hour = calendar.get(Calendar.HOUR_OF_DAY);
//        min = calendar.get(Calendar.MINUTE);
//        showTime(hour, min);
//        showDate(year, month+1, day);
//
//        mNotificationHelper = new NotificationHelper(this);
//
//        Bundle extras = getIntent().getExtras();
//
//        if(extras !=null) {
//            int Value = extras.getInt("id");
//
//            if (Value == 0 )
//                deleteTaskView.setVisibility(View.GONE);
//
//            if(Value>0){
//
//                //means this is the view part not the add contact part.
//
//                Cursor rs = db.retrieveTask(Value);
//                id  = Value;
//                rs.moveToFirst();
//
//                String title = rs.getString(rs.getColumnIndex(DatabaseHelper.col_2));
//                String time = rs.getString(rs.getColumnIndex(DatabaseHelper.col_3));
//                String date = rs.getString(rs.getColumnIndex(DatabaseHelper.col_4));
//                importance = rs.getInt(rs.getColumnIndex(DatabaseHelper.col_5));
//                System.out.println(importance);
//                if (!rs.isClosed())  {
//                    rs.close();
//                }
//
//                // show delete button
//                deleteTaskView.setVisibility(View.VISIBLE);
//                deleteTaskView.setOnClickListener((new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        db.deleteTask(Value);
//                        Toast.makeText(getApplicationContext(), "Deleted Successfully",
//                                Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                }));
//
//                titleView.setText((CharSequence)title);
//              //  titleView.setFocusable(false);
//
//                dateView.setText((CharSequence)date);
//               // dateView.setFocusable(false);
//
////                Calendar c = Calendar.getInstance();
////                Date d = new Date(dateView.getText().toString());
////                System.out.println(d);
////                c.setTime(d);
////
////                d.setTime();
////               // d.setTime(Long.parseLong(timeView.getText().toString()));
////                System.out.println(d);
//
////                System.out.println(dateView.getText().toString()+" "+timeView.getText().toString());
////                String time1 = dateView.getText().toString()+" "+timeView.getText().toString().replace(" ", "");
////
////                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
//////
//////                Date inputDate;
//////                try {
//////                    inputDate = simpleDateFormat.parse(time1.substring(0,time1.indexOf("PM")));
//////                    System.out.println(inputDate.getTime());
//////                    Calendar c = Calendar.getInstance();
//////                    c.setTimeInMillis(inputDate.getTime());
//////                } catch (ParseException e) {
//////                    e.printStackTrace();
//////                }
//
//
//
//                timeView.setText((CharSequence)time);
//                System.out.println("importance: " + importance);
//                switch (importance){
//                    case 1: highPriority.setChecked(true);
//                        System.out.println(" high importance: " + importance);
//                        radioGroup.check(R.id.highPriority);
//
//                        break;
//                    default: lowPriority.setChecked(true);
//                        radioGroup.check(R.id.lowPriority);
//
//                }
//
//
//            }
//        }
//
//        //Save Button
//        saveButtonView = (Button) findViewById(R.id.saveReminderButton);
//
//        saveButtonView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String title = titleView.getText().toString();
//                String date = dateView.getText().toString();
//                String time = timeView.getText().toString();
//                importance = radioGroup.getCheckedRadioButtonId() == R.id.highPriority? 1:0;
//
//                Bundle extras = getIntent().getExtras();
//                if (extras != null) {
//                    int Value = extras.getInt("id");
//                    System.out.println("id value :" + Value);
//                    if (Value > 0) { // update task
//                        System.out.println(importance);
//                        if (db.updateTask(id, title, time, date, importance)) {
//                           // createNotification();
//                            if (importance == 1)
//                                sendOnChannel1(title, "high importance", id);
//                            else
//                                sendOnChannel2(title, "low importance", id);
//                           // scheduleNotification(getNotification( dateView.getText().toString()) , Long.parseLong(timeView.toString())) ;
//                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
//                        }
//                    } else { // add new task
//                        System.out.println("inside adding new task");
//                        System.out.println("title: " + title);
//
//
//                        if (db.insertTask(title, time, date, importance)) {
//                            int id = db.numberOfRows() +1 ;
//                            if (importance == 1)
//                                sendOnChannel1(title, "high importance", id );
//                            else
//                                sendOnChannel2(title, "low importance", id );
//                          //  scheduleNotification(getNotification( dateView.getText().toString()) , Long.parseLong(timeView.toString())) ;
//                            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Not Added", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    finish();
//
//                    }
//
//                }
//
//        });
//
//
//        //Back button
//        backbuttonView = (Button) findViewById(R.id.backbutton);
//        backbuttonView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//
//
//        //show date dialog
//        dateView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog(999);
//            }
//        });
//
//        //show time dialog
//        timeView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog(0);
//
//
//            }
//        });
//
//    }
//
//
//    @SuppressWarnings("deprecation")
//    public void setDate(View view) {
//        showDialog(999);
//        Toast.makeText(getApplicationContext(), "ca",
//                Toast.LENGTH_SHORT)
//                .show();
//    }
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        // TODO Auto-generated method stub
//        if (id == 999) {
//            return new DatePickerDialog(this,
//                    myDateListener, year, month, day);
//        }
//        if (id ==0 ){
//            return new TimePickerDialog(this,
//                    timeListener, hour, min, false);
//        }
//        return null;
//    }
//
//    private DatePickerDialog.OnDateSetListener myDateListener = new
//            DatePickerDialog.OnDateSetListener() {
//                @Override
//                public void onDateSet(DatePicker arg0,
//                                      int arg1, int arg2, int arg3) {
//                    // TODO Auto-generated method stub
//                    // arg1 = year
//                    // arg2 = month
//                    // arg3 = day
//                    showDate(arg1, arg2+1, arg3);
//                }
//            };
//
//    private TimePickerDialog.OnTimeSetListener timeListener = new
//           TimePickerDialog.OnTimeSetListener() {
//                @Override
//                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                    showTime(hourOfDay,minute);
//                }
//           };
//
//
//
//
//
//    public void showTime(int hour, int min) {
//        String format;
//        if (hour == 0) {
//            hour += 12;
//            format = "AM";
//        } else if (hour == 12) {
//            format = "PM";
//        } else if (hour > 12) {
//            hour -= 12;
//            format = "PM";
//        } else {
//            format = "AM";
//        }
//
//        timeView.setText(new StringBuilder().append(hour).append(" : ").append(min)
//                .append(" ").append(format));
//    }
//
//    private void showDate(int year, int month, int day) {
//        dateView.setText(new StringBuilder().append(day).append("/")
//                .append(month).append("/").append(year));
//    }
//
//    public void onRadioButtonClicked(View view) {
//        // Is the button now checked?
//        boolean checked = ((RadioButton) view).isChecked();
//
//        // Check which radio button was clicked
//        switch(view.getId()) {
//            case R.id.highPriority:
//                if (checked)
//                 importance = 1;
//                    break;
//            case R.id.lowPriority:
//                if (checked)
//                  importance =0;
//                    break;
//        }
//    }
//
////
////    private void scheduleNotification (Notification notification , long delay) {
////        Intent notificationIntent = new Intent( this, NotificationPublisher. class ) ;
////        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION_ID , 1 ) ;
////        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION , notification) ;
////        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
////        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
////        assert alarmManager != null;
////        System.out.println("here in scheduleNotification");
////
////        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
////    }
////    private Notification getNotification (String content) {
////        Intent notifyIntent = new Intent(this, SecondActivity.class);
////        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
////        builder.setContentTitle( "Scheduled Notification" ) ;
////        builder.setContentText(content) ;
////        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
////        builder.setAutoCancel( true ) ;
////        builder.setContentIntent(pendingIntent);
////        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
////        System.out.println("returning builder");
////
////        return builder.build() ;
////    }
////
////
////    public void createNotification () {
////        Intent myIntent = new Intent(getApplicationContext() , NotifyService. class ) ;
////        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE ) ;
////        PendingIntent pendingIntent = PendingIntent. getService ( this, 0 , myIntent , 0 ) ;
////        Calendar calendar = Calendar. getInstance () ;
////        calendar.set(Calendar. SECOND , 0 ) ;
////        calendar.set(Calendar. MINUTE , 59 ) ;
////        calendar.set(Calendar. HOUR , 2 ) ;
////        calendar.set(Calendar. AM_PM , Calendar. PM ) ;
////        calendar.add(Calendar. DAY_OF_MONTH , 24 ) ;
////        System.out.println("Setting Alarm: ");
////        alarmManager.setRepeating(AlarmManager. RTC_WAKEUP , calendar.getTimeInMillis() , 1000 * 60 * 60 * 24 , pendingIntent) ;
////    }
//
//    public void sendOnChannel1(String title , String message , int id ){
//
//        String time1 = dateView.getText().toString()+" "+timeView.getText().toString().replace(" ", "");
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
//
//                Date inputDate;
//                Calendar c = Calendar.getInstance();
//                try {
//                    inputDate = simpleDateFormat.parse(time1.substring(0,time1.indexOf("PM")));
//                    System.out.println(inputDate.getTime());
//                    c = Calendar.getInstance();
//                    c.setTimeInMillis(inputDate.getTime());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(this, AlertReceiver1.class);
//        intent.putExtra("title", title);
//        intent.putExtra("message",message);
//        intent.putExtra("id",id);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
//
//
//        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
//        NotificationCompat.Builder nb = mNotificationHelper.getChannel1Notification(title,message,id);
//        mNotificationHelper.getManager().notify(1,nb.build());
//    }
//
//    public void sendOnChannel2(String title , String message, int id ){
//        NotificationCompat.Builder nb = mNotificationHelper.getChanne21Notification(title,message,id);
//        mNotificationHelper.getManager().notify(2,nb.build());
//    }
//}
package com.example.reminderapplication;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    private static int id;

    Button backbuttonView, saveButtonView, deleteTaskView;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private static EditText titleView;
    private String tit;

    public static int importance;
    private RadioGroup radioGroup;
    private RadioButton highPriority , lowPriority;
    DatabaseHelper db;
    private NotificationHelper mNotificationHelper;


    private Calendar calendar;
    private Calendar calendar2;
    private TextView dateView;
    private TextView timeView;


    private int year, month, day, hour ,min ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        Toast.makeText(getApplicationContext(), "in second activity", Toast.LENGTH_SHORT).show();

        db= new DatabaseHelper(this);
        titleView = (EditText) findViewById(R.id.editTitle);
        dateView = (TextView) findViewById(R.id.dateView);
        timeView = (TextView) findViewById(R.id.timeView);
        radioGroup = (RadioGroup) findViewById(R.id.PriorityRadioGroup);
        saveButtonView = (Button) findViewById(R.id.saveReminderButton);
        deleteTaskView =(Button) findViewById(R.id.deleteTaskButton);
        lowPriority = (RadioButton) findViewById(R.id.lowPriority);
        highPriority = (RadioButton) findViewById(R.id.lowPriority);
        id =0;
        calendar = Calendar.getInstance();
        calendar2 = Calendar.getInstance();


        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
        showDate(year, month+1, day);

        mNotificationHelper = new NotificationHelper(this);

        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");
            id = Value;
            if (Value == 0 )
                deleteTaskView.setVisibility(View.GONE);

            if(Value>0){

                //means this is the view part not the add contact part.

                Cursor rs = db.retrieveTask(Value);
                id  = Value;
                rs.moveToFirst();

                String title = rs.getString(rs.getColumnIndex(DatabaseHelper.col_2));
                String time = rs.getString(rs.getColumnIndex(DatabaseHelper.col_3));
                String date = rs.getString(rs.getColumnIndex(DatabaseHelper.col_4));
                importance = rs.getInt(rs.getColumnIndex(DatabaseHelper.col_5));
                System.out.println(importance);
                if (!rs.isClosed())  {
                    rs.close();
                }

                // show delete button
                deleteTaskView.setVisibility(View.VISIBLE);
                deleteTaskView.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        db.deleteTask(Value);
                        Toast.makeText(getApplicationContext(), "Deleted Successfully",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }));

                titleView.setText((CharSequence)title);
                //  titleView.setFocusable(false);

                dateView.setText((CharSequence)date);
                // dateView.setFocusable(false);

                timeView.setText((CharSequence)time);
                System.out.println("importance: " + importance);
                switch (importance){
                    case 1: highPriority.setChecked(true);
                        System.out.println(" high importance: " + importance);
                        radioGroup.check(R.id.highPriority);

                        break;
                    default: lowPriority.setChecked(true);
                        radioGroup.check(R.id.lowPriority);

                }


            }
        }

        //Save Button
        saveButtonView = (Button) findViewById(R.id.saveReminderButton);

        saveButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleView.getText().toString();
                String date = dateView.getText().toString();
                String time = timeView.getText().toString();
                importance = radioGroup.getCheckedRadioButtonId() == R.id.highPriority? 1:0;


                String time1 = dateView.getText().toString()+" "+timeView.getText().toString().replace(" ", "");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");

                Date inputDate;
                Calendar c = Calendar.getInstance();
                try {
                    inputDate = simpleDateFormat.parse(time1.substring(0,time1.indexOf("PM")));
                    System.out.println(inputDate.getTime());
                    c = Calendar.getInstance();
                    c.setTimeInMillis(inputDate.getTime());
                }
                catch (ParseException e) {
                    e.printStackTrace();
                }



                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    int Value = extras.getInt("id");
                    System.out.println("id value :" + Value);
                    if (Value > 0) { // update task
                        System.out.println(importance);
                        if (db.updateTask(id, title, time, date, importance)) {
                            // createNotification();
                            if (importance == 1)
                                sendOnChannel1(c, id);
                            else
                                sendOnChannel2(c,id);
                            // scheduleNotification(getNotification( dateView.getText().toString()) , Long.parseLong(timeView.toString())) ;
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                        }
                    } else { // add new task
                        System.out.println("inside adding new task");
                        System.out.println("title: " + title);

                        int newId = db.numberOfRows() +1;
                        if (db.insertTask(title, time, date, importance)) {
                            if (importance == 1)
                                sendOnChannel1(c,newId);
                            else
                                sendOnChannel2(c,newId);
                            //  scheduleNotification(getNotification( dateView.getText().toString()) , Long.parseLong(timeView.toString())) ;
                            Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(), "Not Added", Toast.LENGTH_SHORT).show();
                        }
                    }

                    finish();

                }

            }

        });


        //Back button
        backbuttonView = (Button) findViewById(R.id.backbutton);
        backbuttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        //show date dialog
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(999);
            }
        });

        //show time dialog
        timeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(0);


            }
        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        Toast.makeText(getApplicationContext(), "ca",
                Toast.LENGTH_SHORT)
                .show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        if (id ==0 ){
            return new TimePickerDialog(this,
                    timeListener, hour, min, false);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private TimePickerDialog.OnTimeSetListener timeListener = new
            TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    showTime(hourOfDay,minute);

                }
            };





    public void showTime(int hour, int min) {
        String format;
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        timeView.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));


        calendar2.set(Calendar.MINUTE,min);
        calendar2.set(Calendar.HOUR,hour);
        calendar2.set(Calendar.SECOND,0);
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));

        calendar2.set(Calendar.YEAR,year);
        calendar2.set(Calendar.MONTH,month-1);
        calendar2.set(Calendar.DAY_OF_MONTH,day);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.highPriority:
                if (checked)
                    importance = 1;
                break;
            case R.id.lowPriority:
                if (checked)
                    importance =0;
                break;
        }
    }

//
//    private void scheduleNotification (Notification notification , long delay) {
//        Intent notificationIntent = new Intent( this, NotificationPublisher. class ) ;
//        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION_ID , 1 ) ;
//        notificationIntent.putExtra(NotificationPublisher. NOTIFICATION , notification) ;
//        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;
//        assert alarmManager != null;
//        System.out.println("here in scheduleNotification");
//
//        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
//    }
//    private Notification getNotification (String content) {
//        Intent notifyIntent = new Intent(this, SecondActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder( this, default_notification_channel_id ) ;
//        builder.setContentTitle( "Scheduled Notification" ) ;
//        builder.setContentText(content) ;
//        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
//        builder.setAutoCancel( true ) ;
//        builder.setContentIntent(pendingIntent);
//        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
//        System.out.println("returning builder");
//
//        return builder.build() ;
//    }
//
//
//    public void createNotification () {
//        Intent myIntent = new Intent(getApplicationContext() , NotifyService. class ) ;
//        AlarmManager alarmManager = (AlarmManager) getSystemService( ALARM_SERVICE ) ;
//        PendingIntent pendingIntent = PendingIntent. getService ( this, 0 , myIntent , 0 ) ;
//        Calendar calendar = Calendar. getInstance () ;
//        calendar.set(Calendar. SECOND , 0 ) ;
//        calendar.set(Calendar. MINUTE , 59 ) ;
//        calendar.set(Calendar. HOUR , 2 ) ;
//        calendar.set(Calendar. AM_PM , Calendar. PM ) ;
//        calendar.add(Calendar. DAY_OF_MONTH , 24 ) ;
//        System.out.println("Setting Alarm: ");
//        alarmManager.setRepeating(AlarmManager. RTC_WAKEUP , calendar.getTimeInMillis() , 1000 * 60 * 60 * 24 , pendingIntent) ;
//    }

    public void sendOnChannel1(Calendar c, int id){

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(this,NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1 ,intent1,0);


//        String time1 = dateView.getText().toString()+" "+timeView.getText().toString().replace(" ", "");
//        intent1.putExtra("id",id);
//        Bundle dataBundle = new Bundle();
//        dataBundle.putInt("id", id);
//        System.out.println(dataBundle.getInt("id"));
//        intent1.putExtras(dataBundle);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
//
//        Date inputDate;
//        Calendar c1 = Calendar.getInstance();
//        try {
//            inputDate = simpleDateFormat.parse(time1.substring(0,time1.indexOf("PM")));
//            System.out.println(inputDate.getTime());
//            c1 = Calendar.getInstance();
//            c1.setTimeInMillis(inputDate.getTime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("Setting Alarm: ");
        if (calendar2.before(Calendar.getInstance())) {
            calendar2.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent);

    }

    public void sendOnChannel2(Calendar c, int id){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(this,NotificationPublisher.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,2 ,intent1,0);
//        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent intent1 = new Intent(this,NotificationPublisher.class);
//        intent1.putExtra("id",id);
//        Bundle dataBundle = new Bundle();
//        dataBundle.putInt("id", id);
//        System.out.println(dataBundle.getInt("id"));
//        intent1.putExtras(dataBundle);
//                String time1 = dateView.getText().toString()+" "+timeView.getText().toString().replace(" ", "");
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
//
//                Date inputDate;
//                Calendar c1 = Calendar.getInstance();
//                try {
//                    inputDate = simpleDateFormat.parse(time1.substring(0,time1.indexOf("PM")));
//                    System.out.println(inputDate.getTime());
//                    c1 = Calendar.getInstance();
//                    c1.setTimeInMillis(inputDate.getTime());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }

       // PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1 ,intent1,0);

//        System.out.println("Setting Alarm: ");
        if (calendar2.before(Calendar.getInstance())) {
            calendar2.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),pendingIntent);
    }

    public static String getTit(){

        return  titleView.getText().toString();
    }
    public static int getImportance(){

        return importance;
    }

    public static int getID(){

        return id;
    }



}