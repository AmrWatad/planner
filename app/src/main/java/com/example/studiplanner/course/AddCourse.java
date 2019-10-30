package com.example.studiplanner.course;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import com.example.studiplanner.DatePickerFragment;
import com.example.studiplanner.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.studiplanner.MainActivity.courses;
import static com.example.studiplanner.fragments.Courses.adapter;
import static com.example.studiplanner.fragments.Courses.mListView;

//import static com.example.studiplanner.fragments.Courses.courses;

public class AddCourse extends AppCompatActivity {
        EditText course,shortCode,techer,location,details,rating,grade,points;
                TextView dateClick,date;

    FrameLayout save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        course=findViewById(R.id.e_course_name);
        shortCode=findViewById(R.id.e_shortcode);
        techer=findViewById(R.id.e_teacher);
        location=findViewById(R.id.e_location);
        details=findViewById(R.id.e_details);
        save=findViewById(R.id.frame_save);
        rating=findViewById(R.id.e_rating);
        dateClick=findViewById(R.id.e_exam_date);
        date=findViewById(R.id.e_exam_date_result);
        grade=findViewById(R.id.ee_grade);
        points=findViewById(R.id.ee_points);
        dateClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dpf = new DatePickerFragment().newInstance();
                dpf.setCallBack(onDate);
                dpf.show(getSupportFragmentManager(),"");
            }
        });
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
            rating.setText(cr.getRating());
            date.setText(cr.getDate());
            grade.setText(cr.getGrade()+"");
            points.setText(cr.getPoints()+"");
           // Toast.makeText(getBaseContext(),"edit="+position,Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(/*(!grade.getText().toString().isEmpty()) && */points.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(),getResources().getString(R.string.write_point_of_course) ,Toast.LENGTH_LONG).show();
                    if (course.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.write_course_name), Toast.LENGTH_LONG).show();
                    else{

                         courses.get(position).setTitle(course.getText().toString());

                        courses.get(position).setGrade(Integer.parseInt(grade.getText().toString()));
                        courses.get(position).setPoints(Double.parseDouble(points.getText().toString()));

                        courses.get(position).setDetails(details.getText().toString());
                        courses.get(position).setShortTitle(shortCode.getText().toString());
                        courses.get(position).setTeacher(techer.getText().toString());
                        courses.get(position).setLocation(location.getText().toString());
                        courses.get(position).setRating(rating.getText().toString());
                        courses.get(position).setDate(date.getText().toString());
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
        else  {
            //Toast.makeText(getBaseContext(),"noot edit!!",Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(course.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(),getResources().getString(R.string.write_course_name),Toast.LENGTH_LONG).show();
                    else{
                        courses.add(new CourseView(course.getText().toString(),shortCode.getText().toString()
                                ,techer.getText().toString()
                                ,location.getText().toString(),details.getText().toString(),
                                rating.getText().toString(),date.getText().toString(),
                                dateformat,grade.getText().toString(),points.getText().toString()));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
    Date dateformat = null;

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String datet = year + "-" + (monthOfYear+1) + "-" +  dayOfMonth+ "";

            try {
                dateformat       = format.parse ( datet );

            } catch (ParseException e) {
                e.printStackTrace();
            }
            date.setText(datet);

        }
    };
    private Rect mRect = new Rect();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
/*************
 * this code is perfect for hide keybored
 *
 *
 *
 * ********/
        // mSpinnerAroundMe.setBackgroundColor(Color.parseColor("#00000000"));

        int[] location = new int[2];
        course.getLocationOnScreen(location);
        mRect.left = location[0];
        mRect.top = location[1];
        mRect.right = location[0] + date.getWidth();
        mRect.bottom = location[1] + date.getHeight();

        int x = (int) ev.getX();
        int y = (int) ev.getY();

        if (action == MotionEvent.ACTION_DOWN && !mRect.contains(x, y)) {
            InputMethodManager input = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            input.hideSoftInputFromWindow(date.getWindowToken(), 0);
        }
        date.setInputType(InputType.TYPE_NULL);

        return super.dispatchTouchEvent(ev);
    }
}
