package com.example.reminderapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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

import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

import java.sql.Time;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {
    Button backbuttonView, saveButtonView, deleteTaskView;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private EditText titleView;
    private String tit;
    private int importance;
    private RadioGroup radioGroup;
    private RadioButton highPriority , lowPriority;
    DatabaseHelper db;


    private Calendar calendar;
    private TextView dateView;
    private TextView timeView;
    private int id=0;

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

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
        showDate(year, month+1, day);


        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            int Value = extras.getInt("id");
            if(Value>0){
                //means this is the view part not the add contact part.


                Cursor rs = db.retrieveTask(Value);
                id  = Value;
                rs.moveToFirst();

                String title = rs.getString(rs.getColumnIndex(DatabaseHelper.col_2));
                String time = rs.getString(rs.getColumnIndex(DatabaseHelper.col_3));
                String date = rs.getString(rs.getColumnIndex(DatabaseHelper.col_4));
                Integer importance = rs.getInt(rs.getColumnIndex(DatabaseHelper.col_5));

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
                titleView.setFocusable(false);

                dateView.setText((CharSequence)date);
                dateView.setFocusable(false);

                timeView.setText((CharSequence)time);
                timeView.setFocusable(false);

                if(importance ==1)
                    highPriority.setChecked(true);
                else
                if(importance ==0)
                   lowPriority.setChecked(true);

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
                int important = radioGroup.getCheckedRadioButtonId() == R.id.lowPriority ? 0 : 1;
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    int Value = extras.getInt("id");
                    if (Value > 0) { // update task
                        if (db.updateTask(id, title, time, date, important)) {
                            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                            //Return activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
                        }


                        if (Value == 0) { // add new task
                            Log.i("s", "onClick: Saving new Reminder");

                            if (db.insertTask(title, time, date, important)) {
                                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getApplicationContext(), "Not Added", Toast.LENGTH_SHORT).show();
                            }

                            //Return activity
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }

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

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.lowPriority) {
                    importance =0;
                } else if(checkedId == R.id.highPriority) {
                    importance = 1;
                }
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
    }

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
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
}