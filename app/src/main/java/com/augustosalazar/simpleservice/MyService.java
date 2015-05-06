package com.augustosalazar.simpleservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {

    private static final String TAG = "MyService";
    private Timer mTimer = new Timer();
    private static boolean isRunning = false;
    private int counter = 0;
    private NotificationManager mNotificationManager;
    Notification mNotification;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service Started.");
        mTimer.scheduleAtFixedRate(new MyTask(), 0, 100L);
        isRunning = true;
        showNotification();
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "Timer doing work." + counter);
            updateNotification();
            try {
                counter += 1;
            } catch (Throwable t) {
                Log.e("TimerTick", "Timer Tick Failed.", t);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {mTimer.cancel();}
        counter=0;
        Log.i(TAG, "Service Stopped.");
        isRunning = false;
        cancelNotification();
    }

    private void showNotification() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotification = new Notification(R.mipmap.ic_launcher, "Trabajando", System.currentTimeMillis());

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        mNotification.setLatestEventInfo(this, getText(R.string.service_label), "Trabajando" + counter, contentIntent);
        mNotificationManager.notify(R.mipmap.ic_launcher, mNotification);
    }

    private void cancelNotification(){
        mNotificationManager.cancel(R.mipmap.ic_launcher); // Cancel the persistent notification.
    }

    private void updateNotification() {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        mNotification.setLatestEventInfo(this, getText(R.string.service_label), "Trabajando.." + counter, contentIntent);
        mNotificationManager.notify(R.mipmap.ic_launcher, mNotification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
