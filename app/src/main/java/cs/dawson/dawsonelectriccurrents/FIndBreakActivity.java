package cs.dawson.dawsonelectriccurrents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FIndBreakActivity extends MenuActivity {
    private Spinner daySpinner;
    private Spinner timeSpinner;
    private ArrayList<String> listDay;
    private ArrayList<String> listTime;
    private Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_break);

        /*daySpinner = (Spinner) findViewById(R.id.daySpinner);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        searchBtn = (Button) findViewById(R.id.searchCourse);*/

        listDay = getDays();
        ArrayAdapter<String> daySpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_day_findfriends, listDay);
        daySpinner.setAdapter(daySpinnerAdapter);

        listTime = getTime();
        ArrayAdapter<String> timeSpinnerAdapter = new ArrayAdapter<String>(this, R.layout.spinner_day_findfriends, listTime);
        timeSpinner.setAdapter(timeSpinnerAdapter);

        searchBtn.setOnClickListener(showFriendCourses);
    }

    private ArrayList<String> getDays() {
        ArrayList<String> days = new ArrayList<>();
        days.add("Monday");
        days.add("Tuesday");
        days.add("Wednesday");
        days.add("Thursday");
        days.add("Friday");
        return days;
    }

    private ArrayList<String> getTime() {
        ArrayList<String> timeList = new ArrayList<>();
        int time = 1000;
        while (time < 1700) {
            timeList.add(Integer.toString(time));
            time += 30;
            timeList.add(Integer.toString(time));
            time += 70;
        }
        timeList.add(Integer.toString(time));
        return timeList;
    }

    private AdapterView.OnClickListener showFriendCourses = new AdapterView.OnClickListener() {
        @Override
        public void onClick(View view) {
            int indexDay = daySpinner.getSelectedItemPosition();
            String daySelected = listDay.get(indexDay);

            int indexTime = timeSpinner.getSelectedItemPosition();
            String timeSelected = listTime.get(indexTime);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}

/*
    LAYOUT USED:

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context="cs.dawson.dawsonelectriccurrents.FindFriendCourseActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">
        <Spinner
            android:id="@+id/daySpinner"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="0.5">
        </Spinner>

        <Spinner
            android:id="@+id/timeSpinner"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_weight="0.5">
        </Spinner>
    </LinearLayout>

    <Button
        android:id="@+id/searchCourse"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="@string/searchFriendCourse" />

</LinearLayout>
*/
