package com.example.studiplanner;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrenceCourse {
    private static SharedPrefrenceCourse instance;

    public   SharedPreferences sh ;//= getSharedPreferences("key", Context.MODE_PRIVATE);
    public   SharedPreferences.Editor ed ;//= sh.edit();

    private SharedPrefrenceCourse() {}

    public synchronized static SharedPrefrenceCourse getInstance() {
        if (instance == null) {
            instance = new SharedPrefrenceCourse();
        }
        return instance;
    }


}
