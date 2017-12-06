package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FindFriendCourseActivity extends MenuActivity
{
    private boolean isAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend_course);

        String email = getIntent().getExtras().getString("friendEmail");

        TextView friendAvailable = (TextView) findViewById(R.id.availableView);

        if(!isAvailable)
            friendAvailable.setText(R.string.notAvailableText);
        else
        {
            friendAvailable.setText(R.string.availableText + " Course: " + " Section: ");
        }
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
