package com.example.studiplanner;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.studiplanner.course.CourseView;
import com.example.studiplanner.task.TaskView;
import com.google.android.gms.common.internal.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.studiplanner.MainActivity.courses;
//import static com.example.studiplanner.fragments.Courses.courses;

public class NotificationService extends Service {

    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 5;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);
        Gson gson = new Gson();
        String json = intent.getStringExtra("courses");
        Type listType = new TypeToken<ArrayList<CourseView>>(){}.getType();
          courses2=new Gson().fromJson(json, listType);
          startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        startTimer();
    }

    @Override
    public void onDestroy() {
       // Log.e(TAG, "onDestroy");
        //stoptimertask();
      //  super.onDestroy();
    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 50000, Your_X_SECS * 1000); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {
        Date date = null; // your date
// Choose time zone in which you want to interpret your Date
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/Israel"));
        Calendar cal2 = Calendar.getInstance(TimeZone.getTimeZone("Europe/Israel"));
        date=cal.getTime();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH)+1;
        int day = cal.get(Calendar.DAY_OF_MONTH)+1;
        System.out.println("qqqqqqqqqqqqqq  day="+day+", month="+month+", year="+year);
        final String[] title = {""};
        final String[] examdate = {""};
        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void run() {

                        //TODO CALL NOTIFICATION FUNC

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            final boolean[] flag = {false};

                            courses2.forEach(i->{
                                        cal2.setTime(i.getDateFormat());
                                        System.out.println("qqqqqqqqqqqqqq  day="+day+", month="+month+", year="+year);

                                        System.out.println("??????????="+(cal2.get(Calendar.DAY_OF_MONTH)+1)+", month=" +(cal2.get(Calendar.MONTH)+1));
                                if ((cal2.get(Calendar.DAY_OF_MONTH)+1 )==(day+3) &&((cal2.get(Calendar.MONTH)+1)==month)){
                                    flag[0] =true;
                                    title[0] =i.getTitle();
                                    examdate[0] =i.getDate();
                                    System.out.println("inside !!!!!!!!!!!!!");
                                }
                            }
                            );
                            if(flag[0]){
                                sendNotification(title[0],examdate[0]);
                                flag[0] =false;
                            }
                        }

                    }
                });
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(String course, String examDate) {
        Intent notificationIntent = new Intent(getApplicationContext(),
                MainActivity.class);


        notificationIntent.putExtra("clicked", "Notification Clicked");
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP); // To open only one activity


        // Invoking the default notification service

        NotificationManager mNotificationManager;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                getApplicationContext());
        Uri uri = RingtoneManager
                .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setContentTitle("Reminder");
        mBuilder.setContentText(/*"You have new Reminders."*/courses2.get(0).getDate());
        mBuilder.setTicker("New Exam Alert!");
        mBuilder.setSmallIcon(R.drawable.pen);
        mBuilder.setSound(uri);
        mBuilder.setAutoCancel(true);

        // Add Big View Specific Configuration
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[2];
        events[0] = new String("course: "+course);
        events[1] = new String("exam date: "+examDate );


        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("You have Exam:");
        //inboxStyle.createColoredBitmap(R.id.icon_frame,courses2.get(0).getColor());

        // Moves events into the big view
        for (int i = 0; i < events.length; i++) {
            inboxStyle.addLine(events[i]);
        }

        mBuilder.setStyle(inboxStyle);

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(getApplicationContext(),
                MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder
                .create(getApplicationContext());
        stackBuilder.addParentStack(MainActivity.class);


        // Adds the Intent that starts the Activity to the top of the stack


        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder
                .getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getBaseContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);


        // notificationID allows you to update the notification later  on.


        mNotificationManager.notify(999, mBuilder.build());
    }

    ArrayList<CourseView> courses2=new ArrayList<>();

}