package com.example.taskmaster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Amplify.Auth.signOut(
                () -> Log.i("AuthQuickstart", "Signed out successfully"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
            }
        });



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

        try {
            // Add these lines to add the AWSApiPlugin plugins
            Amplify.addPlugin(new AWSApiPlugin() );
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(new AWSCognitoAuthPlugin() );
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch ( AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }

//        Button signIn = findViewById(R.id.signIn);
//        signIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//sing up activity (replace email , username , passwoord)
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), "haneenalwatan993@gmail.com")
                .build();
//        Amplify.Auth.signUp("haneen", "Pass123456", options,
//                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
//                error -> Log.e("AuthQuickStart", "Sign up failed", error)
//        );

//        Amplify.Auth.confirmSignUp(
//                "haneen",
//                "855565",
//                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
//                error -> Log.e("AuthQuickstart", error.toString())
//        );
        Amplify.Auth.signInWithWebUI(
                this,
                result -> Log.i("AuthQuickStart", result.toString()),
                error -> Log.e("AuthQuickStart", error.toString())
        );


//        ArrayList<Task> AllTask = new ArrayList<Task>();
//        AllTask.add(new Task("Submit lab27","submit it after add readme.md","new"));
//        AllTask.add(new Task("Solve lab28","all requirement is done as well","complete"));
//        AllTask.add(new Task("Edit CC27","rewrite white board","in progress"));

//        get data from database room
//        AppDatabase appDatabase =  Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "database_task").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//        TaskDao taskDao = appDatabase.taskDao();
//
//        List<Task> task = taskDao.getAll();

        List<Todo> AllTask =new ArrayList<>();
        RecyclerView allTaskRecycleView = findViewById(R.id.taskrecycleview);


        Handler handler = new Handler( Looper.getMainLooper(),
                new Handler.Callback(){
                    @Override
                    public boolean handleMessage(@NonNull Message message){
                        allTaskRecycleView.getAdapter().notifyDataSetChanged();
                        return false ;
                    }

                });
                Amplify.API.query(
                ModelQuery.list(Todo.class ),
                response -> {
                    for (Todo todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getId());
                        Log.i("MyAmplifyApp", todo.getTitle());
                        Log.i("MyAmplifyApp", todo.getDescription());
                        Log.i("MyAmplifyApp", todo.getState());
                        AllTask.add(todo);
                    }
                    handler.sendEmptyMessage(1);

                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        allTaskRecycleView.setLayoutManager(new LinearLayoutManager(this));
        allTaskRecycleView.setAdapter(new TaskAdapter(AllTask));

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }
}