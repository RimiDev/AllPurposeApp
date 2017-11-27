package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends MenuActivity
{
    private FirebaseAuth.AuthStateListener mAuthListener;
    private final static String TAG = MainActivity.class.getName();

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

    @Override
    public void onStart() {
        if (mFirebaseUser == null)
            // Login activity
        else
            // Display main activity
    }
}
