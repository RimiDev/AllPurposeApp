package cs.dawson.dawsonelectriccurrents;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import java.util.ArrayList;
import cs.dawson.dawsonelectriccurrents.beans.User;
import cs.dawson.dawsonelectriccurrents.database.FriendFinderDBHelper;
import cs.dawson.dawsonelectriccurrents.utilities.FriendBreakLoader;

/**
 * This class takes care of finding any friend from the user that is on break with the specified time
 * from the user
 * @author Kevin Bui
 * @author Maxime Lacasse
 * @version 1.0
 */

public class FIndBreakActivity extends MenuActivity {

    private static final String TAG = FIndBreakActivity.class.getName();
    private String day;
    private String startTime;
    private String endTime;
    private ListView listViewFriends;
    private String email;
    private String password;
    private FriendFinderDBHelper database;
    private ArrayList<User> user;

    // Keys
    private final String USERS_PREFS = "user";
    private static final String BUNDLE_DAY = "day";
    private static final String BUNDLE_START = "starttime";
    private static final String BUNDLE_END = "endtime";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "pw";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_break);
        database = new FriendFinderDBHelper(this);
        database.getWritableDatabase();

        // Get the list view
        listViewFriends = (ListView) findViewById(R.id.listViewFriends);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            day = extras.getString(BUNDLE_DAY);
            startTime = extras.getString(BUNDLE_START);
            endTime = extras.getString(BUNDLE_END);
        }

        SharedPreferences prefs = getSharedPreferences(USERS_PREFS, MODE_PRIVATE);
        if (prefs != null) {
            // Edit the textviews for the current shared preferences
            email = prefs.getString(EMAIL, "");
            password = prefs.getString(PASSWORD, "");
        }

        // Call the Async task
        FriendBreakLoader friendsBreaks = new FriendBreakLoader(this, email, password, day, startTime, endTime);
        friendsBreaks.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to easily log to logcat
     *
     * @param msg to be printed to logcat
     */
    public static void logIt(String msg) {
        final String TAG = "---------WEATHER: ";
        Log.d(TAG, msg);
    }

}