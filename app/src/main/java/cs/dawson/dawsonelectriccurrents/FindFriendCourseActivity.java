package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FindFriendCourseActivity extends MenuActivity
{
    private boolean isAvailable = false;
    private TextView friendAvailable;
    private TextView classInfo;
    private String email;
    private String time;
    private String asweek;
    private String password;
    private String course;
    private String section;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_course);

        email = getIntent().getExtras().getString("friendEmail");

        friendAvailable = (TextView) findViewById(R.id.availableView);
        classInfo = (TextView) findViewById(R.id.classInfoView);

        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.US);
        asweek = dateFormat.format(now);

        int hours = new Time(System.currentTimeMillis()).getHours();
        int minutes = new Time(System.currentTimeMillis()).getMinutes();
        time = hours + "" + minutes;

        CourseAsyncTask course = new CourseAsyncTask();
        course.execute();
    }

    private class CourseAsyncTask extends AsyncTask<String, Void, ArrayList<String>>
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
            String friendUrl = "http://dawsonfriendfinder2017.herokuapp.com/api/api/whereisfriend?" +
                    "email=" + email + "&password=" + password +
                    "&friendemail=" + email + "&day=" + asweek + "&time=" + time;
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
            else {
                friendAvailable.setText(getString(R.string.availableText));
                classInfo.setText("Course: " + course + " \nSection: " + section);
            }

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
