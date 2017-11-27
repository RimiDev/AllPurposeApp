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


    //Weather details for 5 day range.
    TextView weatherDay1;
    TextView weatherDay2;
    TextView weatherDay3;
    TextView weatherDay4;
    TextView weatherDay5;


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
                 * 3. Grab the information from dt_txt to aquire the hour and day for step 4 condition.
                 * 4. In the for loop for actual grabbing of the information, make sure to
                 * create a condition that it will only grab information from the sections that
                 * have the time that we established in this for loop.
                 */

                //1. Creating a SimpleDateFormat to format the current date/time with ease.
                SimpleDateFormat sdf = new SimpleDateFormat("HHdd");
                String currentHourDay = sdf.format(new Date()); //Output: 01 || 11 -> weather: 01 || 11

                //2. For loop to grab the correct time that we want to have for our user.
                String weatherHourWeNeedToGrab = currentHourDay.substring(0,2); //Grabbing the current hour.
                String weatherDayWeNeedToGrab = currentHourDay.substring(2,4); //Grabbing current day.

                int dayCounter = 1;

                //Iterate through all the JSONObjects inside the list JSONArray.
                for (int i = 0; i < jsonItems.length(); i++) {

                    //3. Grabbing the dt_txt to see if it's the results that we want to display.
                    String weatherTimeAndDay = jsonItems.getJSONObject(i).getString("dt_txt");
                    String weatherHour = weatherTimeAndDay.substring(11,13); //Grabbing the hour.
                    String weatherDay = weatherTimeAndDay.substring(8,10); //Grabbing the day.
                    String weatherDate = weatherTimeAndDay.substring(0,10); //Grabbing the date.
                    String weatherTime = weatherTimeAndDay.substring(11,19); //Grabbing the time.

                    logIt("WeatherDayGRAB: " + weatherDayWeNeedToGrab);
                    logIt("WeatherDay: " + weatherDay);
                    logIt("WeatherHour: " + weatherHour);
                    logIt("weatherHourGRAB: " + weatherHourWeNeedToGrab);

                    //4. A condition to check to check if we are grabbing the same hour
                    //And incrementing the day counter until we hit 5 (5-day forecast).
                    if (Integer.valueOf(weatherHour) > Integer.valueOf(weatherHourWeNeedToGrab) &&
                            weatherDay.equals(weatherDayWeNeedToGrab)){
                        //The same hour and fits the day counter -> GRAB RESULTS!

                        logIt("Inside the weatherTime condition");

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
                        String weatherDescription = jsonItems.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                        String weatherIcon = jsonItems.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");

                        //Grabbing the clouds.
                        String cloudsAll = jsonItems.getJSONObject(i).getJSONObject("clouds").getString("all");

                        //Grabbing the winds.
                        String windSpeed = jsonItems.getJSONObject(i).getJSONObject("wind").getString("speed");
                        String windDeg = jsonItems.getJSONObject(i).getJSONObject("wind").getString("deg");


                        //Displaying the results
                        //CONVERT TEMPERATURE TO CELCIUS
                        switch (dayCounter){
                            case 1:
                                weatherDay1.setText(weatherDate + "\n" + weatherTime + "\n" + mainTemp + "\n" + mainMinTemp + "\n" + mainMaxTemp + "\n" + mainPressure +
                                        "\n" + mainSeaLevel + "\n" + mainGroundLevel + "\n" + mainHuminity + "\n" + mainTempKf +
                                        "\n" + weatherId + "\n" + weatherMain + "\n" + weatherDescription + "\n" +
                                        "\n" + weatherIcon + "\n" + cloudsAll + "\n" + windSpeed + "\n" + windDeg);
                            case 2:
                                weatherDay2.setText(weatherDate + "\n" + weatherTime + "\n" + mainTemp + "\n" + mainMinTemp + "\n" + mainMaxTemp + "\n" + mainPressure +
                                        "\n" + mainSeaLevel + "\n" + mainGroundLevel + "\n" + mainHuminity + "\n" + mainTempKf +
                                        "\n" + weatherId + "\n" + weatherMain + "\n" + weatherDescription + "\n" +
                                        "\n" + weatherIcon + "\n" + cloudsAll + "\n" + windSpeed + "\n" + windDeg);
                            case 3:
                                weatherDay3.setText(weatherDate + "\n" + weatherTime + "\n" + mainTemp + "\n" + mainMinTemp + "\n" + mainMaxTemp + "\n" + mainPressure +
                                        "\n" + mainSeaLevel + "\n" + mainGroundLevel + "\n" + mainHuminity + "\n" + mainTempKf +
                                        "\n" + weatherId + "\n" + weatherMain + "\n" + weatherDescription + "\n" +
                                        "\n" + weatherIcon + "\n" + cloudsAll + "\n" + windSpeed + "\n" + windDeg);
                            case 4:
                                weatherDay4.setText(weatherDate + "\n" + weatherTime + "\n" + mainTemp + "\n" + mainMinTemp + "\n" + mainMaxTemp + "\n" + mainPressure +
                                        "\n" + mainSeaLevel + "\n" + mainGroundLevel + "\n" + mainHuminity + "\n" + mainTempKf +
                                        "\n" + weatherId + "\n" + weatherMain + "\n" + weatherDescription + "\n" +
                                        "\n" + weatherIcon + "\n" + cloudsAll + "\n" + windSpeed + "\n" + windDeg);
                            case 5:
                                weatherDay5.setText(weatherDate + "\n" + weatherTime + "\n" + mainTemp + "\n" + mainMinTemp + "\n" + mainMaxTemp + "\n" + mainPressure +
                                        "\n" + mainSeaLevel + "\n" + mainGroundLevel + "\n" + mainHuminity + "\n" + mainTempKf +
                                        "\n" + weatherId + "\n" + weatherMain + "\n" + weatherDescription + "\n" +
                                        "\n" + weatherIcon + "\n" + cloudsAll + "\n" + windSpeed + "\n" + windDeg);
                        }



                        logIt("WeatherDayBeforeIncrease: " + weatherDayWeNeedToGrab);

                        //Increase the weather day counter.
                        int weatherIncrease = Integer.valueOf(weatherDay);
                        weatherIncrease++;
                        weatherDayWeNeedToGrab = String.valueOf(weatherIncrease);

                        logIt("WeatherDayAfterIncrease: " + weatherDayWeNeedToGrab);

                    } else {
                        logIt("Continue...");
                        continue;
                    }




                }

                //Displaying the results.

//                weatherTypeDay1.setText();
//
//                weatherTypeDay2.setText(id);
//
//                weatherTypeDay3.setText(id);
//
//                weatherTypeDay4.setText(id);
//
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
        weatherDay1 = (TextView) findViewById(R.id.weatherDay1);
        weatherDay2 = (TextView) findViewById(R.id.weatherDay2);
        weatherDay3 = (TextView) findViewById(R.id.weatherDay3);
        weatherDay4 = (TextView) findViewById(R.id.weatherDay4);
        weatherDay5 = (TextView) findViewById(R.id.weatherDay5);

    }

    private String get3HourRangeCondition(String currentHourDay){
        String hour = "Hour?";

        for (int i = 0; i <= 24; i += 3) {
            if (i <= Integer.valueOf(currentHourDay)) {
                //Not in the correct range.
                continue;
            } else {
                //Correct range.
                if (i <= 9) {
                    hour = String.valueOf("0" + i); //Get the number out of the loop.

                } else {
                    hour = String.valueOf(i); //Get the number out of the loop.
                }
                break;
            }
        }
        return hour;
    } // end of get3HourRangeCondition






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
