package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FindFriendActivity extends MenuActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);

        /*HttpURLConnection httpConnection = null;
        try
        {
            URL url = new URL("http://dawson.app/api/api/allfriends?email=alessandromciotola@gmail.com&password=123456");
            httpConnection = (HttpURLConnection) url.openConnection();
            InputStream is = httpConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int data = isr.read();
            while(data != -1)
            {
                char current = (char)data;
                data = isr.read();
                Log.d("ONCREATE", "onCreate: " + current);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            httpConnection.disconnect();
        }*/
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
