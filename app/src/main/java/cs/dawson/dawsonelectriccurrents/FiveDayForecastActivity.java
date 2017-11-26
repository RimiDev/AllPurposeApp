package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    //Weather types for 5 day range.
    TextView weatherTypeDay1;
    TextView weatherTypeDay2;
    TextView weatherTypeDay3;
    TextView weatherTypeDay4;
    TextView weatherTypeDay5;

    //Weather temperature for 5 day range.
    TextView weatherTempDay1;
    TextView weatherTempDay2;
    TextView weatherTempDay3;
    TextView weatherTempDay4;
    TextView weatherTempDay5;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiveday_forecast);

        Bundle b = getIntent().getExtras();
        city = b.getString("city");

        setUpWeatherDisplays();

        //Calling the WeatherRequest class to demand a request with the weather forecast with user input.
        WeatherRequest request = new WeatherRequest(city, apiKey);
        try {
            parseJSONandDisplay(request.execute(city, apiKey).get());
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
     * Whilst parsing the JSONObject, we will be setting the textfields to display the results to the user.
     * @param jsonForecastResult Whatever doInBackground returns is this parameter, in this case, JSON info as a String.
     */
    public void parseJSONandDisplay(String jsonForecastResult){
        //Checks if s is null, if it is, then the user typed a bad city name and we did not
        //get any results from the website, which results in a crash when trying to use
        //bufferreader on a bad HttpURLConnection. This will redirect to a error page for user.
        if (jsonForecastResult != null) {

            try {
                logIt("ResultInOnPostExecute: " + jsonForecastResult);
                //Create a JSONObject with the String JSON results from 'doInBackground' method.
                JSONObject jsonObject = new JSONObject(jsonForecastResult);
                //Grabbing the first item to then grab the weather.
                JSONArray jsonItems = jsonObject.getJSONArray("list");

                logIt("jsonObject: " + jsonObject);
                logIt("jsonItems: " + jsonItems);

                for (int i = 0; i < 5; i++) {
                    //Grabbing all the individual items associated to the weather.
                    String temp = jsonItems.getJSONObject(i).getJSONObject("main").getString("temp");
                    String main = jsonItems.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");

                    logIt("temp: " + temp);
                    logIt("main: " + main);
                }

                //Displaying the results.



//                weatherTempDay1.setText(main);
//                weatherTypeDay1.setText(id);
//
//                weatherTempDay2.setText(main);
//                weatherTypeDay2.setText(id);
//
//                weatherTempDay3.setText(main);
//                weatherTypeDay3.setText(id);
//
//                weatherTempDay4.setText(main);
//                weatherTypeDay4.setText(id);
//
//                weatherTempDay5.setText(main);
//                weatherTypeDay5.setText(id);




            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            //Alert user that input is invalid and that no response is given to us.
        }

    }

    /**
     * This method is used to set up all the resources for the UI associated with displaying the
     * results from the JSON file that we grabbed from https://openweathermap.org/.
     * Displaying in a 5 day forecast fashion.
     */
    public void setUpWeatherDisplays(){

        //Finding resources for weather type.
        weatherTypeDay1 = (TextView) findViewById(R.id.weatherTypeDay1);
        weatherTypeDay2 = (TextView) findViewById(R.id.weatherTypeDay2);
        weatherTypeDay3 = (TextView) findViewById(R.id.weatherTypeDay3);
        weatherTypeDay4 = (TextView) findViewById(R.id.weatherTypeDay4);
        weatherTypeDay5 = (TextView) findViewById(R.id.weatherTypeDay5);

        //Finding resources for weather temperature.
        weatherTempDay1 = (TextView) findViewById(R.id.weatherTempDay1);
        weatherTempDay2 = (TextView) findViewById(R.id.weatherTempDay2);
        weatherTempDay3 = (TextView) findViewById(R.id.weatherTempDay3);
        weatherTempDay4 = (TextView) findViewById(R.id.weatherTempDay4);
        weatherTempDay5 = (TextView) findViewById(R.id.weatherTempDay5);



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
