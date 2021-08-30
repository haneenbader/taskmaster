package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // target to button add task
        Button addTask = findViewById(R.id.addtaskhome);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToAddTasks = new Intent(MainActivity.this, AddTask.class);
                startActivity(goToAddTasks);
            }
        });



        // target to button all task
        Button allTask = findViewById(R.id.alltask);
        //add eventListener
        allTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent goToAllTasks = new Intent(MainActivity.this, AllTask.class);
                startActivity(goToAllTasks);
            }
        });

        //put value from button
        Button task1 = findViewById(R.id.task1);
        task1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task1Title  = task1.getText().toString();
                Intent goToTask1Detail = new Intent(MainActivity.this, TaskDetail.class);
                goToTask1Detail.putExtra("taskTitle",task1Title);
                startActivity(goToTask1Detail);
            }
        });

        Button task2 = findViewById(R.id.task2);
        task2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task2Title  = task2.getText().toString();
                Intent goToTask2Detail = new Intent(MainActivity.this, TaskDetail.class);
                goToTask2Detail.putExtra("taskTitle",task2Title);
                startActivity(goToTask2Detail);
            }
        });

        Button task3 = findViewById(R.id.task3);
        task3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task3Title  = task3.getText().toString();
                Intent goToTask3Detail = new Intent(MainActivity.this, TaskDetail.class);
                goToTask3Detail.putExtra("taskTitle",task3Title);
                startActivity(goToTask3Detail);
            }
        });

        // target to button all task
        Button setting = findViewById(R.id.hometosetting);
        //add eventListener
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V) {
                Intent goToSetting = new Intent(MainActivity.this, SettingsPage.class);
                startActivity(goToSetting);
            }
        });
    }


//    @Override
//    protected void onStart() {
//        super.onStart();
//        Toast.makeText(getApplicationContext(), "Override onStart()", Toast.LENGTH_SHORT).show();
//    }
    @Override
    protected void onResume() {
        super.onResume();
        String userTaskMessage = "’s tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("userName","user");

        TextView textViewUserName = findViewById(R.id.textViewusername);
        textViewUserName.setText(userName +userTaskMessage );
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Toast.makeText(getApplicationContext(), "Override onPause()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Toast.makeText(getApplicationContext(), "Override onStop()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Toast.makeText(getApplicationContext(), "Override onRestart()", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(getApplicationContext(), "Override onDestroy()", Toast.LENGTH_SHORT).show();
//    }

}