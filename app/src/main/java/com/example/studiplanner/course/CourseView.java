package com.example.studiplanner.course;

import android.graphics.Color;

import java.util.Date;
import java.util.Random;

public class CourseView {
    private String title;
    private String shortTitle;
    private String teacher;
    private String location;
    private String details;
    private double points;
    private int grade;



    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Date getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(Date dateFormat) {
        this.dateFormat = dateFormat;
    }

    Date dateFormat;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String date;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    private String rating;

    public int getColor() {
        return color;
    }

    private int color;

    public CourseView(String title) {
        this.title = title;
    }

    public CourseView() {
    }

    public CourseView(String title, String shortTitle, String teacher, String location, String details,
                      String rating, String date, Date dateFormat ,String grade, String points) {
        this.title = title;
        if (title.length() <= 5)
            this.shortTitle = title;
        else
            this.shortTitle = title.substring(0, 4);

        this.teacher = teacher;
        this.location = location;
        this.details = details;
        setColor();
        this.rating = rating;
        this.date = date;
        this.dateFormat = dateFormat;
        this.grade=Integer.parseInt(grade);
        this.points=Double.parseDouble(points);
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
