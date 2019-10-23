package com.example.studiplanner.course;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.studiplanner.R;
import com.example.studiplanner.task.DoneTaskBaseAdapter;
import com.example.studiplanner.task.TaskView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

import static com.example.studiplanner.MainActivity.courses;
import static com.example.studiplanner.MainActivity.tasks_done;
import static com.example.studiplanner.fragments.Tasks.taskName;

public class BottomSheetFragment_Course extends BottomSheetDialogFragment {
    Context context;
    private int position;

    public static DoneTaskBaseAdapter adapterTask;

    public BottomSheetFragment_Course(int position) {
        this.position = position;
    }

    //create custom theme for your bottom sheet modal
    @Override
    public int getTheme() {
        return  0;//R.style.AppBottomSheetDialogTheme;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(requireContext(), getTheme());  //set your created theme here

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setupDialog(@NonNull Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View contentView = View.inflate(getContext(), R.layout.item_course_view, null);
        context = contentView.getContext();
        dialog.setContentView(contentView);
        TextView text_title = contentView.findViewById(R.id.text_title);
        TextView teacher = contentView.findViewById(R.id.text_teacher);
        TextView location = contentView.findViewById(R.id.text_location);
        TextView details = contentView.findViewById(R.id.textView_details);
        TextView grade = contentView.findViewById(R.id.text_grade);
        TextView points = contentView.findViewById(R.id.text_points);
        TextView  exam = contentView.findViewById(R.id.text_exam);
        text_title.setText(courses.get(position).getTitle());
        teacher.setText(courses.get(position).getTeacher());
        location.setText(courses.get(position).getLocation());
        details.setText(courses.get(position).getDetails());
        grade.setText(courses.get(position).getGrade()+"");
        points.setText(courses.get(position).getPoints() +"");
        exam.setText(courses.get(position).getDate() +"");
     //   DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
    //    int width = displayMetrics.widthPixels;
     //   int height = displayMetrics.heightPixels;
      /*  int maxHeight = (int) (500); //custom height of bottom sheet

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((BottomSheetBehavior) behavior).setPeekHeight(maxHeight);  //changed default peek height of bottom sheet
*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}