package com.test.widget.list_view_utils;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.test.widget.R;
import com.test.widget.entities.Interval;

import java.util.ArrayList;

/**
 * Created by Florin Bucur on 3/27/2017.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private ArrayList<Interval> listItemList;
    private Context context = null;
    private int appWidgetId;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        populateListItem();
    }

    private void populateListItem() {
        if (RemoteFetchService.listItemList == null) {
            listItemList = new ArrayList<>();
        }else{
            System.out.println(listItemList);
            listItemList = (ArrayList<Interval>) RemoteFetchService.listItemList
                    .clone();
        }
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
                context.getPackageName(), R.layout.widget_row);
        Interval listItem = listItemList.get(position);

        remoteView.setTextViewText(R.id.profesor, listItem.getProfesor());
        remoteView.setTextViewText(R.id.materie, listItem.getMaterie());
        remoteView.setTextViewText(R.id.locatie, listItem.getLocatie());
        remoteView.setTextViewText(R.id.interval, listItem.getOraInceput() + " - " + listItem.getOraSfarsit());

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
