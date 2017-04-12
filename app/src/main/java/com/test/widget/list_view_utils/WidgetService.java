package com.test.widget.list_view_utils;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

    private ArrayList<Interval> listItemList;
    private int scrollPoss;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return this;
    }

    @Override
    public void onDataSetChanged() {
        listItemList = (ArrayList<Interval>) SharePref.getCurrentOrar(getApplicationContext());
        scrollPoss = SharePref.getScollPosition(getApplicationContext());
        Log.e(WidgetService.class.getName(), "onDataSetChanged:" + scrollPoss);
    }

    @Override
    public int getCount() {
        return listItemList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews remoteView = new RemoteViews(this.getPackageName(), R.layout.widget_row);
        Interval listItem = listItemList.get(position);

        remoteView.setTextViewText(R.id.profesor, listItem.getProfesor());
        remoteView.setTextViewText(R.id.materie, listItem.getMaterie());
        remoteView.setTextViewText(R.id.locatie, listItem.getLocatie());
        remoteView.setTextViewText(R.id.start, listItem.getOraInceput() );
        remoteView.setTextViewText(R.id.end, listItem.getOraSfarsit());

        if (position == scrollPoss) {
            remoteView.setInt(R.id.selected_card_line, "setBackgroundColor", ContextCompat.getColor(getApplicationContext(), R.color.blue_widget));
        } else {
            remoteView.setInt(R.id.selected_card_line, "setBackgroundColor", ContextCompat.getColor(getApplicationContext(), R.color.card_background));
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
    public void onDestroy() {
    }
}
