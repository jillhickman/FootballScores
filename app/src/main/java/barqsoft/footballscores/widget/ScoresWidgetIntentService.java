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

    }
}
