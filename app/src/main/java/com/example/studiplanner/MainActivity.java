package com.example.studiplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Button;

import com.example.studiplanner.fragments.Courses;
import com.example.studiplanner.fragments.Tasks;
import com.example.studiplanner.fragments.TimeTable;

public class MainActivity extends AppCompatActivity {
    private Button btnTasks, btnTime, btnCourses;
    private Fragment mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnTasks = findViewById(R.id.btnTasks);
        btnTime = findViewById(R.id.btnTime);
        btnCourses = findViewById(R.id.courses);

        Tasks homeFragment = new Tasks();
        getSupportFragmentManager().beginTransaction().
                replace(R.id.container, homeFragment).
                commit();
        if (savedInstanceState != null) {
            //Restore the fragment's instance
            mContent = getSupportFragmentManager().getFragment(savedInstanceState, "myFragmentName");

        }
        btnTasks.setOnClickListener(v -> {
            FragmentManager fm = getSupportFragmentManager();
            Tasks movieFragment = new Tasks();
            fm.beginTransaction().
                    replace(R.id.container, movieFragment).
                    commit();
        });
        btnTime.setOnClickListener(v -> {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new TimeTable()).
                    commit();
        });
        btnCourses.setOnClickListener(v -> {
            getSupportFragmentManager().
                    beginTransaction().
                    replace(R.id.container, new Courses()).
                    commit();
        });
    }
}
