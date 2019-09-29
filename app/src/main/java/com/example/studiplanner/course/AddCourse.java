package com.example.studiplanner.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.studiplanner.R;

import static com.example.studiplanner.fragments.Courses.adapter;
import static com.example.studiplanner.fragments.Courses.courses;
import static com.example.studiplanner.fragments.Courses.mListView;

public class AddCourse extends AppCompatActivity {
        EditText course,shortCode,techer,location,details;
    FrameLayout save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        course=findViewById(R.id.e_course_name);
        shortCode=findViewById(R.id.e_shortcode);
        techer=findViewById(R.id.e_teacher);
        location=findViewById(R.id.e_location);
        details=findViewById(R.id.e_details);
        save=findViewById(R.id.frame_save);
        Intent in=getIntent();
        Bundle bundle = getIntent().getBundleExtra("xy");   //<< get Bundle from Intent
        String value = bundle.getString("msg");
        if(value.equals("edit")){
            int position = bundle.getInt("position");
            CourseView cr=courses.get(position);
            course.setText(cr.getTitle());
            shortCode.setText(cr.getShortTitle());
            techer.setText(cr.getTeacher());
            location.setText(cr.getLocation());
            details.setText(cr.getDetails());
            Toast.makeText(getBaseContext(),"edit="+position,Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(course.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(),"write course name!",Toast.LENGTH_LONG).show();
                    else{
                        courses.get(position).setTitle(course.getText().toString());
                        courses.get(position).setDetails(details.getText().toString());
                        courses.get(position).setShortTitle(shortCode.getText().toString());
                        courses.get(position).setTeacher(techer.getText().toString());
                        courses.get(position).setLocation(location.getText().toString());
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        if ( getFragmentManager().getBackStackEntryCount() > 0)
                        {
                            getFragmentManager().popBackStack();
                            return;
                        }
                        AddCourse.super.onBackPressed();
                    }
                }
            });
        }
        else {
            Toast.makeText(getBaseContext(),"noot edit!!",Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(course.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(),"write course name!",Toast.LENGTH_LONG).show();
                    else{
                        courses.add(new CourseView(course.getText().toString(),shortCode.getText().toString()
                                ,techer.getText().toString(),location.getText().toString(),details.getText().toString()));
                        mListView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        if ( getFragmentManager().getBackStackEntryCount() > 0)
                        {
                            getFragmentManager().popBackStack();
                            return;
                        }
                        AddCourse.super.onBackPressed();
                    }
                }
            });
        }


    }
}
