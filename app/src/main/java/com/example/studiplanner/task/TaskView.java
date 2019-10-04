package com.example.studiplanner.task;

import java.util.Date;

public class TaskView {
    String name;
    String details;
    String course;
    String date;
    Date dateFormat;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(Date dateFormat) {
        this.dateFormat = dateFormat;
    }

    public TaskView(String name, String details, String date, Date dateformat, String course) {
        this.name=name;
        this.details=details;
        this.date=date;
        this.course=course;
        this.dateFormat=dateformat;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }



    public TaskView(String name, String details) {
        this.name=name;
        this.details=details;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




}
