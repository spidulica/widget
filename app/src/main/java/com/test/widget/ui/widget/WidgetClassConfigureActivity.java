package com.test.widget.ui.widget;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.test.widget.R;
import com.test.widget.list_view_utils.RemoteFetchService;

/**
 * The configuration screen for the {@link WidgetClass WidgetClass} AppWidget.
 */
public class WidgetClassConfigureActivity extends Activity implements View.OnClickListener {
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static final String PREFS_NAME = "com.test.widget.ui.widget.WidgetClass";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    EditText mAppWidgetText;


    private void startWidget() {

        // this intent is essential to show the widget
        // if this intent is not included,you can't show
        // widget on homescreen
        Intent intent = new Intent();
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(Activity.RESULT_OK, intent);

        // start your service
        // to fetch data from web
        Intent serviceIntent = new Intent(this, RemoteFetchService.class);
        serviceIntent
                .putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.putExtra("grupa", mAppWidgetText.getText().toString());
        startService(serviceIntent);

        // finish this activity
        this.finish();

    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        super.onCreate(icicle);
        setContentView(R.layout.widget_class_configure);

        assignAppWidgetId();
        findViewById(R.id.add_button).setOnClickListener(this);
        mAppWidgetText = (EditText)findViewById(R.id.appwidget_text);

    }



    private void assignAppWidgetId() {
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_button)
            startWidget();
    }
}

