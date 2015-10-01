package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;

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

//        Date d = new Date();
//        String date = (new SimpleDateFormat("yyyy-MM-dd")).format(d);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);
        String myDate = dateFormat.format(calendar.getTime());

        String [] SCORES_COLUMNS = {
                DatabaseContract.scores_table.HOME_COL,
                DatabaseContract.scores_table.AWAY_COL,
                DatabaseContract.scores_table.HOME_GOALS_COL,
                DatabaseContract.scores_table.AWAY_GOALS_COL,
                DatabaseContract.scores_table.TIME_COL,
        };

        Cursor data = context.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                SCORES_COLUMNS,
                null,
                new String[]{myDate},
//              null,
                DatabaseContract.scores_table.HOME_GOALS_COL + " DESC LIMIT 1");

        int j = data.getCount();

        if (data != null && data.moveToFirst()) {
            remoteViews.setTextViewText(R.id.widget_home_name, data.getString(0));
            remoteViews.setTextViewText(R.id.widget_away_name, data.getString(1));
            remoteViews.setTextViewText(R.id.widget_score_textview, Utilies.getScores(data.getInt(2), data.getInt(3)));
        }
//        mHolder.away_name.setText(data.getString(scoresAdapter.COL_AWAY));
//        mHolder.score.setText(Utilies.getScores(data.getInt(scoresAdapter.COL_HOME_GOALS), data.getInt(scoresAdapter.COL_AWAY_GOALS)));

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}
