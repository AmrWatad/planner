package com.example.studiplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.studiplanner.course.AddCourse;
import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.course.CoursesBaseAdapter;
import com.example.studiplanner.task.AddTask;
import com.example.studiplanner.task.BottomSheetFragment;
import com.example.studiplanner.task.TaskBaseAdapter;
import com.example.studiplanner.task.TaskView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studiplanner.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.studiplanner.MainActivity.tasks;
//import static com.example.studiplanner.task.BottomSheetFragment.tasks;

public class Tasks extends Fragment {
    ConstraintLayout constraintLayout;
    public static TextView taskName;
    FloatingActionButton plus;
    public  static ListView mListView1;
     public  static TaskBaseAdapter adapter1;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          v =  inflater.inflate(R.layout.fragment_tasks, container, false);
        initListView();
          constraintLayout=v.findViewById(R.id.cns_task);
        taskName=v.findViewById(R.id.t_task_name);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MenuItem menuItem = null;
                mOnNavigationItemSelectedListener.onNavigationItemSelected(menuItem);
            }
        });
        plus=v.findViewById(R.id.fab);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("msg","save");
                Intent intent=new Intent(getActivity(), AddTask.class);
                intent.putExtra("xy", bundle);
                getActivity().startActivity(intent);
            }
        });


        return v;
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item ->
    {
         BottomSheetFragment bf = new BottomSheetFragment();
        bf.show(getActivity().getSupportFragmentManager(), bf.getTag());
        return true;
    };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            String editTextValue = savedInstanceState.getString("EditText");
            Log.i(TAG, "Found edit text value: " + editTextValue);

        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
      }
    public void initListView() {
        mListView1 = v.findViewById(R.id.lv_tasks);
         adapter1 = new TaskBaseAdapter(getActivity(), (ArrayList<TaskView>) tasks/*dataSources*/);
        mListView1.setAdapter(adapter1);
      /*  mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), *//*dataSources*//*tasks.get(i).getName(), Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "edit");
                bundle.putInt("position", i);
                Intent in=new Intent(getActivity(),AddCourse.class);
                in.putExtra("xy", bundle);
                startActivity(in);
            }
        });*/
    }


   /*  private static List<TaskView> loadMovies() {


        tasks.add(new TaskView("Jurassic World - 111111111111"));
        tasks.add(new TaskView("Jurassic World - 22222222222"   ));
        tasks.add(new TaskView("Jurassic World - 3333333333 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 4444444 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 55555555555 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 66666666666 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 77777777 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 111111111111"));
        tasks.add(new TaskView("Jurassic World - 22222222222"));
        tasks.add(new TaskView("Jurassic World - 3333333333 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 4444444 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 55555555555 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 66666666666 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 77777777 Kingdom"));
        return tasks;
    }*/
}