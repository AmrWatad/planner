package com.example.studiplanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.studiplanner.R;
import com.example.studiplanner.results.ResultsBaseAdapter;
import com.example.studiplanner.task.AddTask;
import com.example.studiplanner.task.BottomSheetFragment;
import com.example.studiplanner.task.TaskBaseAdapter;
import com.example.studiplanner.task.TaskView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.studiplanner.MainActivity.courses;
import static com.example.studiplanner.MainActivity.tasks;
import static com.example.studiplanner.MainActivity.tasks_done;

public class Results extends Fragment {
     public    ListView mListView1;
    public    ResultsBaseAdapter adapter1;
    private TextView average;
    View v;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v =  inflater.inflate(R.layout.fragment_results, container, false);
        average=v.findViewById(R.id.average);
         initListView();
         calcAverage();

        return v;
    }

    private void calcAverage() {
        double points=0;
        int sumGrads=0;
        for (int i = 0; i < courses.size(); i++) {
            if(courses.get(i).getPoints()!=0){
                points+=courses.get(i).getPoints();
                sumGrads+=courses.get(i).getPoints()*courses.get(i).getGrade();
            }
        }
        String averag=df2.format(sumGrads/points);
        average.setText(averag );
    }

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
        mListView1 = v.findViewById(R.id.lv_results);
        adapter1 = new ResultsBaseAdapter(getActivity() );
        mListView1.setAdapter(adapter1);

    }


}