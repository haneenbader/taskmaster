package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;

public class AddTask extends AppCompatActivity {
    private  Intent pickImg ;


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
//                  appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database_task").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//                TaskDao taskDao = appDatabase.taskDao();
//                Task task =new Task(title.getText().toString() ,body.getText().toString() , state.getText().toString());
//                taskDao.insertAll(task);

                Todo todo = Todo.builder()
                        .title(title.getText().toString())
                        .description(body.getText().toString())
                        .state(state.getText().toString())
                        .build();



                Amplify.API.mutate(
                        ModelMutation.create(todo),
                        response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                        error -> Log.e("MyAmplifyApp", "Create failed", error)
                );



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

        Button uploadImg = findViewById(R.id.uploadImg);
        uploadImg.setOnClickListener(v -> {

            pickImg = new Intent(Intent.ACTION_GET_CONTENT);
            pickImg.setType("*/*");
            pickImg = Intent.createChooser(pickImg, "pick image");
            startActivityForResult(pickImg, 1234);


        });

    }
}