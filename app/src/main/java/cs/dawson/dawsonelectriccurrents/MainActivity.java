package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.util.Log;

import cs.dawson.dawsonelectriccurrents.cancelled.CancelledActivity;
import cs.dawson.dawsonelectriccurrents.database.FriendFinderDBHelper;

import cs.dawson.dawsonelectriccurrents.notes.NotesActivity;

public class MainActivity extends MenuActivity
{
    private final static String TAG = MainActivity.class.getName();
    private FriendFinderDBHelper database;
    private final String USERS_PREFS = "user";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new FriendFinderDBHelper(this);
        database.getWritableDatabase();

        SharedPreferences prefs = getSharedPreferences(USERS_PREFS, MODE_PRIVATE);
        String fullName = prefs.getString("firstName", "") + " " + prefs.getString("lastName", "");
        Log.i(TAG, "Full name is: " + fullName);

        if (fullName == null || fullName.trim().equals("")){
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
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

    public void startAcademicCalendar(View view) {
        Intent intent = new Intent(this,AcademicCalendarActivity.class);
        startActivity(intent);
    }

    public void startFindFriends(View view) {
        Intent intent = new Intent(this,FindFriendActivity.class);
        startActivity(intent);
    }

    public void startFindFreeFriends(View view) {
        Intent intent = new Intent(this,FindFreeFriends.class);
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

    @Override
    public void onStart() {
        super.onStart();



    }
}
