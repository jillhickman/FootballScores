package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;
import barqsoft.footballscores.service.MyFetchService;

/**
 * Created by jillhickman on 9/29/15.
 */
public class TodayWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Starting the service to get the data for the widget
        Intent service_start = new Intent(context, MyFetchService.class);
        context.startService(service_start);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_scores);

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

}
