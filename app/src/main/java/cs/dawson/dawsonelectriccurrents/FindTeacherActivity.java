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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

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
    protected FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_teacher);

        mDatabase = FirebaseDatabase.getInstance();
        Log.i(TAG, "Database: " + mDatabase);
        search = (Button)findViewById(R.id.searchTeacherBtn);
        firstNameInput = (EditText)findViewById(R.id.firstNameTeacher);
        lastNameInput = (EditText) findViewById(R.id.lastNameTeacher);
        rg = (RadioGroup)findViewById(R.id.radioGroupTeacher);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChooseTeacher();
            }
        });

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
    public void searchTeacher() {

    }

    /**
     * Opens the choose teacher fragment
     */
    public void openChooseTeacher() {
        Intent intent = new Intent(this, ChooseTeacherActivity.class);
        startActivity(intent);
    }
}
