package com.example.studiplanner.course;

import android.graphics.Color;

import java.util.Random;

public class CourseView {
    private String title;
    private String shortTitle;
    private String teacher;
    private String location;
    private String details;

    public int getColor() {
        return color;
    }

    private int color;

    public CourseView(String title) {
        this.title = title;
    }

    public CourseView() {
    }

    public CourseView(String title, String shortTitle, String teacher, String location, String details) {
        this.title = title;
        if (title.length() <= 5)
            this.shortTitle = title;
        else
            this.shortTitle = title.substring(0, 4);

        this.teacher = teacher;
        this.location = location;
        this.details = details;
        setColor();

    }

    private void setColor() {
        Random r = new Random();
        int red = r.nextInt(255 - 0 + 1) + 0;
        int green = r.nextInt(255 - 0 + 1) + 0;
        int blue = r.nextInt(255 - 0 + 1) + 0;
        color = Color.rgb(red, green, blue);
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getLocation() {
        return location;
    }

    public String getDetails() {
        return details;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }
}
