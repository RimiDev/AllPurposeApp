package cs.dawson.dawsonelectriccurrents;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

import cs.dawson.dawsonelectriccurrents.weatherrequest.WeatherRequest;

/**
 * This class is used to generate an httpURLConnection to the https://openweathermap.org/
 * website to gather information on the weather using the five day forecast feature.
 * Created by maximelacasse on 2017-11-22.
 */

public class FiveDayForecastActivity extends MenuActivity {

    TextView JSONresponse;
    //The API key that was genereated for my account on https://openweathermap.org/
    public String apiKey = "&APPID=5b62062bcde765f123614e4c944f8027";
    //The city that the user wants to check the weather for.
    public String city = "Paris";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiveday_forecast);

        Bundle b = getIntent().getExtras();
        city = b.getString("city");

        logIt("I am here: " + city);

        Button btnHit = (Button) findViewById(R.id.btnHit);
        JSONresponse = (TextView) findViewById(R.id.JSONresponse);

        //Calling the WeatherRequest class to demand a request with the weather forecast with user input.
        //Check if city is null, if so, do not execute request (prevents crash).
        if (city != null) {
            WeatherRequest request = new WeatherRequest(city, apiKey);
            request.execute(city, apiKey);
        } else {
            JSONresponse.setText("INVALID");
        }

    } // end of onCreate

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
