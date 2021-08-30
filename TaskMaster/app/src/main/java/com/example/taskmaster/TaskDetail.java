package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

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

       //get value from intent

        TextView textViewTitle = findViewById(R.id.textViewtitle);
        String taskTitle = getIntent().getExtras().getString("taskTitle");
        textViewTitle.setText(taskTitle);
    }
}