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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Florin Bucur on 3/27/2017.
 */

public class RemoteFetchService extends Service implements CallbackApiListener{

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    public static ArrayList<Interval> listItemList;
    private String grupa;

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
            System.out.println(grupa);
        }
        ApiCalls.getInstance(getApplicationContext()).getRequest(AppUrlConstants.BASE_URL, RemoteFetchService.this);
        return super.onStartCommand(intent, flags, startId);
    }

    private void populateWidget() {

        Intent widgetUpdateIntent = new Intent();
        widgetUpdateIntent.setAction(WidgetClass.DATA_FETCHED);
        widgetUpdateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        sendBroadcast(widgetUpdateIntent);

        this.stopSelf();
    }

    @Override
    public void onSuccessfulResponse(String response) {
        HashMap<String, ArrayList<Interval>> orar = new Gson().fromJson(response, new TypeToken<HashMap<String, ArrayList<Interval>>>() {
        }.getType());
        listItemList = new ArrayList<Interval>();
        for(Map.Entry<String, ArrayList<Interval>> entry : orar.entrySet()){
            for(Interval interval : entry.getValue()){
                listItemList.add(interval);
            }
        }
        populateWidget();
    }

    @Override
    public void onFailResponse() {

    }
}
