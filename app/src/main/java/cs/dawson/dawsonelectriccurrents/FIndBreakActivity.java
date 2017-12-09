package cs.dawson.dawsonelectriccurrents;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import cs.dawson.dawsonelectriccurrents.adapters.FriendAdapter;
import cs.dawson.dawsonelectriccurrents.beans.User;
import cs.dawson.dawsonelectriccurrents.database.FriendFinderDBHelper;

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

    private ArrayList<String> friendListNames;
    private ArrayList<String> friendListEmails;


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

            Log.i(TAG, "Day: " + day);
            Log.i(TAG, "Start: " + startTime);
            Log.i(TAG, "End: " + endTime);
        }

        SharedPreferences prefs = getSharedPreferences(USERS_PREFS, MODE_PRIVATE);
        if (prefs != null) {
            // Edit the textviews for the current shared preferences
            email = prefs.getString(EMAIL, "");
            password = prefs.getString(PASSWORD, "");
        }

        FriendsBreakASyncTask friendsBreaks = new FriendsBreakASyncTask();
        friendsBreaks.execute();

    }


    public void fillListView()
    {
        ListView friendsListView = (ListView) findViewById(R.id.listViewFriends);

        if(friendListNames.get(0).equalsIgnoreCase("User has no friends.")){
            TextView noFriendsTV = (TextView) findViewById(R.id.listItem);
            noFriendsTV.setText("No friends");
        }
        else
        {
            FriendAdapter adapter = new FriendAdapter(this, friendListNames, friendListEmails);
            friendsListView.setAdapter(adapter);
            //EMAIL SHIT
            //friendsListView.setOnItemClickListener(showFriends);
        }
    }



    private class FriendsBreakASyncTask extends AsyncTask<String, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(String... urls)
        {
            String friendUrl = "http://dawsonfriendfinder2017.herokuapp.com/api/api/friendbreaks?" +
                    "email=" + email + "&password=" + password + "&day=" + day + "&starttime=" + startTime
                    + "&endtime=" + endTime;
            
            logIt("URLFRIENDS: " + friendUrl);


            try
            {
                URL url = new URL(friendUrl);
                HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                boolean isError = httpConnection.getResponseCode() >= 400;
                InputStream is = isError ? new BufferedInputStream(httpConnection.getErrorStream()) :
                        new BufferedInputStream(httpConnection.getInputStream());

                String result = "";
                StringBuilder returningResults = new StringBuilder();
                InputStreamReader inStreamResults = new InputStreamReader(is);
                BufferedReader readBuffer = new BufferedReader(inStreamResults);

                while ((result = readBuffer.readLine()) != null)
                    returningResults.append(result);

                JSONArray jsonArray = new JSONArray(returningResults.toString());
                friendListNames = new ArrayList<>();
                friendListEmails = new ArrayList<>();
                for(int i = 0 ; i< jsonArray.length(); i++)
                {
                    String email = jsonArray.getJSONObject(i).getString("email");
                    String name = jsonArray.getJSONObject(i).getString("name");
                    friendListNames.add(name);
                    friendListEmails.add(email);
                    Log.d("LINE", "doInBackground: " + email + " " + name);
                }
            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }
            return friendListNames;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s)
        {
            super.onPostExecute(s);
            fillListView();
        }
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