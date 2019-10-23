package com.example.studiplanner.task;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.studiplanner.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import static com.example.studiplanner.MainActivity.tasks_done;
import static com.example.studiplanner.fragments.Tasks.taskName;

public class BottomSheetFragment extends BottomSheetDialogFragment {
  Context context;
    public static ListView mListViewTask;

    public static DoneTaskBaseAdapter  adapterTask;
    public BottomSheetFragment() {

    }

    //create custom theme for your bottom sheet modal
    @Override
    public int getTheme() {
        return R.style.AppBottomSheetDialogTheme;
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

        View contentView = View.inflate(getContext(), R.layout.fragment_tasks_done, null);
        context = contentView.getContext();
        initListView(contentView);
         dialog.setContentView(contentView);

        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
      /*  int maxHeight = (int) (500); //custom height of bottom sheet

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) contentView.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();
        ((BottomSheetBehavior) behavior).setPeekHeight(maxHeight);  //changed default peek height of bottom sheet
      */  mListViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 dialog.dismiss();
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    public void initListView(View contentView) {
        mListViewTask =  contentView.findViewById(R.id.tasks_done);
       // loadMovies();
        adapterTask = new DoneTaskBaseAdapter(getActivity(), (ArrayList<TaskView>) tasks_done/*dataSources*/);
        mListViewTask.setAdapter(adapterTask);
       /* mListViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), *//*dataSources*//*courses.get(i).getTitle(), Toast.LENGTH_LONG).show();
                Bundle bundle = new Bundle();
                bundle.putString("msg", "edit");
                bundle.putInt("position", i);
                Intent in=new Intent(getActivity(), AddCourse.class);
                in.putExtra("xy", bundle);
                startActivity(in);
            }
        });*/
    }

    /*private static List<TaskView> loadMovies() {


        tasks.add(new TaskView("Jurassic World - 111111111111"));
        tasks.add(new TaskView("Jurassic World - 22222222222"   ));
        tasks.add(new TaskView("Jurassic World - 3333333333 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 4444444 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 55555555555 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 66666666666 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 77777777 Kingdom" ));
        tasks.add(new TaskView("Jurassic World - 111111111111"));
        tasks.add(new TaskView("Jurassic World - 22222222222"));
        tasks.add(new TaskView("Jurassic World - 3333333333 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 4444444 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 55555555555 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 66666666666 Kingdom"));
        tasks.add(new TaskView("Jurassic World - 77777777 Kingdom"));
        return tasks;
    }*/
}