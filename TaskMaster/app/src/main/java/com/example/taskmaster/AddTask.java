package com.example.taskmaster;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddTask extends AppCompatActivity {
    private  Intent pickImg ;
    String img ="";

    AppDatabase appDatabase ;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION}, 1234);

            boolean x = ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
                    &&
                    ActivityCompat
                            .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED;


            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            double longitude = location.getLongitude();
                            double latitude = location.getLatitude();

                        }
                    }

                });

//        Team team = new Team.Builder()
//                .name("team2")
//                .build();
//
//
//        Amplify.API.mutate(ModelMutation.create(team),
//                response -> Log.i("MyAmplifyApp", "Team with id: " + response.getData().getId()),
//                error -> Log.e("MyAmplifyApp", "Create failed", error)
//        );
    }

@Override
protected void onStart() {
    super.onStart();

    Intent intent = getIntent();
        if (intent.getType() != null && intent.getType().equals("text/plain")){
            EditText desc = findViewById(R.id.taskDescription);
            desc.setText(intent.getExtras().get(Intent.EXTRA_TEXT).toString());
        }
//---------------------------------------
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

                // target to button add task
        Button addTask = findViewById(R.id.addtaskbutton);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "submitted!", Toast.LENGTH_SHORT).show();
//                take data from input to database
//                  appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database_task").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//                TaskDao taskDao = appDatabase.taskDao();
//                Task task =new Task(title.getText().toString() ,body.getText().toString() , state.getText().toString());
//                taskDao.insertAll(task);

//        target data from input field
                EditText title = findViewById(R.id.tastTitle);
                EditText body = findViewById(R.id.taskDescription);
                EditText state = findViewById(R.id.taskState);


                RadioButton team1 = findViewById(R.id.team1AddTask);
                RadioButton team2 = findViewById(R.id.team2AddTask);
                RadioButton team3 = findViewById(R.id.team3AddTask);
                String name = "";
                if (team1.isChecked()) {
                    name = "team1";
                } else if (team2.isChecked()) {
                    name = "team2";
                } else if (team3.isChecked()) {
                    name = "team3";
                }
                System.out.println(name+"==================================================");
                Team team = null;
                for (int i = 0; i < teams.size(); i++) {
//                    System.out.println(teams.get(i) + "==========================================");
                    if (teams.get(i).getName().equals(name)) {
                        team = teams.get(i);
                        break;
                    }
                }
                System.out.println(team.getName()+"8888888888888888888888888888888888888888888888");
                Todo todo = Todo.builder()
                        .title(title.getText().toString())
                        .description(body.getText().toString())
                        .state(state.getText().toString())
                        .img(img)
                        .team(team)
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

}