package com.test.widget.list_view_utils;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.widget.api_call.ApiCalls;
import com.test.widget.api_call.AppUrlConstants;
import com.test.widget.api_call.CallbackApiListener;
import com.test.widget.entities.Interval;
import com.test.widget.ui.widget.WidgetClass;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Florin Bucur on 3/27/2017.
 */

public class RemoteFetchService extends Service implements CallbackApiListener{

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public static ArrayList<Interval> listItemList;
    private String grupa;
    private String day;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.hasExtra(AppWidgetManager.EXTRA_APPWIDGET_ID)) {
            appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            grupa = intent.getStringExtra("grupa");
        }
        day = getCurrentDay();
        System.out.println("asdadada " + day);
        String path = AppUrlConstants.BASE_URL + "/orar/like?grupa="+ grupa+"&zi=" + day;
        ApiCalls.getInstance(getApplicationContext()).getStringRequest(path, RemoteFetchService.this);
        return super.onStartCommand(intent, flags, startId);
    }

    private void populateWidget() {

        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(WidgetClass.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        widgetUpdateIntent.putExtra("Day", day);
        sendBroadcast(widgetUpdateIntent);

        this.stopSelf();
    }

    @Override
    public void onSuccessfulResponse(String response) {
        ArrayList<Interval> orar = new Gson().fromJson(response, new TypeToken<ArrayList<Interval>>() {
        }.getType());
        listItemList = new ArrayList<Interval>();
        for(Interval entry : orar){
            listItemList.add(entry);
        }
        populateWidget();
    }

    @Override
    public void onFailResponse() {

    }

    private String getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        String result = "Today";

        switch (day) {
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
}
