package cs.dawson.dawsonelectriccurrents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class AcademicCalendarActivity extends AppCompatActivity {

    private static final String TAG = AcademicCalendarActivity.class.getName();
    private Button load;
    private EditText yearInput;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_calendar);
        loadInitialCalendar();
        yearInput = (EditText) findViewById(R.id.yearInput);
        rg = (RadioGroup) findViewById(R.id.radioGroup);
        load = (Button) findViewById(R.id.loadBtn);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAllInput()) {
                    sendDataToFragment();
                } else {
                    errorMessage();
                }
            }
        });
    }

    /**
     * Loads the initial webview with the current semester
     */
    public void loadInitialCalendar() {
        CalendarWVFragment fragment = new CalendarWVFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.academicCalendarFragment, fragment, fragment.getTag()).commit();
    }

    /**
     * Sends the data to the fragment
     */
    public void sendDataToFragment() {
        String semester = getSemesterValue();
        if (validateAllInput()) {
            Bundle bundle = new Bundle();
            bundle.putString("year", yearInput.getText().toString());
            bundle.putString("semester", semester);
            CalendarWVFragment fragment = new CalendarWVFragment();
            fragment.setArguments(bundle);
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.academicCalendarFragment, fragment, fragment.getTag()).commit();
        }
    }

    /**
     * Checks if all input was input
     *
     * @return
     */
    public boolean validateAllInput() {
        String year = ((EditText) findViewById(R.id.yearInput)).getText().toString();
        Log.i(TAG, "Year: " + year);

        if (rg.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, R.string.noInput,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (!validateYear(year)) {
            return false;
        }

        return true;
    }

    /**
     * Validates the year input from the user
     *
     * @param year
     * @return
     */
    public boolean validateYear(String year) {
        try {
            int yearInt = Integer.parseInt(year);
            Log.i(TAG, "Year input: " + yearInt);
            if (yearInt < 1995 || yearInt > 2025)
                return false;
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalidYear,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    /**
     * Returns the semester
     *
     * @return
     */
    public String getSemesterValue() {
        String semester = "";
        int id = rg.getCheckedRadioButtonId();
        Log.i(TAG, "RadioGroup ID: " + id);
        Log.i(TAG, "Fall ID: " + R.id.fallRadioBtn);
        Log.i(TAG, "Winter ID: " + R.id.winterRadioBtn);

        if (id == R.id.fallRadioBtn)
            semester = "fall";
        else if (id == R.id.winterRadioBtn)
            semester = "winter";

        return semester;
    }

    /**
     * Pops a toast message to validate the year
     */
    public void errorMessage() {
        Toast.makeText(this, R.string.invalidYear,
                Toast.LENGTH_LONG).show();

    }
}
