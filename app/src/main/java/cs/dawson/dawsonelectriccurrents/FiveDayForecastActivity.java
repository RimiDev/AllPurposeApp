package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.text.SimpleDateFormat;
import java.util.Date;
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


                /**
                 * Checking and pushing condiitons that will grab only the next weather
                 * information in a 3 hour range depending on our current time.
                 * 1. Create a SimpleDateFormat to create the format that we need and get the
                 * current time which grabs the hour.
                 * 2. For loop that will iterate through checking if current time is LESS
                 * than the current time's hour. The for loop will increase by 3 every iteration
                 * since the openWeatherChannel sends out new information every 3 hours.
                 * This will ensure that we are grabbing the next future weather information.
                 * 3. In the for loop for actual grabbing of the information, make sure to
                 * create a condition that it will only grab information from the sections that
                 * have the time that we established in this for loop.
                 */

                //1. Creating a SimpleDateFormat to format the current date/time with ease.
                SimpleDateFormat sdf = new SimpleDateFormat("HH");
                String currentHourTime = sdf.format(new Date()); //Output: 01 || 11 -> weather: 01 || 11

                //2. For loop to grab the correct time that we want to have for our user.
                String weatherTimeWeNeedToGrab = "";
                for (int i = 0; i <= 24; i += 3){
                    if (i <= Integer.valueOf(currentHourTime)){
                        //Not in the correct range.
                        continue;
                    } else {
                        //Correct range.
                        if (i <= 9){
                            weatherTimeWeNeedToGrab = String.valueOf("0" + i); //Get the number out of the loop.

                        } else {
                            weatherTimeWeNeedToGrab = String.valueOf(i); //Get the number out of the loop.
                        }
                        break;
                    }
                }

                //3. Grabbing only information with regards to the next 3-hour time range condition.

                String weatherTime = jsonItems.getJSONObject(1).getString("dt_txt");

                String weatherDay = weatherTime.substring(8,10); //Grabbing the day.

                String weatherHour = weatherTime.substring(11,13); //Grabbing the hour.

                logIt("dt_text: " + weatherTime);
                logIt("dt_text: " + weatherDay);
                logIt("dt_text: " + weatherHour);


                for (int i = 0; i < 5; i++) {
                    //Grabbing all the individual items associated to the weather.


                    //Grabbing the main branch and all it's components.
                    String mainTemp = jsonItems.getJSONObject(i).getJSONObject("main").getString("temp");
                    String mainMinTemp= jsonItems.getJSONObject(i).getJSONObject("main").getString("temp_min");
                    String mainMaxTemp = jsonItems.getJSONObject(i).getJSONObject("main").getString("temp_max");
                    String mainPressure = jsonItems.getJSONObject(i).getJSONObject("main").getString("pressure");
                    String mainSeaLevel = jsonItems.getJSONObject(i).getJSONObject("main").getString("sea_level");
                    String mainGroundLevel = jsonItems.getJSONObject(i).getJSONObject("main").getString("grnd_level");
                    String mainHuminity = jsonItems.getJSONObject(i).getJSONObject("main").getString("grnd_level");
                    String mainTempKf = jsonItems.getJSONObject(i).getJSONObject("main").getString("temp_kf");

                    //Grabbing the weather branch and all it's components.
                    String weatherId = jsonItems.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("id");
                    String weatherMain = jsonItems.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
                    String weatherDesciption = jsonItems.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                    String weatherIcon = jsonItems.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");


                    //Grabbing the clouds.
                    String cloudsAll = jsonItems.getJSONObject(i).getJSONObject("clouds").getString("all");

                    //Grabbing the winds.
                    String windSpeed = jsonItems.getJSONObject(i).getJSONObject("wind").getString("speed");
                    String windDeg = jsonItems.getJSONObject(i).getJSONObject("wind").getString("deg");

                    logIt("clouds: " + cloudsAll);
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
