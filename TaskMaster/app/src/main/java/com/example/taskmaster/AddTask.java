package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Team;
import com.amplifyframework.datastore.generated.model.Todo;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private  Intent pickImg ;
    String img ="";
    Team team = null;
    AppDatabase appDatabase ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);

        List<Team> teams = new ArrayList<>();

        Amplify.API.query(
                ModelQuery.list(Team.class),
                response -> {
                    for (Team todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getName());
                        teams.add(todo);

                    }
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );
        RadioButton team1 = findViewById(R.id.team1AddTask);
        RadioButton team2     = findViewById(R.id.team2AddTask);
        RadioButton team3      = findViewById(R.id.team3AddTask);
        String name = "";
        if (team1.isChecked()) {
            name = "team1";
        } else if (team2.isChecked()) {
            name = "team2";
        } else if (team3.isChecked()) {
            name = "team3";
        }


        for (int i = 0; i < teams.size(); i++) {
            System.out.println(teams.get(i)+"==========================================");
            if (teams.get(i) != null) {
                if (teams.get(i).getName().equals(name)) {
                    team = teams.get(i);
                }
            }
        }

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
                if (team != null) {
                    Todo todo = Todo.builder()
                            .title(title.getText().toString())
                            .description(body.getText().toString())
                            .state(state.getText().toString())
                            .img(img)
                            .team(team)
                            .build();

                    System.out.println(team + "=======================================");
                    System.out.println(img + "=======================================");

                    Amplify.API.mutate(
                            ModelMutation.create(todo),
                            response -> Log.i("MyAmplifyApp", "Added Todo with id: " + response.getData().getId()),
                            error -> Log.e("MyAmplifyApp", "Create failed", error)
                    );


                }
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
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFile();
            }
        });

    }
    // method to pick file from mobile system
    private void pickFile(){
        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.setType("*/*");  //any type of file
        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
//        startActivity(chooseFile);
        startActivityForResult(chooseFile, 1234);
    }
    // upload img from input stream to amplify

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (data!=null){

        try {
            //getdata from intent and set it inside inputStream
            img = data.getData().toString();
            System.out.println(img +"======================================================");
            InputStream imgInputStream = getContentResolver().openInputStream(data.getData());

            //add data to amplify
            Amplify.Storage.uploadInputStream(
                    "taskImg",
                    imgInputStream,
                    result -> Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey()),
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        } catch (FileNotFoundException error) {
            Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
        }
      }
//    }
}