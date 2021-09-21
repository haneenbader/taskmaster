package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;

import java.io.File;

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

       //get data from dataBase room
//        AppDatabase appDatabase =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database_task").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//        TaskDao taskDao = appDatabase.taskDao();
          Intent intent = getIntent();
//          Task task = taskDao.findById(intent.getExtras().getInt("id"));
        TextView titleText = findViewById(R.id.textViewtitle2);
        System.out.println(R.id.textViewtitle2);
        TextView stateText = findViewById(R.id.textViewstate2);
        TextView BodyText = findViewById(R.id.textViewBody);
        ImageView imgTask = findViewById(R.id.imgTask);

//         to get data from adapter
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String state= intent.getStringExtra("state");


        titleText.setText(title);
        stateText.setText(state);
        BodyText.setText(body);

      //download img from storage
//        Amplify.Storage.downloadFile(
//                //get img from intent
//                intent.getExtras().getString("img"),
//                new File(getApplicationContext().getFilesDir() + "/download.jpg"),
//                result -> {
//                //  target imageView
//                    ImageView imgTask = findViewById(R.id.imgTask);
//                    String newImg = result.getFile().getPath();
//                    imgTask.setImageBitmap(BitmapFactory.decodeFile(newImg));
//
//                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile());},
//                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
//        );
        Amplify.Storage.downloadFile(

   title,
//                intent.getExtras().getString("taskImg"),
//                "taskImg",
                new File(getApplicationContext().getFilesDir() + "/download.jpg"),
                result -> {
                    Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName());

                    imgTask.setImageBitmap(BitmapFactory.decodeFile(result.getFile().getPath()));
                },
                error -> Log.e("MyAmplifyApp",  "Download Failure", error)
        );

    }

}