package com.example.studiplanner;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        stoptimertask();
        super.onDestroy();


    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
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

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    public void run() {

                        //TODO CALL NOTIFICATION FUNC
                        //   YOURNOTIFICATIONFUNCTION();
                        //  setDataForNotificationWithActionButton();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            courses.forEach(i->{
                               Calendar calendar= Calendar.getInstance();
                               int dayOfWeek = calendar.get(Calendar.MINUTE);
                                if(i.getDateFormat().getDay()==4)
                                    sendNotification();
                            });
                        }
                     /*   Calendar calendar = Calendar.getInstance();
                        int dayOfWeek = calendar.get(Calendar.MINUTE);
                        if (dayOfWeek == 22) {
                            sendNotification();
                        }*/
                        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                            aaa( );
                        }*/

                    }
                });
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification() {
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
        mBuilder.setContentText("You have new Reminders.");
        mBuilder.setTicker("New Reminder Alert!");
        mBuilder.setSmallIcon(R.drawable.pen);
        mBuilder.setSound(uri);
        mBuilder.setAutoCancel(true);

        // Add Big View Specific Configuration
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        String[] events = new String[2];
        events[0] = new String("Your first line text ");
        events[1] = new String(" Your second line text");


        // Sets a title for the Inbox style big view
        inboxStyle.setBigContentTitle("You have Reminders:");

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setDataForNotificationWithActionButton() {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("aaaaaaaaaa")
                .setStyle(new NotificationCompat.BigTextStyle().bigText("ffffffff"))
                .setContentText("ffffff");
        Intent answerIntent = new Intent(this, MainActivity.class);
        answerIntent.setAction("Yes");
        PendingIntent pendingIntentYes = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.pen, "Yes", pendingIntentYes);
        answerIntent.setAction("No");
        PendingIntent pendingIntentNo = PendingIntent.getActivity(this, 1, answerIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.addAction(R.drawable.plus, "No", pendingIntentNo);
        sendNotification();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void aaa() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 30);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        sendNotification();
        setDataForNotificationWithActionButton();
    }
}