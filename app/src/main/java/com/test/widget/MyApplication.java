package com.test.widget;

import android.app.Application;

import com.test.widget.scheduler.AlarmReceiver;

/**
 * Created by Spidulica on 22-Mar-17.
 */

public class MyApplication extends Application {
    public static final String TAG = MyApplication.class.getSimpleName();
    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        alarm.setAlarm(this);
    }

}
