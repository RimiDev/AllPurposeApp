package cs.dawson.dawsonelectriccurrents.cancelled;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import cs.dawson.dawsonelectriccurrents.FindFriendActivity;
import cs.dawson.dawsonelectriccurrents.FindFriendCourseActivity;
import cs.dawson.dawsonelectriccurrents.MenuActivity;
import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.beans.CancelledClass;

public class ShowCancelledFriends extends MenuActivity
{
    private ArrayList<String> friendListNames;
    private ArrayList<String> friendListEmails;
    private String course;
    private String section;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cancelled_friends);

        CancelledClass cancelledClass = (CancelledClass) getIntent().getSerializableExtra("course");
        String[] title = cancelledClass.getTitle().split("\\s+");
        course = title[0];
        section = title[1];

        Log.d("SHOWCANCELLEDFRIENDS", "onCreate: " + course + " " + section);

        CancelledFriendsAsyncTask task = new CancelledFriendsAsyncTask();
        task.execute();
    }

    private class CancelledFriendsAsyncTask extends AsyncTask<String, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(String... urls)
        {
            SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
            if (prefs != null) {
                // Edit the textviews for the current shared preferences
                email = prefs.getString("email", "");
                password = prefs.getString("pw", "");
            }

            String friendUrl = "http://dawsonfriendfinder2017.herokuapp.com/api/api/coursefriends?" +
                    "email=" + email + "&password=" + password + "&course=" + course + "&section=" + section;

            Log.d("DOIN", "doInBackground: " + friendUrl);

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

    public void fillListView()
    {
        ListView friendsListView = (ListView) findViewById(R.id.friendsCancelledListView);

        if(friendListNames == null || friendListNames.get(0).equalsIgnoreCase("No course found.")){
            TextView noFriendsCancelledTV = (TextView) findViewById(R.id.noFriendCancelledTV);
            noFriendsCancelledTV.setText("No friends in this course!");
        }
        else
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.cancelled_class, friendListNames);
            friendsListView.setAdapter(adapter);
            friendsListView.setOnItemClickListener(emailFriends);
        }
    }

    private AdapterView.OnItemClickListener emailFriends = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent sendEMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", friendListEmails.get(position), null));
            sendEMail.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.from) + " " + getResources().getString(R.string.app_name));
            startActivity(Intent.createChooser(sendEMail, getResources().getString(R.string.sendemail)));
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
