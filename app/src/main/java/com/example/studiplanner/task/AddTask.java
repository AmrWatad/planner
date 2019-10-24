package com.example.studiplanner.task;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.MotionEventCompat;

import com.example.studiplanner.DatePickerFragment;
import com.example.studiplanner.R;
import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.course.CoursesBaseAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import static com.example.studiplanner.MainActivity.courses;
//import static com.example.studiplanner.fragments.Courses.courses;
import static com.example.studiplanner.MainActivity.tasks;
import static com.example.studiplanner.fragments.Tasks.adapter1;
import static com.example.studiplanner.fragments.Tasks.mListView1;
//import static com.example.studiplanner.task.BottomSheetFragment.tasks;

public class AddTask extends AppCompatActivity {
        EditText task,details;
        TextView course,date, course_task_View,date_pecker;
    FrameLayout save;
    CourseView addingCours;
    private View popupInputDialogView = null;
    private AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(tasks!=null)
            Collections.sort(tasks, new CustomComparator());
        setContentView(R.layout.activity_add_task);
        task=findViewById(R.id.e_task_name);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        course=findViewById(R.id.t_course);
        course_task_View =findViewById(R.id.t_course_view);
        date=findViewById(R.id.add_date);
        date_pecker=findViewById(R.id.date);
          details=findViewById(R.id.e_details);
        save=findViewById(R.id.frame_save);
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatPopUp();
            }
        });
        date.setOnClickListener(new View.OnClickListener() {
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
            TaskView taskView=tasks.get(position);
            task.setText(taskView.getName());
             details.setText(taskView.getDetails());
           // Toast.makeText(getBaseContext(),"edit="+position,Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(task.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.write_course_name),Toast.LENGTH_LONG).show();
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
           // Toast.makeText(getBaseContext(),"noot edit!!",Toast.LENGTH_LONG).show();
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(task.getText().toString().isEmpty())
                        Toast.makeText(getBaseContext(),getResources().getString(R.string.write_task_name),Toast.LENGTH_LONG).show();
                    else{
                        tasks.add(new TaskView(task.getText().toString() ,details.getText().toString(),date_pecker.getText().toString(),dateformat,course_task_View.getText().toString()));
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
    private void creatPopUp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setCancelable(true);
        initPopUp( );

        alertDialogBuilder.setView(popupInputDialogView);

        alertDialog = alertDialogBuilder.create();
        //alertDialog.getWindow().setLayout(600, 800);
        alertDialog.show();
    }
    private void initPopUp( ) {
        ListView mListView;
        CoursesBaseAdapter adapter;
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        popupInputDialogView = layoutInflater.inflate(R.layout.list_courses, null);

        mListView = popupInputDialogView.findViewById(R.id.courses_lv);
        FloatingActionButton remove=popupInputDialogView.findViewById(R.id.t_remove);
        remove.setVisibility(View.INVISIBLE);

        // mListView.
        mListView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 600));

        adapter = new CoursesBaseAdapter(this, (ArrayList<CourseView>) courses/*dataSources*/);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Drawable unwrappedDrawable = AppCompatResources.getDrawable(getApplicationContext(), R.drawable.rectangle_background);
                Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
                DrawableCompat.setTint(wrappedDrawable,courses.get(i).getColor());
                course_task_View.setBackground(unwrappedDrawable);
                course_task_View.setText(courses.get(i).getTitle());

                alertDialog.dismiss();
            }
        });

    }
    Date dateformat = null;
    private Rect mRect = new Rect();

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String date = year + "-" + monthOfYear + "-" +  dayOfMonth+ "";

            try {
                  dateformat       = format.parse ( date );

            } catch (ParseException e) {
                e.printStackTrace();
            }

            date_pecker.setText(date);

         }
    };
    public class CustomComparator implements Comparator<TaskView> {


        @Override
        public int compare(TaskView t1, TaskView t2) {
            if(t1.getDateFormat()!=null&&t2.getDateFormat()!=null)
            return t1.getDateFormat().compareTo(t2.getDateFormat());
            return 1;
        }
    }
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
