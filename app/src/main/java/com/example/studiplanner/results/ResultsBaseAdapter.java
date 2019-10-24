package com.example.studiplanner.results;

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
import com.example.studiplanner.course.AddCourse;
import com.example.studiplanner.course.CourseView;

import java.util.ArrayList;

import static com.example.studiplanner.MainActivity.courses;
import static com.example.studiplanner.fragments.Courses.mListView;

public class ResultsBaseAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
     Context context;


    public ResultsBaseAdapter(Context context ) {
         this.context = context;
        mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override

    public CourseView getItem(int position) {
        return courses.get(position);
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
            convertView = mInflater.inflate(R.layout.item_exam_result, parent, false);
            holder = new CourseViewHolder();
             holder.title = convertView.findViewById(R.id.text_title);
             holder.exam_result = convertView.findViewById(R.id.text_grade);
             holder.points = convertView.findViewById(R.id.text_points);
             convertView.setTag(holder);
        } else {
            holder = (CourseViewHolder) convertView.getTag();

        }
        CourseView course = getItem(position);
        holder.title.setText(course.getTitle());
        holder.exam_result.setText(course.getGrade()+"");
        holder.points.setText(course.getPoints()+"");


        return convertView;

    }

    public static class CourseViewHolder {
         public TextView title,exam_result,points;
    }



}
