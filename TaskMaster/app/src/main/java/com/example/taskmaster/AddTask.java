package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);

        // target to button add task
        Button addTask = findViewById(R.id.addtaskbutton);
        //add eventListener

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_SHORT).show();
            }
        });

        // target to button home page
        Button homePage = findViewById(R.id.homebutton);
        //add eventListener
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePage = new Intent(AddTask.this , HomePage.class);
                startActivity(goToHomePage);
            }
        });

    }
}