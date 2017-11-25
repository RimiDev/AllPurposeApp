package cs.dawson.dawsonelectriccurrents;

import android.content.Context;
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
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maximelacasse on 2017-11-22.
 */


public class FiveDayForecastActivity extends MenuActivity {

    String WEATHER_API_KEY = "..";
    TextView JSONresponse;
    private String api = "http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=d00d22f8b4783f4e387c4cbad258470f";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiveday_forecast);


        logIt("I am here");

        Button btnHit = (Button) findViewById(R.id.btnHit);
        JSONresponse = (TextView) findViewById(R.id.JSONresponse);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAsync myAsync = new MyAsync();
                myAsync.execute();
            }
        });

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


    public class MyAsync extends AsyncTask<Void, Void, String>{

        @Override
        protected void onPreExecute() {
            logIt("onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = null;
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=d00d22f8b4783f4e387c4cbad258470f");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                result = inputStreamToString(in);
                logIt("result: " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                logIt("ResultInOnPostExecute: "+ s);
                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("contacts");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    String id = jsonArray.getJSONObject(i).getString("id");
//                    String name = jsonArray.getJSONObject(i).getString("name");
//                    String email = jsonArray.getJSONObject(i).getString("email");
//                    String mobile = jsonArray.getJSONObject(i).getJSONObject("phone").getString("mobile");

//                    JSONresponse.append(id + "\n");
//                    JSONresponse.append(name + "\n");
//                    JSONresponse.append(email + "\n");
//                    JSONresponse.append(mobile + "\n\n");

                   

                //}

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private String inputStreamToString(InputStream is) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader rd = new BufferedReader(isr);

        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer.toString();
    }






    public String httpJSONreceive() throws MalformedURLException {
        URL url = new URL("http://www.android.com/");
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            JSONresponse.setText(readStream(in));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    } // end of httpJSONreceive

    private String readStream(InputStream is) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
            return bo.toString();
        } catch (IOException e) {
            return "";
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
