package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddTask extends AppCompatActivity {
    AppDatabase appDatabase ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);

        // target to button add task
        Button addTask = findViewById(R.id.addtaskbutton);
//        target data from input field
        EditText title = findViewById(R.id.tastTitle);
        EditText  body = findViewById(R.id.taskDescription);
        EditText state = findViewById(R.id.taskState);
        //add eventListener

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_SHORT).show();
//                take data from input to database
                  appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task").allowMainThreadQueries().fallbackToDestructiveMigration().build();
                TaskDao taskDao = appDatabase.taskDao();
                Task task =new Task(title.getText().toString() ,body.getText().toString() , state.getText().toString());
                taskDao.insertAll(task);
            }
        });

        // target to button home page
        Button homePage = findViewById(R.id.tohomepage);
        //add eventListener
        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToHomePage = new Intent(AddTask.this , MainActivity.class);
                startActivity(goToHomePage);
            }
        });

    }
}