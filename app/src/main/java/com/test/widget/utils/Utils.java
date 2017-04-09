package com.test.widget.utils;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.test.widget.entities.Interval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Spidulica on 05-Apr-17.
 */

public class Utils {

    public static void setScrollPosition(Context context) {
        ArrayList<Interval> listItemList = (ArrayList<Interval>) SharePref.getCurrentOrar(context);

        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        SharePref.setCurrentHour(context, currentHour);
        int position = 0;
        if (currentHour >= 8) {
            for (int i = 0; i < listItemList.size(); i++) {
                Interval interval = listItemList.get(i);
                int startHour = Utils.getHour(interval.getStartHour());
                int endHour = Utils.getHour(interval.getEndHour());

                if (startHour <= currentHour && currentHour < endHour) {
                    position = listItemList.indexOf(interval);
                    break;
                } else if (endHour <= currentHour && i < listItemList.size() - 2) {
                    Interval interval1 = listItemList.get(i + 1);
                    int startHour1 = Utils.getHour(interval1.getStartHour());
                    if (currentHour <= startHour1) {
                        position = i + 1;
                        break;
                    }
                } else if (i == listItemList.size() - 1) {
                    position = i;
                }
            }
        }

        SharePref.setScollPosition(context, position);
    }

    private static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getCurrentDay(int dayOfWeeK) {
        String result = "Today";

        switch (dayOfWeeK) {
            case Calendar.SUNDAY:
                result = "Duminica";
                break;
            case Calendar.MONDAY:
                result = "Luni";
                break;
            case Calendar.TUESDAY:
                result = "Marti";
                break;
            case Calendar.WEDNESDAY:
                result = "Miercuri";
                break;
            case Calendar.THURSDAY:
                result = "Joi";
                break;
            case Calendar.FRIDAY:
                result = "Vineri";
                break;
            case Calendar.SATURDAY:
                result = "Sambata";
                break;

        }
        return result;
    }


    public static int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

}
