package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.Utilies;
import barqsoft.footballscores.service.myFetchService;

/**
 * Created by jillhickman on 9/29/15.
 */
public class TodayWidgetProvider extends AppWidgetProvider {
    public static final String DATABASE_CHANGED = "DATA_CHANGED";
    public static final String HOME_COL_KEY = "home key";
    public static final String AWAY_COL_KEY = "away key";
    public static final String HOME_GOALS_KEY = "home goals key";
    public static final String AWAY_GOALS_KEY = "away goals key";
    public static final String NO_DATA = "no data";


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Calling the app's update data
        Intent service_start = new Intent(context, myFetchService.class);
        context.startService(service_start);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_scores);
        //Fires the app from the widget
        Intent configIntent = new Intent(context, MainActivity.class);
        PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget, configPendingIntent);


//        String [] SCORES_COLUMNS = {
//                DatabaseContract.scores_table.HOME_COL,
//                DatabaseContract.scores_table.AWAY_COL,
//                DatabaseContract.scores_table.HOME_GOALS_COL,
//                DatabaseContract.scores_table.AWAY_GOALS_COL,
////                DatabaseContract.scores_table.TIME_COL,
//        };
//        //Get data from cursor
//        Cursor data = context.getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
//                SCORES_COLUMNS,
//                null,
//                new String[]{Utilies.getTodayDate()},
//                DatabaseContract.scores_table.HOME_GOALS_COL + " DESC LIMIT 1");


        //If we have data, set the views

//            remoteViews.setTextViewText(R.id.widget_home_name, HOME_COL_KEY);
//            remoteViews.setTextViewText(R.id.widget_away_name, AWAY_COL_KEY);
////            remoteViews.setTextViewText(R.id.widget_score_textview, Utilies.getScores((HOME_GOALS_KEY), AWAY_GOALS_KEY));
//
//            //Set the no matches gone while the views visible
//            remoteViews.setViewVisibility(R.id.widget_home_name, View.VISIBLE);
//            remoteViews.setViewVisibility(R.id.widget_away_name, View.VISIBLE);
//            remoteViews.setViewVisibility(R.id.widget_score_textview, View.VISIBLE);
//            remoteViews.setViewVisibility(R.id.widget_no_matches, View.GONE);
//
//            //Set the views gone while the no matches visible since there is no matches
//            remoteViews.setViewVisibility(R.id.widget_home_name, View.GONE);
//            remoteViews.setViewVisibility(R.id.widget_away_name, View.GONE);
//            remoteViews.setViewVisibility(R.id.widget_score_textview, View.GONE);
//            remoteViews.setViewVisibility(R.id.widget_no_matches, View.VISIBLE);



        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(DATABASE_CHANGED)) {
            AppWidgetManager gm = AppWidgetManager.getInstance(context);
            int[] ids = gm.getAppWidgetIds(new ComponentName(context, TodayWidgetProvider.class));
            this.onUpdate(context, gm, ids);

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_scores);

            if (intent.hasExtra(NO_DATA)){
                //Set the views gone while the no matches visible since there is no matches
                remoteViews.setViewVisibility(R.id.widget_home_name, View.GONE);
                remoteViews.setViewVisibility(R.id.widget_away_name, View.GONE);
                remoteViews.setViewVisibility(R.id.widget_score_textview, View.GONE);
                remoteViews.setViewVisibility(R.id.widget_no_matches, View.VISIBLE);
            }else {
                String homeTeam = intent.getStringExtra(TodayWidgetProvider.HOME_COL_KEY);
                Log.i("TodayWidgetProvider", homeTeam);
                String awayTeam = intent.getStringExtra(TodayWidgetProvider.AWAY_COL_KEY);
                Log.i("TodayWidgetProvider", awayTeam);
                int homeGoals = intent.getIntExtra(TodayWidgetProvider.HOME_GOALS_KEY, -9999);
                int awayGoals = intent.getIntExtra(TodayWidgetProvider.AWAY_GOALS_KEY, -9999);

                //Set the no matches gone while the views visible
                remoteViews.setViewVisibility(R.id.widget_home_name, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.widget_away_name, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.widget_score_textview, View.VISIBLE);
                remoteViews.setViewVisibility(R.id.widget_no_matches, View.GONE);

                remoteViews.setTextViewText(R.id.widget_home_name, homeTeam);
                remoteViews.setTextViewText(R.id.widget_away_name, awayTeam);

                if (homeGoals == -9999 || awayGoals == -9999) {
                    remoteViews.setTextViewText(R.id.widget_score_textview, "-");
                } else {
                    remoteViews.setTextViewText(R.id.widget_score_textview, Utilies.getScores(homeGoals, awayGoals));
                }
            }

        } else {
            super.onReceive(context, intent);
        }
    }

}
