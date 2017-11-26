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
import java.util.concurrent.ExecutionException;

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
            WeatherRequest request = new WeatherRequest(city, apiKey);
        try {
            parseJSON(request.execute(city, apiKey).get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
     * onPostExecute in the WeatherRequest class passed us the information that
     * doInBackground() passed him (The JSON weather information that we need to parse)
     * we now have the JSON information in a string, we create a JSONObject with that string
     * to be able to manipulate and go around in the JSON file, using JSONArrays and JSONObjects.
     * @param jsonForecastResult Whatever doInBackground returns is this parameter, in this case, JSON info as a String.
     */
    public void parseJSON(String jsonForecastResult){
        //Checks if s is null, if it is, then the user typed a bad city name and we did not
        //get any results from the website, which results in a crash when trying to use
        //bufferreader on a bad HttpURLConnection. This will redirect to a error page for user.
        if (jsonForecastResult != null) {

            try {
                logIt("ResultInOnPostExecute: " + jsonForecastResult);
                //Create a JSONObject with the String JSON results from 'doInBackground' method.
                JSONObject jsonObject = new JSONObject(jsonForecastResult);
                //Grabbing the first item to then grab the weather.
                JSONArray jsonItems = jsonObject.getJSONArray("weather");

                //Grabbing all the individual items associated to the weather.
                String id = jsonItems.getJSONObject(0).getString("id");
                String main = jsonItems.getJSONObject(0).getString("main");

                logIt("jsonItems: " + jsonItems);
                logIt("id: " + id);
                logIt("main: " + main);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //Alert user that input is invalid and that no response is given to us.
        }

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
