package com.test.widget.utils;


import com.test.widget.BuildConfig;

/**
 * Created by Spidulica on 17-Nov-16.
 */

public class AppLog {
    static public void d(Class<?> c, String msg) {
        String TAG = c.getName();
        if (BuildConfig.DEBUG) {
            android.util.Log.d(TAG, msg);
        }
    }

    static public void i(Class<?> c, String msg) {
        String TAG = c.getName();
        if (BuildConfig.DEBUG) {
            android.util.Log.i(TAG, msg);
        }
    }

    static public void e(Class<?> c, String msg) {
        String TAG = c.getName();
        if (BuildConfig.DEBUG) {
            android.util.Log.e(TAG, msg);
        }
    }
}
