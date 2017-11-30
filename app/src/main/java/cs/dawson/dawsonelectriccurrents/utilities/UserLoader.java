package cs.dawson.dawsonelectriccurrents.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import java.util.ArrayList;

import cs.dawson.dawsonelectriccurrents.SettingsActivity;
import cs.dawson.dawsonelectriccurrents.beans.User;
import cs.dawson.dawsonelectriccurrents.database.FriendFinderDBHelper;

/**
 * Performs database manipulation with user table using background thread
 * @author Kevin
 * @version 1.0
 */

public class UserLoader extends AsyncTask<Void, Void, ArrayList<User>> {

    private static final String TAG = "UserLoader";
    private Activity activity;
    private Options option;
    private FriendFinderDBHelper database;
    private String data[];
    private final String USER_PREFS = "user";

    /**
     * Constructor
     * @param option
     * @param settings
     * @param database
     * @param data
     */
    public UserLoader(Options option, SettingsActivity settings, FriendFinderDBHelper database, String[] data) {
        this.option = option;
        this.activity = settings;
        this.database = database;
        this.data = data;
    }

    /**
     * Constructor
     * @param option
     * @param context
     * @param database
     * @param data
     */
    public void UserLoader(Options option, Activity context, FriendFinderDBHelper database, String[] data) {
        this.option = option;
        this.activity = context;
        this.database = database;
        this.data = data;
    }

    @Override
    protected ArrayList<User> doInBackground(Void... voids) {
        ArrayList<User> list = null;

        switch (option) {
            case ADD_USER:
                database.addUser(data);
                break;
            case MODIFY_USER:
                database.editUser(data);
                break;
            default:
                list = new ArrayList<User>();
                break;
        }

        list = database.retrieverUserByEmail(data[2]);
        Log.i(TAG, "Email: " + data[2]);
        User currUser = list.get(0);
        Log.i(TAG, "Current User Email: " + currUser.getEmail());

        // Save in shared preferences
        SharedPreferences prefs = activity.getSharedPreferences(USER_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();
        e.putInt("userId", currUser.getUserId());
        e.putString("firstName", currUser.getFirstName());
        e.putString("lastName", currUser.getLastName());
        e.putString("email", currUser.getEmail());
        e.putString("password", currUser.getPassword());
        e.putString("lastUpdated", currUser.getLastUpdated());
        e.commit();
        return list;
    }
}
