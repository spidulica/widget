package com.test.widget.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.RemoteViews;

import com.test.widget.R;
import com.test.widget.list_view_utils.WidgetService;
import com.test.widget.utils.SharePref;
import com.test.widget.utils.Utils;

import java.util.Arrays;
import java.util.Calendar;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetClassConfigureActivity WidgetClassConfigureActivity}
 */
public class WidgetProvider extends AppWidgetProvider {
    public static final String UPDATE_DATA = "com.wordpress.laaptu.UPDATE_DATA";
    public static final String TAG = WidgetProvider.class.getName();

    private static HandlerThread sWorkerThread;
    private static Handler sWorkerQueue;

    public WidgetProvider() {
        // Start the worker thread
        sWorkerThread = new HandlerThread("MyWidgetProvider-worker");
        sWorkerThread.start();
        sWorkerQueue = new Handler(sWorkerThread.getLooper());
    }

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.e(TAG, "onUpdate");
        // There may be multiple widgets active, so update all of them

        // Set up the intent that starts the WidgetService, which will
        // provide the views for this collection.
        // When intents are compared, the extras are ignored, so we need to embed the extras
        // into the data so that the extras will not be ignored.
        Intent intent = new Intent(context, WidgetService.class);
        intent.setData(Uri.fromParts("content", Arrays.toString(appWidgetIds), null));

        // Instantiate the RemoteViews object for the App Widget layout.
        // Set up the RemoteViews object to use a RemoteViews adapter.
        // This adapter connects to a RemoteViewsService  through the specified intent.
        // This is how you populate the data.
        final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_class);
        remoteView.setTextViewText(R.id.widget_grupa, SharePref.getGrupa(context));
        remoteView.setTextViewText(R.id.widget_serie, SharePref.getSerie(context));
        remoteView.setRemoteAdapter(R.id.widget_listView, intent);
        setCurrentDay(remoteView, context);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteView);

        sWorkerQueue.postDelayed(new Runnable() {
            @Override
            public void run() {
                remoteView.setScrollPosition(R.id.widget_listView, SharePref.getScollPosition(context));
                appWidgetManager.partiallyUpdateAppWidget(SharePref.getAppWidgetId(context), remoteView);
            }
        }, 200);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        ComponentName thisWidget = new ComponentName(context, WidgetProvider.class);
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        if (intent.getAction().equals(UPDATE_DATA)) {
            // Update remote view
            final RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_class);
            setCurrentDay(remoteView, context);
            appWidgetManager.updateAppWidget(appWidgetIds, remoteView);

            // Update list content of the widget
            // This will call onDataSetChanged() method of WidgetDisplay class
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_listView);

            sWorkerQueue.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remoteView.setScrollPosition(R.id.widget_listView, SharePref.getScollPosition(context));
                    appWidgetManager.partiallyUpdateAppWidget(SharePref.getAppWidgetId(context), remoteView);
                }
            }, 200);
        }
    }


    private void setCurrentDay(RemoteViews remoteView, Context context) {
        int day = SharePref.getCurrentDay(context);

        switch (day) {
            case Calendar.MONDAY:
                remoteView.setTextColor(R.id.widget_day_0, Utils.getColor(context, R.color.text_white));
                remoteView.setTextColor(R.id.widget_day_1, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_2, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_3, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_4, Utils.getColor(context, R.color.text_grey));

                remoteView.setInt(R.id.widget_line_selected_0, "setBackgroundColor", Utils.getColor(context, R.color.blue_widget));
                remoteView.setInt(R.id.widget_line_selected_1, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_2, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_3, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_4, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                break;
            case Calendar.TUESDAY:
                remoteView.setTextColor(R.id.widget_day_0, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_1, Utils.getColor(context, R.color.text_white));
                remoteView.setTextColor(R.id.widget_day_2, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_3, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_4, Utils.getColor(context, R.color.text_grey));

                remoteView.setInt(R.id.widget_line_selected_0, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_1, "setBackgroundColor", Utils.getColor(context, R.color.blue_widget));
                remoteView.setInt(R.id.widget_line_selected_2, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_3, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_4, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                break;
            case Calendar.WEDNESDAY:
                remoteView.setTextColor(R.id.widget_day_0, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_1, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_2, Utils.getColor(context, R.color.text_white));
                remoteView.setTextColor(R.id.widget_day_3, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_4, Utils.getColor(context, R.color.text_grey));

                remoteView.setInt(R.id.widget_line_selected_0, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_1, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_2, "setBackgroundColor", Utils.getColor(context, R.color.blue_widget));
                remoteView.setInt(R.id.widget_line_selected_3, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_4, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                break;
            case Calendar.THURSDAY:
                remoteView.setTextColor(R.id.widget_day_0, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_1, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_2, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_3, Utils.getColor(context, R.color.text_white));
                remoteView.setTextColor(R.id.widget_day_4, Utils.getColor(context, R.color.text_grey));

                remoteView.setInt(R.id.widget_line_selected_0, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_1, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_2, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_3, "setBackgroundColor", Utils.getColor(context, R.color.blue_widget));
                remoteView.setInt(R.id.widget_line_selected_4, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                break;
            case Calendar.FRIDAY:
                remoteView.setTextColor(R.id.widget_day_0, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_1, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_2, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_3, Utils.getColor(context, R.color.text_grey));
                remoteView.setTextColor(R.id.widget_day_4, Utils.getColor(context, R.color.text_white));

                remoteView.setInt(R.id.widget_line_selected_0, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_1, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_2, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_3, "setBackgroundColor", Utils.getColor(context, R.color.background_widget));
                remoteView.setInt(R.id.widget_line_selected_4, "setBackgroundColor", Utils.getColor(context, R.color.blue_widget));
                break;
        }

    }

}
