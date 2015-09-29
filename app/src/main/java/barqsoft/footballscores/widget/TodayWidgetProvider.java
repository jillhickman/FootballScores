package barqsoft.footballscores.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

/**
 * Created by jillhickman on 9/29/15.
 */
public class TodayWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

//        final int N = appWidgetIds.length;
//
//        // Perform this loop procedure for each Today widget
//        for (int appWidgetId : appWidgetIds) {
//            int layoutId = R.layout.widget_today_small;
//            RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);
//
//
//            // Add the data to the RemoteViews
//            views.setImageViewResource(R.id.widget_icon, weatherArtResourceId);
//            // Content Descriptions for RemoteViews were only added in ICS MR1
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
//                setRemoteContentDescription(views, description);
//            }
//            views.setTextViewText(R.id.widget_high_temperature, formattedMaxTemperature);
//
//
//            // Create an Intent to launch MainActivity
//            Intent launchIntent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, launchIntent, 0);
//            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
//
//
//            // Tell the AppWidgetManager to perform an update on the current app widget
//            appWidgetManager.updateAppWidget(appWidgetId, views);

    }
}
