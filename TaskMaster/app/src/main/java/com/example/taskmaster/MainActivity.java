package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

//        ArrayList<Task> AllTask = new ArrayList<Task>();
//        AllTask.add(new Task("Submit lab27","submit it after add readme.md","new"));
//        AllTask.add(new Task("Solve lab28","all requirement is done as well","complete"));
//        AllTask.add(new Task("Edit CC27","rewrite white board","in progress"));

//        get data from database
        AppDatabase appDatabase =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database_task").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        TaskDao taskDao = appDatabase.taskDao();

        List<Task> task = taskDao.getAll();

        RecyclerView allTaskRecycleView = findViewById(R.id.taskrecycleview);
        allTaskRecycleView.setLayoutManager(new LinearLayoutManager(this));
        allTaskRecycleView.setAdapter(new TaskAdapter(task));


}


    @Override
    protected void onResume() {
        super.onResume();
        String userTaskMessage = "’s tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("userName","user");

        TextView textViewUserName = findViewById(R.id.textViewusername);
        textViewUserName.setText(userName +userTaskMessage );
    }

}