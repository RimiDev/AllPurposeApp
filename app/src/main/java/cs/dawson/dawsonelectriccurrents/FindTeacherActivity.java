package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Activity containing all the functionality to search for teachers
 * @author Kevin
 * @version 1.0
 */
public class FindTeacherActivity extends MenuActivity {

    private static final String TAG = FindTeacherActivity.class.getName();
    public Button search;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_teacher);

        search = (Button)findViewById(R.id.searchTeacherBtn);
        firstNameInput = (EditText)findViewById(R.id.firstNameTeacher);
        lastNameInput = (EditText) findViewById(R.id.lastNameTeacher);
        rg = (RadioGroup)findViewById(R.id.radioGroupTeacher);
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
     * Searches for the teacher
     */
    public void searchTeacher(View view) {
        if (validateFields()) {
            String selection = getSelectionValue();
            String fn = firstNameInput.getText().toString();
            String ln = lastNameInput.getText().toString();
            Intent intent = new Intent(this, ChooseTeacherActivity.class);
            intent.putExtra("selection", selection);
            intent.putExtra("firstname", fn);
            intent.putExtra("lastname", ln);
            startActivity(intent);
        } else {
            errorMessage();
        }
    }

    /**
     * Validates if all required fields are filled up
     * @return
     */
    private boolean validateFields() {

        String fn = firstNameInput.getText().toString();
        String ln = lastNameInput.getText().toString();
        Log.i(TAG, "Firstname Teacher: " + fn);
        Log.i(TAG, "Lastname Teacher: " + ln);
        boolean valid = false;

        if (!fn.equals("") || !ln.equals("")) {
            valid = true;
        }

        // Checks if any radio button was selected
        if (rg.getCheckedRadioButtonId() == -1) {
            valid = false;
        }

        return valid;
    }

    /**
     * Returns the search selection
     *
     * @return
     */
    public String getSelectionValue() {
        String selection = "";
        int id = rg.getCheckedRadioButtonId();
        Log.i(TAG, "RadioGroup ID: " + id);
        Log.i(TAG, "Like ID: " + R.id.like);
        Log.i(TAG, "Exact ID: " + R.id.exact);

        if (id == R.id.like)
            selection = "like";
        else if (id == R.id.exact)
            selection = "exact";

        return selection;
    }

    /**
     * Pops a toast message to validate the year
     */
    public void errorMessage() {
        Toast.makeText(this, R.string.invalidInput,
                Toast.LENGTH_LONG).show();

    }
}
