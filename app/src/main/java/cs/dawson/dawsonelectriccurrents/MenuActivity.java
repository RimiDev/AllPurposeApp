package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.about:
                showAbout();
                return true;
            case R.id.dawson:
                showDawson();
                return true;
            case R.id.settings:
                showSettings();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showAbout()
    {
        Intent intent = new Intent(MenuActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private void showDawson()
    {

    }

    private void showSettings()
    {
        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}
