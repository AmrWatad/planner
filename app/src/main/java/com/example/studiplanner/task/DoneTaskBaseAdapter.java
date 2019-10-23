package com.example.studiplanner.task;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.studiplanner.R;

import java.util.ArrayList;

import static com.example.studiplanner.MainActivity.tasks;
import static com.example.studiplanner.fragments.Tasks.adapter1;
import static com.example.studiplanner.fragments.Tasks.mListView1;
import static com.example.studiplanner.task.BottomSheetFragment.mListViewTask;
//import static com.example.studiplanner.task.BottomSheetFragment.tasks;


public class DoneTaskBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<TaskView> mDataSource;
    Context context;

    private View popupInputDialogView = null;
    private AlertDialog alertDialog;

    public DoneTaskBaseAdapter(Context context, ArrayList<TaskView> items) {
        mDataSource = items;
        this.context = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override

    public TaskView getItem(int position) {
        return mDataSource.get(position);
    }

    @Override

    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CourseViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_task_done, parent, false);
            holder = new CourseViewHolder();
            holder.title = convertView.findViewById(R.id.item_task_title);
            holder.remove = convertView.findViewById(R.id.remove_item);
            holder.unDone = convertView.findViewById(R.id.imagedone);
            holder.checkBox = convertView.findViewById(R.id.checkBox2);
            holder.unDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    creatPopUp(position, "passunDone", ((CheckBox) view));
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (CourseViewHolder) convertView.getTag();
        }
        TaskView course = getItem(position);
        holder.title.setText(course.getName());
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatPopUp(position, "remove", null);
            }
        });
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatPopUp(position, "passunDone", ((CheckBox) view));
            }
        });

        return convertView;

    }

    public static class CourseViewHolder {
        public ImageView remove, unDone;
        public TextView title;
        public CheckBox checkBox;
    }

    private void creatPopUp(int position, String type, CheckBox checkBox) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        boolean flag = false;
        if (type.equals("remove"))
            initPopupViewControls(position);
        else {
            initPopupViewControls_unDone(position, checkBox);
            flag = true;
        }
        alertDialogBuilder.setView(popupInputDialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        if (flag)
            alertDialog.setOnDismissListener(dialog -> {
                checkBox.setChecked(true);
                /* code goes here */
            });
    }

    private void initPopupViewControls_unDone(int position, CheckBox checkBox) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_return_task, null);
        TextView returnTask = popupInputDialogView.findViewById(R.id.textdelete);
        TextView name = popupInputDialogView.findViewById(R.id.text_edit);
        returnTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.add(mDataSource.get(position));
                mDataSource.remove(position);
                DoneTaskBaseAdapter adapter = new DoneTaskBaseAdapter(context, (ArrayList<TaskView>) mDataSource);
                mListViewTask.setAdapter(adapter);
                mListView1.setAdapter(adapter1);
                adapter1.notifyDataSetChanged();
                alertDialog.dismiss();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(true);
                alertDialog.dismiss();
            }
        });
    }

    private void initPopupViewControls(int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_remove_course, null);
        TextView delete = popupInputDialogView.findViewById(R.id.textdelete);
        TextView name = popupInputDialogView.findViewById(R.id.text_edit);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.remove(position);
                DoneTaskBaseAdapter adapter = new DoneTaskBaseAdapter(context, (ArrayList<TaskView>) mDataSource);
                mListViewTask.setAdapter(adapter);
                alertDialog.dismiss();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
