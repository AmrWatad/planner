package com.example.studiplanner.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.studiplanner.course.AddCourse;
import com.example.studiplanner.R;
import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.course.CoursesBaseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Courses extends Fragment  {
    FloatingActionButton plus;
    Context context;
    View v;
    private View popupInputDialogView = null;
    private   AlertDialog alertDialog;
    public static ListView mListView;
    public static   List<CourseView> courses  = new ArrayList<>();
    public static CoursesBaseAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
          v =  inflater.inflate(R.layout.fragment_courses, container, false);
        initListView();
        plus=v.findViewById(R.id.fab);
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

    public void initListView() {
           mListView = v.findViewById(R.id.courses_lv);
           adapter = new CoursesBaseAdapter(getActivity(), (ArrayList<CourseView>) courses/*dataSources*/);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), /*dataSources*/courses.get(i).getTitle(), Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "edit");
                bundle.putInt("position", i);
                Intent in=new Intent(getActivity(),AddCourse.class);
                in.putExtra("xy", bundle);
                startActivity(in);
            }
        });
    }

  /*  private static List<CourseView> loadMovies() {


        courses.add(new CourseView("Jurassic World - 111111111111"));
        courses.add(new CourseView("Jurassic World - 22222222222"   ));
        courses.add(new CourseView("Jurassic World - 3333333333 Kingdom" ));
         courses.add(new CourseView("Jurassic World - 4444444 Kingdom" ));
        courses.add(new CourseView("Jurassic World - 55555555555 Kingdom" ));
         courses.add(new CourseView("Jurassic World - 66666666666 Kingdom" ));
        courses.add(new CourseView("Jurassic World - 77777777 Kingdom" ));
        courses.add(new CourseView("Jurassic World - 111111111111"));
        courses.add(new CourseView("Jurassic World - 22222222222"));
        courses.add(new CourseView("Jurassic World - 3333333333 Kingdom"));
        courses.add(new CourseView("Jurassic World - 4444444 Kingdom"));
        courses.add(new CourseView("Jurassic World - 55555555555 Kingdom"));
        courses.add(new CourseView("Jurassic World - 66666666666 Kingdom"));
        courses.add(new CourseView("Jurassic World - 77777777 Kingdom"));
         return courses;
    }*/


}