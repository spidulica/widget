package com.test.widget.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.securepreferences.SecurePreferences;
import com.test.widget.entities.Interval;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Spidulica on 05-Apr-17.
 */

public class SharePref {

    private static SharedPreferences mSharedPreferences;

    private static SharedPreferences getSharedPreferences(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = new SecurePreferences(context);
        }
        return mSharedPreferences;
    }

    private static final String GRUPA = "grupa";
    private static final String CURRENT_DAY = "current_day";
    private static final String CURRENT_HOUR = "current_hour";
    private static final String SCROLL_POSITION = "scroll_position";
    private static final String CURRENT_ORAR = "current_orar";
    private static final String EXTRA_APPWIDGET_ID = "extra_appwidget_id";

    public static String getGrupa(Context context) {
        return getSharedPreferences(context).getString(GRUPA, null);
    }

    public static void setGrupa(Context context, String grupa) {
        getSharedPreferences(context).edit().putString(GRUPA, grupa).apply();
    }

    public static Integer getCurrentDay(Context context) {
        return getSharedPreferences(context).getInt(CURRENT_DAY, 0);
    }

    public static void setDay(Context context, int currentDay) {
        getSharedPreferences(context).edit().putInt(CURRENT_DAY, currentDay).apply();
    }

    public static Integer getCurrentHour(Context context) {
        return getSharedPreferences(context).getInt(CURRENT_HOUR, 0);
    }

    public static void setCurrentHour(Context context, int currentHour) {
        getSharedPreferences(context).edit().putInt(CURRENT_HOUR, currentHour).apply();
    }

    public static Integer getScollPosition(Context context) {
        return getSharedPreferences(context).getInt(SCROLL_POSITION, 0);
    }

    public static void setScollPosition(Context context, int currentHour) {
        getSharedPreferences(context).edit().putInt(SCROLL_POSITION, currentHour).apply();
    }

    public static List<Interval> getCurrentOrar(Context context) {
        String response = getSharedPreferences(context).getString(CURRENT_ORAR, null);
        if (response != null) {
            List<Interval> listItemList = new Gson().fromJson(response, new TypeToken<ArrayList<Interval>>() {
            }.getType());

            for (Interval entry : listItemList) {
                DateFormat format = new SimpleDateFormat("hh:mm", Locale.getDefault());
                Date startDate;
                Date endDate;
                try {
                    startDate = format.parse(entry.getOraInceput());
                    endDate = format.parse(entry.getOraSfarsit());
                } catch (ParseException e) {
                    e.printStackTrace();
                    startDate = new Date();
                    endDate = new Date();
                }
                entry.setStartHour(startDate);
                entry.setEndHour(endDate);
            }

            Collections.sort(listItemList, new Comparator<Interval>() {
                @Override
                public int compare(Interval rInterval, Interval lInterval) {
                    return rInterval.getStartHour().compareTo(lInterval.getStartHour());
                }
            });

            return listItemList;
        } else {
            return new ArrayList<>();
        }
    }

    public static void setCurrentOrar(Context context, String currentOrar) {
        getSharedPreferences(context).edit().putString(CURRENT_ORAR, currentOrar).apply();
    }

    public static Integer getAppWidgetId(Context context) {
        return getSharedPreferences(context).getInt(EXTRA_APPWIDGET_ID, 0);
    }

    public static void setAppWidgetId(Context context, int appWidgetId) {
        getSharedPreferences(context).edit().putInt(EXTRA_APPWIDGET_ID, appWidgetId).apply();
    }

}
