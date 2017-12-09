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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FindFriendCourseActivity extends MenuActivity
{
    private boolean isAvailable = false;
    private TextView friendAvailable;
    private String email;
    private int day;
    private String localTime;
    private String dayOfWeek;
    private String course;
    private String section;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_course);

        email = getIntent().getExtras().getString("friendEmail");

        friendAvailable = (TextView) findViewById(R.id.availableView);

        Calendar cal = Calendar.getInstance();
        Date currentLocalTime = cal.getTime();
        DateFormat time = new SimpleDateFormat("HH:mm a");
        localTime = time.format(currentLocalTime);

        DateFormat date = new SimpleDateFormat("dd");
        day = cal.get(Calendar.DAY_OF_WEEK);
        dayOfWeek = date.format(day);

        Log.d("LINE", "time: " + time + " dayOfWeek: " + dayOfWeek + " " + email);


        FriendsAsyncTask hello = new FriendsAsyncTask();
        hello.execute();
    }

    private class FriendsAsyncTask extends AsyncTask<String, Void, ArrayList<String>>
    {
        @Override
        protected ArrayList<String> doInBackground(String... urls)
        {
            //String friendUrl = "http://dawsonfriendfinder2017.herokuapp.com/api/api/whereisfriends?email=alessandromciotola@gmail.com&password=shadowx15" +
                    //"&friendemail=" + email + "&day=" + day + "&time=" + localTime;
            String friendUrl = "http://dawsonfriendfinder2017.herokuapp.com/api/api/whereisfriend?" +
                    "email=alessandromciotola@gmail.com&password=shadowx15" +
                    "&friendemail=" + email + "&day=monday&time=1110";
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

                Log.d("RETURNING", "doInBackground: " + returningResults.toString());

                JSONArray jsonObject = new JSONArray(returningResults.toString());
                    course = jsonObject.getJSONObject(0).getString("course");
                    section = jsonObject.getJSONObject(0).getString("section");
                    Log.d("LINE", "doInBackground: " + course + " " + section);

                if(course != null || !course.equals("") || section != null || !section.equals(""))
                    isAvailable = true;

            }
            catch (IOException | JSONException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> s)
        {
            super.onPostExecute(s);

            if(!isAvailable)
                friendAvailable.setText(R.string.notAvailableText);
            else
                friendAvailable.setText(R.string.availableText + " Course: " + " Section: ");

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
