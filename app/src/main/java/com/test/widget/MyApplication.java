package com.test.widget;

import android.app.Application;
import android.content.Intent;

import com.test.widget.scheduler.AlarmReceiver;
import com.test.widget.scheduler.SchedulingService;

/**
 * Created by Spidulica on 22-Mar-17.
 */

public class MyApplication extends Application{
    public static final String TAG = MyApplication.class.getSimpleName();
    AlarmReceiver alarm = new AlarmReceiver();

    @Override
    public void onCreate() {
        super.onCreate();
        Intent service = new Intent(this, SchedulingService.class);
        startService(service);
        alarm.setAlarm(this);
    }
}
