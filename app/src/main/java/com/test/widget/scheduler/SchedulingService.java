package com.test.widget.scheduler;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.Intent;

import com.test.widget.api_call.ApiCalls;
import com.test.widget.api_call.AppUrlConstants;
import com.test.widget.api_call.CallbackApiListener;
import com.test.widget.list_view_utils.RemoteFetchService;
import com.test.widget.utils.SharePref;
import com.test.widget.utils.Utils;

import java.util.Calendar;


public class SchedulingService extends IntentService implements CallbackApiListener {
    public SchedulingService() {
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String grupa = SharePref.getGrupa(getApplicationContext());
        if(grupa == null){
            return;
        }

        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int myDayOfWeek = SharePref.getCurrentDay(getApplicationContext());
        if(myDayOfWeek != dayOfWeek){
            createCallServer(dayOfWeek, grupa);
            return;
        }

        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int myHourOfDay = SharePref.getCurrentHour(getApplicationContext());

        if(hourOfDay != myHourOfDay){
            moveScrollWidgetPosition();
        }
    }

    private void createCallServer(int dayOfWeek, String grupa) {
        String day = Utils.getCurrentDay(dayOfWeek);
        SharePref.setDay(getApplicationContext(), dayOfWeek);
        String path = AppUrlConstants.BASE_URL + "/orar/like?grupa=" + grupa + "&zi=" + day;
        ApiCalls.getInstance(getApplicationContext()).getStringRequest(path, this);
    }

    private void moveScrollWidgetPosition() {
        Utils.setScrollPosition(getApplicationContext());
        updateWidget();
    }

    private void updateWidget() {
        Intent serviceIntent = new Intent(this, RemoteFetchService.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, SharePref.getAppWidgetId(getApplicationContext()));
        startService(serviceIntent);
    }

    @Override
    public void onSuccessfulResponse(String response) {
        SharePref.setCurrentOrar(SchedulingService.this, response);
        Utils.setScrollPosition(getApplicationContext());
        updateWidget();
    }



    @Override
    public void onFailResponse() {

    }
}
