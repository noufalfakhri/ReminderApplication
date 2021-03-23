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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button buttonView;
    TextView noTasksView ;
    public static final int msg_request = 1;
    DatabaseHelper db;
    private ListView taskList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonView = (Button) findViewById(R.id.createReminder);
        noTasksView = (TextView) findViewById(R.id.emptyRemindersView);
        db = new DatabaseHelper(this);

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ssd", "onClick: Creating Reminder");
                Intent intent = new Intent (getApplicationContext() , SecondActivity.class);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", 0);
                intent.putExtras(dataBundle);
                startActivityForResult(intent, msg_request);

            }
        });

    }

//    @Override
//    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == msg_request) {
//            if (resultCode == RESULT_OK) {
//                viewData();
//
//            }
//        }
//    }


    @Override
    protected void onStart() {
        super.onStart();
        viewData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewData();
    }
    public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }


    public void viewData(){


        ArrayList<HashMap<String, String>> array_list = db.getAllTasks();
        int DbRow= db.numberOfRows();
        System.out.println(DbRow + " NUMbeR OF ROWS");
        if (DbRow < 1 ) {
            noTasksView.setVisibility(View.VISIBLE);
            return;
        }

        if (DbRow >= 1 ) { //  TASKS AVAILABLE
            noTasksView.setVisibility(View.GONE);


            ListAdapter arrayAdapter = new SimpleAdapter(this,array_list, R.layout.list_row, new String[]{"ID","Title",}, new int[]{R.id.taskID, R.id.taskTitle});
          //  ArrayAdapter arrayAdapter=new ArrayAdapter(this,R.layout.list_row, array_list);
            //arrayAdapter.notifyDataSetChanged();
            taskList = (ListView) findViewById(R.id.listView);

            taskList.setAdapter(arrayAdapter);

            taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                    // TODO Auto-generated method stub
                    // Display Task
                    System.out.println("ID: "+  array_list.get(arg2));

                    System.out.println("ID: "+  array_list.get(arg2).get("ID"));


                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", Integer.parseInt(array_list.get(arg2).get("ID")));

                    Intent intent = new Intent(getApplicationContext(), SecondActivity.class);

                    intent.putExtras(dataBundle);
                    startActivityForResult(intent, msg_request);
                }
            });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
