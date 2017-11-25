package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;

public class WeatherActivity extends MenuActivity {

    EditText cityinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        cityinput = findViewById(R.id.cityinput);

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

    public void startUvIndex(View view)
    {

        Intent intent = new Intent(this, UvIndexActivity.class);
        intent.putExtra("city", cityinput.getText());
        startActivity(intent);
    }


    public void startFiveDayForecast(View view)
    {
        Intent intent = new Intent(this, FiveDayForecastActivity.class);
        intent.putExtra("city", cityinput.getText());
        startActivity(intent);
    }

    /**
     * Method to easily log to logcat
     *
     * @param msg to be printed to logcat
     */
    public static void logIt(String msg) {
        final String TAG = "-------------WEATHER: ";
        Log.d(TAG, msg);
    }


}
