package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by jillhickman on 9/29/15.
 */
public class TodayWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

//        final int count = appWidgetIds.length;
//
//        for (int i = 0; i < count; i++) {
//            int widgetId = appWidgetIds[i];
//            String number = String.format("%03d", (new Random().nextInt(900) + 100));
//
//            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                    R.layout.simple_widget);
//            remoteViews.setTextViewText(R.id.textView, number);
//
//            Intent intent = new Intent(context, SimpleWidgetProvider.class);
//            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
//                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
//            appWidgetManager.updateAppWidget(widgetId, remoteViews);
//        }
// Perform this loop procedure for each Today widget
//        for (int appWidgetId : appWidgetIds) {
//            int layoutId = R.layout.widget_scores;
//            RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);
//            // Create an Intent to launch MainActivity
//            Intent launchIntent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
//            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
//        }

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_scores);
        Intent configIntent = new Intent(context, MainActivity.class);

        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.widget, configPendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}
