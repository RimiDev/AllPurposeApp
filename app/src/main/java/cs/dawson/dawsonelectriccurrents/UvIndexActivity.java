package cs.dawson.dawsonelectriccurrents;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import cs.dawson.dawsonelectriccurrents.weatherrequest.GPSTracker;


/**
 * Created by maximelacasse on 2017-11-22.
 */

public class UvIndexActivity extends MenuActivity {

    String cityinput;

    LocationManager locationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_index);

//        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        double longitude = location.getLongitude();
//        double latitude = location.getLatitude();
        //dawsonweathertitle.setText(longitude + " " + latitude);

        GPSTracker locationer = new GPSTracker(this);
        logIt("Latitude: " + locationer.getLatitude());
        logIt("Longitude: " + locationer.getLongitude());

    TextView dawsonweathertitle = (TextView) findViewById(R.id.dawsonweathertitle);

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
