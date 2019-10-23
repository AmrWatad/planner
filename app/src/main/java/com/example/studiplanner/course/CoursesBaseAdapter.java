package com.example.studiplanner.course;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.studiplanner.R;

import java.util.ArrayList;

import static com.example.studiplanner.fragments.Courses.mListView;

public class CoursesBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<CourseView> mDataSource;
    Context context;

    private View popupInputDialogView = null;
    private AlertDialog alertDialog;

    public CoursesBaseAdapter(Context context, ArrayList<CourseView> items) {
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

    public CourseView getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_course, parent, false);
            holder = new CourseViewHolder();
            holder.image = convertView.findViewById(R.id.item_course_image);
            holder.title = convertView.findViewById(R.id.item_course_title);
            holder.remove = convertView.findViewById(R.id.remove_item);
            holder.rating = convertView.findViewById(R.id.t_rating);
            holder.date = convertView.findViewById(R.id.t_date);
            convertView.setTag(holder);
        } else {
            holder = (CourseViewHolder) convertView.getTag();

        }
        CourseView course = getItem(position);
        holder.title.setText(course.getTitle());
        holder.rating.setText(course.getRating()+"/10");
        holder.date.setText(course.getDate());
        TextDrawable drawable = TextDrawable.builder().beginConfig()
                .fontSize(23).endConfig()
                .buildRound(course.getTitle(), course.getColor());

        holder.image.setBackground(drawable);
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creatPopUp(position);
            }
        });
        return convertView;

    }

    public static class CourseViewHolder {
        public ImageView remove, image;
        public TextView title,date,rating;
    }

    private void creatPopUp(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setCancelable(true);
        initPopupViewControls(position);
        alertDialogBuilder.setView(popupInputDialogView);
        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void initPopupViewControls(int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        popupInputDialogView = layoutInflater.inflate(R.layout.popup_remove_course, null);
        TextView delete = popupInputDialogView.findViewById(R.id.textdelete);
        TextView text_edit = popupInputDialogView.findViewById(R.id.text_edit);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataSource.remove(position);
                CoursesBaseAdapter adapter = new CoursesBaseAdapter(context, (ArrayList<CourseView>) mDataSource);
               if(adapter!=null && mListView!=null)//todo: init of them,from this code place
                mListView.setAdapter(adapter);
                alertDialog.dismiss();
            }
        });
        text_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("msg", "edit");
                bundle.putInt("position", position);
                Intent in=new Intent(context,AddCourse.class);
                in.putExtra("xy", bundle);
                context.startActivity(in);
                alertDialog.dismiss();
            }
        });
    }


}
