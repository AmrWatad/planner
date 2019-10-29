package com.example.studiplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.fragments.Courses;
import com.example.studiplanner.fragments.Results;
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
    private LinearLayout btnTasks, btnTime, btnCourses,btnResults;
    private ImageView ibtnTasks, ibtnTime, ibtnCourses,iresults;
    private Fragment mContent;
    SharedPreferences mPrefs;
    public static List<CourseView> courses  = new ArrayList<>();
    public static List<TaskView> tasks  = new ArrayList<>();
    public static List<TaskView> tasks_done  = new ArrayList<>();
    public static HashMap<Integer, CourseView> textViews = new HashMap();
    public static  boolean table_numbers=true;
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
        //todo: notification's
        /*Intent intent = new Intent(this, NotificationService.class);
        intent.putExtra("courses",arrayToString(courses));
        startService(intent);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        iresults = findViewById(R.id.iresults);
        btnResults = findViewById(R.id.results);
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
        btnResults.setOnClickListener(v -> {
            setSelected(4);
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new Results()).
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
        iresults.setSelected(false);

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
            case 4:
                iresults.setSelected(true);
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        prefsEditor.putString("courses", arrayToString(courses));
        prefsEditor.putString("tasks", arrayToString(tasks));
        prefsEditor.putString("tasks_done", arrayToString(tasks_done));
        prefsEditor.putString("textViews", arrayToString(textViews));
        prefsEditor.commit();
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