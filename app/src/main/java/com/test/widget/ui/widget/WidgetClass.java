package com.test.widget.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.RemoteViews;

import com.test.widget.R;
import com.test.widget.list_view_utils.RemoteFetchService;
import com.test.widget.list_view_utils.WidgetService;
import com.test.widget.utils.SharePref;
import com.test.widget.utils.Utils;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link WidgetClassConfigureActivity WidgetClassConfigureActivity}
 */
public class WidgetClass extends AppWidgetProvider {
    public static final String DATA_FETCHED = "com.wordpress.laaptu.DATA_FETCHED";

    private static HandlerThread sWorkerThread;
    private static Handler sWorkerQueue;

    public WidgetClass() {
        // Start the worker thread
        sWorkerThread = new HandlerThread("MyWidgetProvider-worker");
        sWorkerThread.start();
        sWorkerQueue = new Handler(sWorkerThread.getLooper());
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            Intent serviceIntent = new Intent(context, RemoteFetchService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    appWidgetIds[i]);
            context.startService(serviceIntent);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews updateWidgetListView(Context context, int appWidgetId) {

        // which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_class);

        // RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, WidgetService.class);
        // passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        // setting a unique Uri to the intent
        // don't know its purpose to me right now
        svcIntent.setData(Uri.parse(svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        // setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(R.id.recyclerView,
                svcIntent);
        // setting an empty view in case of no data
        //remoteViews.setEmptyView(R.id.recyclerView, R.id.empty_view);
        return remoteViews;
    }

    /*
     * It receives the broadcast as per the action set on intent filters on
     * Manifest.xml once data is fetched from RemotePostService,it sends
     * broadcast and WidgetProvider notifies to change the data the data change
     * right now happens on ListProvider as it takes RemoteFetchService
     * listItemList as data
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(DATA_FETCHED)) {
            final int appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            final AppWidgetManager appWidgetManager = AppWidgetManager
                    .getInstance(context);
            final RemoteViews remoteViews = updateWidgetListView(context, appWidgetId);
            remoteViews.setTextViewText(R.id.day, Utils.getCurrentDay(SharePref.getCurrentDay(context)));
            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

            sWorkerQueue.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remoteViews.setScrollPosition(R.id.recyclerView, SharePref.getScollPosition(context));
                    appWidgetManager.partiallyUpdateAppWidget(appWidgetId, remoteViews);
                }

            }, 1000);
        }
    }
}

