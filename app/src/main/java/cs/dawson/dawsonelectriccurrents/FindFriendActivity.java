package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.os.AsyncTask;
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


public class FindFriendActivity extends MenuActivity
{
    private ArrayList<String> friendListNames;
    private ArrayList<String> friendListEmails;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        FriendsAsyncTask task = new FriendsAsyncTask();
        task.execute();



    }

    public void fillListView()
    {
        ListView friendsListView = (ListView) findViewById(R.id.friendsListView);

        if(friendListNames.get(0).equalsIgnoreCase("User has no friends.")){
            TextView noFriendsTV = (TextView) findViewById(R.id.noFriendTV);
            noFriendsTV.setText("No friends");
        }
        else
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.cancelled_class, friendListNames);
            friendsListView.setAdapter(adapter);
            friendsListView.setOnItemClickListener(showFriends);
        }
    }

    private AdapterView.OnItemClickListener showFriends = new AdapterView.OnItemClickListener()
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Intent intent = new Intent(FindFriendActivity.this, FindFriendCourseActivity.class);
            intent.putExtra("friendEmail", friendListEmails.get(position));

            startActivity(intent);
        }
    };

    private class FriendsAsyncTask extends AsyncTask<String, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(String... urls)
        {
            String friendUrl = "http://dawsonfriendfinder2017.herokuapp.com/api/api/allfriends?email=alessandromciotola@gmail.com&password=shadowx15";

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
