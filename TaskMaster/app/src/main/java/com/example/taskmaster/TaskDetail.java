package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        // target to button home page
        Button homePage = findViewById(R.id.tohomepage);
        //add eventListener
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePage = new Intent(TaskDetail.this , MainActivity.class);
                startActivity(goToHomePage);
            }
        });

       //get data from dataBase

        AppDatabase appDatabase =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database_task").allowMainThreadQueries().fallbackToDestructiveMigration().build();
        TaskDao taskDao = appDatabase.taskDao();
        Intent intent = getIntent();
        Task task = taskDao.findById(intent.getExtras().getInt("id"));

        TextView titleText = findViewById(R.id.textViewtitle2);
        System.out.println(R.id.textViewtitle2);
        TextView stateText = findViewById(R.id.textViewstate2);
        TextView BodyText = findViewById(R.id.textViewBody);

        titleText.setText(task.title);
        stateText.setText(task.state);
        BodyText.setText(task.body);


    }
}