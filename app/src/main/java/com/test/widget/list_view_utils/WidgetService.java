package com.test.widget.list_view_utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.test.widget.R;
import com.test.widget.entities.Interval;
import com.test.widget.utils.SharePref;

import java.util.ArrayList;

/**
 * Created by Florin Bucur on 3/27/2017.
 */

public class WidgetService extends RemoteViewsService implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Interval> listItemList = new ArrayList<>();
    private int scrollPoss;
    private int appWidgetId;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        int appWidgetId = intent.getIntExtra(
                AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        populateListItem();
        return this;
    }

    private void populateListItem() {
        listItemList = (ArrayList<Interval>) SharePref.getCurrentOrar(getApplicationContext());
        scrollPoss = SharePref.getScollPosition(getApplicationContext());
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(
                this.getPackageName(), R.layout.widget_row);
        Interval listItem = listItemList.get(position);

        remoteView.setTextViewText(R.id.profesor, listItem.getProfesor());
        remoteView.setTextViewText(R.id.materie, listItem.getMaterie());
        remoteView.setTextViewText(R.id.locatie, listItem.getLocatie());
        remoteView.setTextViewText(R.id.interval, listItem.getOraInceput() + " - " + listItem.getOraSfarsit());

        if (position == scrollPoss) {
            remoteView.setInt(R.id.row_item, "setBackgroundColor",
                    Color.WHITE);
        }

        return remoteView;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }
}
