package com.example.studiplanner.fragments;

import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.studiplanner.R;
import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.course.CoursesBaseAdapter;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.example.studiplanner.MainActivity.courses;
import static com.example.studiplanner.MainActivity.table_numbers;
import static com.example.studiplanner.MainActivity.textViews;
//import static com.example.studiplanner.fragments.Courses.courses;

public class TimeTable extends Fragment {
    TableLayout tableLayout;
    LabeledSwitch switchgit;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.table , container, false);
        tableLayout = v.findViewById(R.id.table);
          switchgit=v.findViewById(R.id.switchgit);
        textViews.entrySet().stream().forEach(i -> {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRect(i.getValue().getShortTitle(), i.getValue().getColor());
            TextView textView = v.findViewById(i.getKey());
            if (drawable!=null && textView!=null)
            textView.setBackground(drawable);
        });
        onTable();
        return v;
    }
   private List<String> times;
    private void onTable() {
        if(table_numbers) {
            switchgit.setOn(true);
            setTableOrder();
        }

        switchgit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 times= Arrays.asList("8:00-\n10:00","10:00-\n12:00","12:00-\n14:00","14:00-\n16:00",
                        "16:00-\n18:00","18:00-\n20:00","20:00-\n22:00");//,"10:00-12:00","10:00-12:00","10:00-12:00");
                times.get(1);
                if(switchgit.isOn()){
                    table_numbers=false;

                }
                else{
                    table_numbers=true;

                }
                setTableOrder();

            }
        });
        for (int i = 2; i < tableLayout.getChildCount(); i++) {
            View child = tableLayout.getChildAt(i);

            if (child instanceof TableRow) {
                TableRow row = (TableRow) child;
                for (int x = 1; x < row.getChildCount(); x++) {
                    View view = row.getChildAt(x);
                    if (view instanceof TextView) {
                        TextView viewText = (TextView) view;
                        viewText.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                // viewText.setText("hloo");
                                creatPopUp(view);
                            }
                        });
                    }
                }
            }
        }
    }

    private void setTableOrder( ) {
        if(table_numbers) {
            for (int i = 1; i < 8; i++) {
                ((TextView) ((TableRow) tableLayout.getChildAt(i + 1)).getChildAt(0)).setText(i + ".");

            }
        }
        else{
            for (int i = 1; i < 8; i++) {
                ((TextView) ((TableRow) tableLayout.getChildAt(i + 1)).getChildAt(0)).setText(times.get(i-1));

            }
        }
    }

    private View popupInputDialogView = null;
    private AlertDialog alertDialog;

    private void creatPopUp(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setCancelable(true);
        initPopUp(view);

        alertDialogBuilder.setView(popupInputDialogView);

        alertDialog = alertDialogBuilder.create();
        //alertDialog.getWindow().setLayout(600, 800);
        alertDialog.show();
    }

    private void initPopUp(View textView) {
        ListView mListView;
        CoursesBaseAdapter adapter;
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        popupInputDialogView = layoutInflater.inflate(R.layout.list_courses, null);

        mListView = popupInputDialogView.findViewById(R.id.courses_lv);
        FloatingActionButton remove=popupInputDialogView.findViewById(R.id.t_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textView instanceof TextView) {
                    TextView viewText = (TextView) textView;
                    viewText.setText("");
                    viewText.setBackgroundColor(getResources().getColor(R.color.white));
                    textViews.remove(viewText.getId());
                    alertDialog.dismiss();
                }
            }
        });

                // mListView.
        mListView.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 600));

        adapter = new CoursesBaseAdapter(getActivity(), (ArrayList<CourseView>) courses/*dataSources*/);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextDrawable drawable = TextDrawable.builder()
                        .buildRect(courses.get(i).getShortTitle(), courses.get(i).getColor());
                if (textView instanceof TextView) {
                    TextView viewText = (TextView) textView;
                    viewText.setBackground(drawable);
                    textViews.put(viewText.getId(), courses.get(i));
                    alertDialog.dismiss();
                }

            }
        });

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            String editTextValue = savedInstanceState.getString("EditText");
            Log.i(TAG, "Found edit text value: " + editTextValue);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


}