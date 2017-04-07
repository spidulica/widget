package com.test.widget.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.RemoteViews;

import com.test.widget.R;
import com.test.widget.list_view_utils.WidgetService;
import com.test.widget.utils.SharePref;
import com.test.widget.utils.Utils;

import java.util.Arrays;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetClassConfigureActivity WidgetClassConfigureActivity}
 */
public class WidgetProvider extends AppWidgetProvider {
    public static final String UPDATE_DATA = "com.wordpress.laaptu.UPDATE_DATA";

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
        RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget_class);
        remoteView.setTextViewText(R.id.day, Utils.getCurrentDay(SharePref.getCurrentDay(context)));
        remoteView.setRemoteAdapter(R.id.listView, intent);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteView);
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
            appWidgetManager.updateAppWidget(appWidgetIds, remoteView);

            // Update list content of the widget
            // This will call onDataSetChanged() method of WidgetDisplay class
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView);

            sWorkerQueue.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remoteView.setScrollPosition(R.id.listView, SharePref.getScollPosition(context));
                    appWidgetManager.partiallyUpdateAppWidget(SharePref.getAppWidgetId(context), remoteView);
                }
            }, 200);
        }
    }
}
