package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    // For testing purposes, will delete after
    private Button one;
    private Button two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_teacher);
        // Loads the blank fragment
        loadInitialFragment();

        mDatabase = FirebaseDatabase.getInstance();
        search = (Button)findViewById(R.id.searchTeacherBtn);
        firstNameInput = (EditText)findViewById(R.id.firstNameTeacher);
        lastNameInput = (EditText) findViewById(R.id.lastNameTeacher);
        rg = (RadioGroup)findViewById(R.id.radioGroupTeacher);

        // ---------------------------------------------------------- Testing
        one = (Button)findViewById(R.id.f1);
        two = (Button)findViewById(R.id.f2);

        one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChooseTeacher();
            }
        });

        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTeacherInformation();
            }
        });

        // ---------------------------------------------------------- Testing
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
        ChooseTeacherFragment fragment = new ChooseTeacherFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }

    /**
     * Opens the teacher information fragment
     */
    public void openTeacherInformation() {
        TeacherContactFragment fragment = new TeacherContactFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }

    /**
     * Loads the initial fragment for the find teacher
     */
    public void loadInitialFragment() {
        BlankFragment fragment = new BlankFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }
}
