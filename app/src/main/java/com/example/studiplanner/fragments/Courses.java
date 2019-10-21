package com.example.studiplanner.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studiplanner.course.AddCourse;
import com.example.studiplanner.R;
import com.example.studiplanner.course.BottomSheetFragment_Course;
import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.course.CoursesBaseAdapter;
import com.example.studiplanner.task.AddTask;
import com.example.studiplanner.task.BottomSheetFragment;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.studiplanner.MainActivity.courses;
import static com.example.studiplanner.MainActivity.tasks;
//import static com.example.studiplanner.task.BottomSheetFragment.tasks;

public class Courses extends Fragment  {
    FloatingActionButton plus;
    Context context;
    View v;
    private View popupInputDialogView = null;
    private   AlertDialog alertDialog;
    public static ListView mListView;
    public static CoursesBaseAdapter adapter;
    LabeledSwitch aSwitch;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          v =  inflater.inflate(R.layout.fragment_courses, container, false);
        initListView();
        Collections.sort(courses, new Courses.CustomComparatorDate());

        plus=v.findViewById(R.id.fab);
        aSwitch=v.findViewById(R.id.switchgit);
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aSwitch.isOn()){
                   // Toast.makeText(getContext(),"Rating",Toast.LENGTH_LONG).show();
                    if(tasks!=null)
                        Collections.sort(courses, new Courses.CustomComparatorRating());
                }
                else{
                   // Toast.makeText(getContext(),"Exams",Toast.LENGTH_LONG).show();

                    Collections.sort(courses, new Courses.CustomComparatorDate());

                }
                UpdateCoursesAdapter();
            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("msg","save");
                Intent intent=new Intent(getActivity(),AddCourse.class);
                intent.putExtra("xy", bundle);
                 getActivity().startActivity(intent);
            }
        });

        return v;
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
    private  int position=0;
    public void initListView() {
           mListView = v.findViewById(R.id.courses_lv);
           adapter = new CoursesBaseAdapter(getActivity(), (ArrayList<CourseView>) courses/*dataSources*/);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               /* Toast.makeText(view.getContext(), *//*dataSources*//*courses.get(i).getTitle(), Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "edit");
                bundle.putInt("position", i);
                Intent in=new Intent(getActivity(),AddCourse.class);
                in.putExtra("xy", bundle);
                startActivity(in);*/
                //todo: like tasks
                MenuItem menuItem = null;
                position=i;
                mOnNavigationItemSelectedListener.onNavigationItemSelected(menuItem);

            }
        });
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item ->
    {
        BottomSheetFragment_Course bf = new BottomSheetFragment_Course(position);
        bf.show(getActivity().getSupportFragmentManager(), bf.getTag());
        return true;
    };

    public class CustomComparatorRating implements Comparator<CourseView> {


        @Override
        public int compare(CourseView t1, CourseView t2) {
            return t1.getRating().compareTo(t2.getRating());

        }
    }



    public class CustomComparatorDate implements Comparator<CourseView> {


        @Override
        public int compare(CourseView t1, CourseView t2) {
           int i=-1;
            try {
               i= t1.getDateFormat().compareTo(t2.getDateFormat());
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return i;
        }
    }

    public  void UpdateCoursesAdapter(){
        CoursesBaseAdapter adapter = new CoursesBaseAdapter(getContext(), (ArrayList<CourseView>) courses);
        mListView.setAdapter(adapter);
    }
}