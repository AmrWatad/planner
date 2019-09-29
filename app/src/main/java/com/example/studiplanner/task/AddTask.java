package com.example.studiplanner.task;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studiplanner.R;
import com.example.studiplanner.course.CourseView;

import static com.example.studiplanner.fragments.Courses.adapter;
import static com.example.studiplanner.fragments.Courses.courses;
import static com.example.studiplanner.fragments.Courses.mListView;
import static com.example.studiplanner.fragments.Tasks.adapter1;
import static com.example.studiplanner.fragments.Tasks.mListView1;
import static com.example.studiplanner.task.BottomSheetFragment.adapterTask;
import static com.example.studiplanner.task.BottomSheetFragment.mListViewTask;
import static com.example.studiplanner.task.BottomSheetFragment.tasks;

public class AddTask extends AppCompatActivity {
        EditText task,details;
        TextView course,date;
    FrameLayout save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        task=findViewById(R.id.e_task_name);
        course=findViewById(R.id.t_course);
        date=findViewById(R.id.add_date);
          details=findViewById(R.id.e_details);
        save=findViewById(R.id.frame_save);
        Intent in=getIntent();
        Bundle bundle = getIntent().getBundleExtra("xy");   //<< get Bundle from Intent
        String value = bundle.getString("msg");
        if(value.equals("edit")){
            int position = bundle.getInt("position");
            TaskView taskView=tasks.get(position);
            task.setText(taskView.getName());
             details.setText(taskView.getDetails());
            Toast.makeText(getBaseContext(),"edit="+position,Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(task.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(),"write course name!",Toast.LENGTH_LONG).show();
                    else{
                        tasks.get(position).setName(task.getText().toString());
                        tasks.get(position).setDetails(details.getText().toString());
                        /*mListViewTask.setAdapter(adapterTask);
                        adapterTask.notifyDataSetChanged();*/
                        mListView1.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();
                        if ( getFragmentManager().getBackStackEntryCount() > 0)
                        {
                            getFragmentManager().popBackStack();
                            return;
                        }
                        AddTask.super.onBackPressed();
                    }
                }
            });
        }
        else {
            Toast.makeText(getBaseContext(),"noot edit!!",Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(task.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(),"write course name!",Toast.LENGTH_LONG).show();
                    else{
                        tasks.add(new TaskView(task.getText().toString() ,details.getText().toString()));
                        mListView1.setAdapter(adapter1);
                        adapter1.notifyDataSetChanged();
                        if ( getFragmentManager().getBackStackEntryCount() > 0)
                        {
                            getFragmentManager().popBackStack();
                            return;
                        }
                        AddTask.super.onBackPressed();
                    }
                }
            });
        }


    }
}
