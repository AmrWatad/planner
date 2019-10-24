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
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.studiplanner.R;

import java.util.ArrayList;

import static com.example.studiplanner.MainActivity.tasks;
import static com.example.studiplanner.MainActivity.tasks_done;
import static com.example.studiplanner.fragments.Tasks.mListView1;
//import static com.example.studiplanner.task.BottomSheetFragment.tasks_done;


public class TaskBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<TaskView> mDataSource;
    Context context;
    CourseViewHolder holder;
    private View popupInputDialogView = null;
    private AlertDialog alertDialog;

    public TaskBaseAdapter(Context context, ArrayList<TaskView> items) {
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
         if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_task, parent, false);
            holder = new CourseViewHolder();
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     creatPopUpTask(position);

                }
            });
            holder.title = convertView.findViewById(R.id.item_task_title);
            holder.isDone = convertView.findViewById(R.id.remove_done_item);
            holder.delete = convertView.findViewById(R.id.checkBox);
            holder.task_course = convertView.findViewById(R.id.task_course);
            holder.date = convertView.findViewById(R.id.task_date);
            convertView.setTag(holder);
        } else {
            holder = (CourseViewHolder) convertView.getTag();
        }
        TaskView course = getItem(position);
        holder.title.setText(course.getName());
        holder.task_course.setText(course.getCourse());
        holder.date.setText(course.getDate());
        holder.isDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatPopUp(position, ((CheckBox) view));
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatPopUp(position, ((CheckBox) view));
            }
        });
        return convertView;

    }

    public static class CourseViewHolder {
        public ImageView isDone;
        public TextView title, task_course, date;
        public CheckBox delete;
    }

    private void creatPopUp(int position, CheckBox checkBox) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
             initPopupViewControls(position, checkBox);
          alertDialogBuilder.setView(popupInputDialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.setOnDismissListener(dialog -> {
            checkBox.setChecked(false);
            /* code goes here */
        });
    }

    private void creatPopUpTask(int position ) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);

            initPopupViewControls_view(position);
        alertDialogBuilder.setView(popupInputDialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    private void initPopupViewControls_view(int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popupInputDialogView = layoutInflater.inflate(R.layout.item_task_view, null);
        TextView task_name = popupInputDialogView.findViewById(R.id.text_title);
        TextView task_details = popupInputDialogView.findViewById(R.id.textView_details);
        TextView task_couerse = popupInputDialogView.findViewById(R.id.text_course);
        TextView task_date = popupInputDialogView.findViewById(R.id.text_exam);
        task_name.setText(tasks.get(position).getName());
        task_details.setText(tasks.get(position).getDetails());
        task_couerse.setText(tasks.get(position).getCourse());
        task_date.setText(tasks.get(position).getDate());

    }

    private void initPopupViewControls(int position, CheckBox checkBox) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_finished_task, null);
        TextView delete = popupInputDialogView.findViewById(R.id.textdelete);
        TextView name = popupInputDialogView.findViewById(R.id.text_edit);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks_done.add(mDataSource.get(position));
                mDataSource.remove(position);
                TaskBaseAdapter adapter = new TaskBaseAdapter(context, (ArrayList<TaskView>) mDataSource);

                mListView1.setAdapter(adapter);

                alertDialog.dismiss();
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(false);
                alertDialog.dismiss();
            }
        });


    }
}
