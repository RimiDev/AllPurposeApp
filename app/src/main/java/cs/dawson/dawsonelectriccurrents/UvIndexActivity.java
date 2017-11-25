package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


/**
 * Created by maximelacasse on 2017-11-22.
 */

public class UvIndexActivity extends MenuActivity {

    String cityinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uv_index);

        cityinput = getIntent().getExtras().getString("city");

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
