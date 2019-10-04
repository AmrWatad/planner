package com.example.studiplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.fragments.Courses;
import com.example.studiplanner.fragments.Tasks;
import com.example.studiplanner.fragments.TimeTable;
import com.example.studiplanner.task.TaskView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private LinearLayout btnTasks, btnTime, btnCourses;
    private ImageView ibtnTasks, ibtnTime, ibtnCourses;
    private Fragment mContent;
    SharedPreferences mPrefs;
    public static List<CourseView> courses  = new ArrayList<>();
    public static List<TaskView> tasks  = new ArrayList<>();
    public static List<TaskView> tasks_done  = new ArrayList<>();
    public static HashMap<Integer, CourseView> textViews = new HashMap();

    @Override
    protected void onDestroy() {
        super.onDestroy();
 //set variables of 'myObject', etc.

        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("courses", arrayToString(courses));
        prefsEditor.putString("tasks", arrayToString(tasks));
        prefsEditor.putString("tasks_done", arrayToString(tasks_done));
        prefsEditor.putString("textViews", arrayToString(textViews));
        prefsEditor.commit();
        Toast.makeText(getApplicationContext(),courses.get(0).getTitle()+",Destroy",Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(),"START",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
          mPrefs = this.getPreferences(Context.MODE_PRIVATE);
           initCourses_SharedPrefrenc(mPrefs);
           initTasks_SharedPrefrenc(mPrefs);
           initTasks_done_SharedPrefrenc(mPrefs);
           initTextViews_SharedPrefrenc(mPrefs);


        btnTasks = findViewById(R.id.btnTasks);
        btnTime = findViewById(R.id.btnTime);
        btnCourses = findViewById(R.id.courses);
        ibtnTasks = findViewById(R.id.ibtnTasks);
        ibtnTime = findViewById(R.id.ibtnTime);
        ibtnCourses = findViewById(R.id.icourses);
        setSelected(1);

        Tasks homeFragment = new Tasks();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, homeFragment).
                commit();
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");

        }
        btnTasks.setOnClickListener(v -> {
            setSelected(1);
            FragmentManager fm = getSupportFragmentManager();
            Tasks movieFragment = new Tasks();
            fm.beginTransaction().
                    replace(R.id.container, movieFragment).
                    commit();
        });
        btnTime.setOnClickListener(v -> {
            setSelected(2);

            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new TimeTable()).
                    commit();
        });
        btnCourses.setOnClickListener(v -> {
            setSelected(3);
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new Courses()).
                    commit();
        });
    }

    private void initTextViews_SharedPrefrenc(SharedPreferences mPrefs) {
        String json = mPrefs.getString("textViews", "");
        Type listType = new TypeToken<HashMap<Integer, CourseView>>(){}.getType();
        HashMap<Integer, CourseView> hm=new Gson().fromJson(json, listType);
        if(hm!=null )
            textViews  = hm;
    }

    private void initTasks_done_SharedPrefrenc(SharedPreferences mPrefs) {
        String json = mPrefs.getString("tasks_done", "");
        Type listType = new TypeToken<ArrayList<TaskView>>(){}.getType();
        ArrayList<TaskView> arrayList=new Gson().fromJson(json, listType);
        if(arrayList!=null )
            tasks_done  = arrayList;
    }

    private void initTasks_SharedPrefrenc(SharedPreferences mPrefs) {
        String json = mPrefs.getString("tasks", "");
        Type listType = new TypeToken<ArrayList<TaskView>>(){}.getType();
        ArrayList<TaskView> arrayList=new Gson().fromJson(json, listType);
        if(arrayList!=null )
            tasks  = arrayList;
    }

    private void initCourses_SharedPrefrenc(SharedPreferences mPrefs) {
        String json = mPrefs.getString("courses", "");
        Type listType = new TypeToken<ArrayList<CourseView>>(){}.getType();
        ArrayList<CourseView> arrayList=new Gson().fromJson(json, listType);
        if(arrayList!=null )
        courses  = arrayList;
    }

    private void setSelected(int i) {
        ibtnTasks.setSelected(false);
        ibtnTime.setSelected(false);
        ibtnCourses.setSelected(false);

        switch (i) {
            case 1:
                ibtnTasks.setSelected(true);
                break;
            case 2:
                ibtnTime.setSelected(true);
                break;
            case 3:
                ibtnCourses.setSelected(true);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(this, NotificationService.class));
    }
    public static <T> String arrayToString(List<T> list) {
        Gson g = new Gson();
        return g.toJson(list);
    }

    public static <T> String arrayToString(HashMap<Integer, CourseView> list) {
        Gson g = new Gson();
        return g.toJson(list);
    }
    public static <T> ArrayList<T> stringToArray(String s) {
        Gson g = new Gson();
        Type listType = new TypeToken<ArrayList<T>>(){}.getType();
        ArrayList<T> list = g.fromJson(s, listType);
        return list;
    }
}
