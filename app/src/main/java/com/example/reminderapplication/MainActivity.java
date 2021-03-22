package com.example.reminderapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button buttonView;
    TextView noTasksView ;
    DatabaseHelper db;
    private ListView taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonView = (Button) findViewById(R.id.createReminder);
        noTasksView = (TextView) findViewById(R.id.emptyRemindersView);
       db = new DatabaseHelper(this);
        ArrayList array_list = db.getAllTasks();
        int DbRow= db.numberOfRows();

        if (DbRow != 0 ){ //  TASKS AVAILABLE
            noTasksView.setVisibility(View.VISIBLE);
        }
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
        taskList = (ListView) findViewById(R.id.listView);
        taskList.setAdapter(arrayAdapter);
        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                // Display Task
                int id  = (arg2 + 1);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);

                Intent intent = new Intent(getApplicationContext(),SecondActivity.class);

                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ssd", "onClick: Creating Reminder");
                Intent intent = new Intent (getApplicationContext() , SecondActivity.class);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                intent.putExtras(dataBundle);
                startActivity(intent);

            }
        });
    }



    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }
    }
