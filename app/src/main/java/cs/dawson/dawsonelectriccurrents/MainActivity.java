package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends MenuActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startClassCancelled(View view)
    {
        Intent intent = new Intent(this, CancelledActivity.class);
        startActivity(intent);
    }

    public void startFindTeacher(View view)
    {
        Intent intent = new Intent(this, FindTeacherActivity.class);
        startActivity(intent);
    }

    public void startAddCalendar(View view)
    {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }

    public void startNotes(View view)
    {
        Intent intent = new Intent(this, NotesActivity.class);
        startActivity(intent);
    }

    public void startWeather(View view)
    {
        Intent intent = new Intent(this, WeatherActivity.class);
        startActivity(intent);
    }

    public void startAbout(View view)
    {
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
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
