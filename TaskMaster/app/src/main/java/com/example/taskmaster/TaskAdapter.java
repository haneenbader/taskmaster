package com.example.taskmaster;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Todo;

import java.util.ArrayList;
import java.util.List;

// 1- Create the class without extensions ..
// 6- Extend RecyclerView.Adapter - StudentAdapter.StudentViewHolder
public class TaskAdapter extends  RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    // 2- create the list the the adapter will use to bind data
    List<Todo> allTask = new ArrayList<>();
    // generate constructor
    public TaskAdapter(List<Todo> allTask) {
        this.allTask = allTask;
    }



    // 3- create the ViewHolder class (Wraps the data and the view)
    public  static  class TaskViewHolder extends RecyclerView.ViewHolder {
        // 4- The model object
        public Todo task ;
        // 5- view object
        View itemView ;
        // + setting the itemView value
        //generate constructor
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;

            itemView.findViewById(R.id.titletaskfragment).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent toTaskDetailsPage = new Intent(v.getContext() ,TaskDetail.class);
                    toTaskDetailsPage.putExtra("title",task.getTitle());
                    toTaskDetailsPage.putExtra("body",task.getDescription());
                    toTaskDetailsPage.putExtra("state",task.getState());
                    v.getContext().startActivity(toTaskDetailsPage);
                }
            });
        }
    }


//    impliment after extend class
    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 7- create the view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_item,parent,false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(view);
        return taskViewHolder;
    }
//    impliment after extend class
    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        holder.task = allTask.get(position);
        Button titleButton = holder.itemView.findViewById(R.id.titletaskfragment);
        titleButton.setText(holder.task.getTitle());

    }


//    impliment after extend class
    @Override
    public int getItemCount() {
        return allTask.size();
    }

}
