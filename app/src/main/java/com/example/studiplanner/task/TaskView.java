package com.example.studiplanner.task;

public class TaskView {
    String name;
    String details;

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

    public TaskView() {
    }

    public TaskView(String name) {
        this.name = name;
    }
}
