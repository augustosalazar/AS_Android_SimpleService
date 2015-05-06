package com.augustosalazar.simpleservice;

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

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Service Started.");
        mTimer.scheduleAtFixedRate(new MyTask(), 0, 100L);
        isRunning = true;
    }

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Log.i(TAG, "Timer doing work." + counter);
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
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
