package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.View;
import android.widget.RemoteViews;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.service.myFetchService;

/**
 * Created by jillhickman on 9/29/15.
 */
public class TodayWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Intent service_start = new Intent(context, myFetchService.class);
        context.startService(service_start);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_scores);
        Intent configIntent = new Intent(context, MainActivity.class);

        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);

        remoteViews.setOnClickPendingIntent(R.id.widget, configPendingIntent);


        String [] SCORES_COLUMNS = {
                DatabaseContract.scores_table.HOME_COL,
                DatabaseContract.scores_table.AWAY_COL,
                DatabaseContract.scores_table.HOME_GOALS_COL,
                DatabaseContract.scores_table.AWAY_GOALS_COL,
//                DatabaseContract.scores_table.TIME_COL,
        };
        //Get data from cursor
        Cursor data = context.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                SCORES_COLUMNS,
                null,
                new String[]{Utilies.getTodayDate()},
                DatabaseContract.scores_table.HOME_GOALS_COL + " DESC LIMIT 1");


        //If we have data, set the views
        if (data != null && data.moveToFirst()) {
            remoteViews.setTextViewText(R.id.widget_home_name, data.getString(0));
            remoteViews.setTextViewText(R.id.widget_away_name, data.getString(1));
            remoteViews.setTextViewText(R.id.widget_score_textview, Utilies.getScores(data.getInt(2), data.getInt(3)));

            //Set the no matches gone while the views visible
            remoteViews.setViewVisibility(R.id.widget_home_name, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.widget_away_name, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.widget_score_textview, View.VISIBLE);
            remoteViews.setViewVisibility(R.id.widget_no_matches, View.GONE);
        }else if(data.getCount() == 0){
            //Set the views gone while the no matches visible since there is no matches
            remoteViews.setViewVisibility(R.id.widget_home_name, View.GONE);
            remoteViews.setViewVisibility(R.id.widget_away_name, View.GONE);
            remoteViews.setViewVisibility(R.id.widget_score_textview, View.GONE);
            remoteViews.setViewVisibility(R.id.widget_no_matches, View.VISIBLE);

        }

        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }
}
