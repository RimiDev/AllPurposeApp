package cs.dawson.dawsonelectriccurrents;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maximelacasse on 2017-11-22.
 */


public class FiveDayForecastActivity extends MenuActivity {

    String WEATHER_API_KEY = "..";
    TextView JSONresponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiveday_forecast);


        Button btnHit = (Button) findViewById(R.id.btnHit);
        JSONresponse = (TextView) findViewById(R.id.JSONresponse);

        btnHit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadJSONTask jsonTask = new ReadJSONTask();
                jsonTask.execute("http://puppygifs.tumblr.com/api/read/json");

                d00d22f8b4783f4e387c4cbad258470f
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


    public class ReadJSONTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String response = null;
            String adres = url[0];

            URL url_HTTPRequest = null;
            try {
                url_HTTPRequest = new URL(adres);
                response = String.valueOf(url_HTTPRequest);

            } catch (MalformedURLException e) {
                Log.e("URL ERROR", "problem z url");
            } catch (Exception e) {
                Log.e("URL ERROR", "problem z transferem");
            }

            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("mylog", "result= " + result);
        }


    }

    public void httpJSONreceive() throws MalformedURLException {
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
