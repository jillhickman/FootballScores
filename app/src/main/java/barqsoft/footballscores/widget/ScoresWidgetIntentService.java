package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.Utilies;

/**
 * Created by jillhickman on 10/1/15.
 */
public class ScoresWidgetIntentService extends IntentService {

    public static String mHomeTeam;
    public static String mAwayTeam;
    public static int mHomeGoals;
    public static int mAwayGoals;

    private static final String [] SCORES_COLUMNS = {
            DatabaseContract.scores_table.HOME_COL,
            DatabaseContract.scores_table.AWAY_COL,
            DatabaseContract.scores_table.HOME_GOALS_COL,
            DatabaseContract.scores_table.AWAY_GOALS_COL,
//                DatabaseContract.scores_table.TIME_COL,
    };
    // these indices must match the projection above
    private static final int INDEX_HOME = 0;
    private static final int INDEX_AWAY = 1;
    private static final int INDEX_HOME_GOALS = 2;
    private static final int INDEX_AWAY_GOALS = 3;

    public ScoresWidgetIntentService() {
        super("ScoresWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Retrieve all of the Today widget ids: these are the widgets we need to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                TodayWidgetProvider.class));

        //Get today's data from the Content Provider
        Cursor data = getContentResolver().query(DatabaseContract.scores_table.buildScoreWithDate(),
                SCORES_COLUMNS,
                null,
                new String[]{Utilies.getTodayDate()},
                //Just give me the last game. Descending order limit to 1
                DatabaseContract.scores_table.HOME_GOALS_COL + " DESC LIMIT 1");
        if (data == null) {
            return;
        }
        if (!data.moveToFirst()) {
            data.close();
            return;
        }
        // Extract the data from the Cursor
        mHomeTeam = data.getString(INDEX_HOME);
        mAwayTeam = data.getString(INDEX_AWAY);
        mHomeGoals = data.getInt(INDEX_HOME_GOALS);
        mAwayGoals = data.getInt(INDEX_AWAY_GOALS);
        data.close();

//        // Perform this loop procedure for each Today widget
//        for (int appWidgetId : appWidgetIds) {
//            int layoutId = R.layout.widget_scores;
//            RemoteViews views = new RemoteViews(getPackageName(), layoutId);
//
//        //If we have data, set the views
//        if (data != null && data.moveToFirst()) {
//            views.setTextViewText(R.id.widget_home_name, data.getString(0));
//            views.setTextViewText(R.id.widget_away_name, data.getString(1));
//            views.setTextViewText(R.id.widget_score_textview, Utilies.getScores(data.getInt(2), data.getInt(3)));
//
//            //Set the no matches gone while the views visible
//            views.setViewVisibility(R.id.widget_home_name, View.VISIBLE);
//            views.setViewVisibility(R.id.widget_away_name, View.VISIBLE);
//            views.setViewVisibility(R.id.widget_score_textview, View.VISIBLE);
//            views.setViewVisibility(R.id.widget_no_matches, View.GONE);
//        }else if(data.getCount() == 0){
//            //Set the views gone while the no matches visible since there is no matches
//            views.setViewVisibility(R.id.widget_home_name, View.GONE);
//            views.setViewVisibility(R.id.widget_away_name, View.GONE);
//            views.setViewVisibility(R.id.widget_score_textview, View.GONE);
//            views.setViewVisibility(R.id.widget_no_matches, View.VISIBLE);
//        }
//
//
//            // Create an Intent to launch MainActivity
//            Intent launchIntent = new Intent(this, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
//            views.setOnClickPendingIntent(R.id.widget, pendingIntent);
//
//            appWidgetManager.updateAppWidget(appWidgetId, views);
//
//        }


    }
//    //Update message from service
//    public void onReceive(Context ctx, Intent intent) {
//        final String action = intent.getAction();
//        if (action.equals(SHOW_NEW_DATA_ACTION)) {
//            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//            final ComponentName cn = new ComponentName(context, TodayWidgetProvider.class);
//            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.widget_scores);
//        }
//        super.onReceive(ctx, intent);
//    }
}
