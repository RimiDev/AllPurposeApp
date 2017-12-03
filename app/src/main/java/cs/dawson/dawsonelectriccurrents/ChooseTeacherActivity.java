package cs.dawson.dawsonelectriccurrents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import cs.dawson.dawsonelectriccurrents.adapters.TeacherAdapter;
import cs.dawson.dawsonelectriccurrents.beans.Teacher;

public class ChooseTeacherActivity extends AppCompatActivity {

    private static final String TAG = ChooseTeacherActivity.class.getName();
    protected FirebaseDatabase mDatabase;
    private String selection;
    private String firstName;
    private String lastName;
    private ArrayList<Teacher> allTeachers = new ArrayList<Teacher>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teacher);
        // Loads the initial fragment
        loadInitialFragment();
        mDatabase = FirebaseDatabase.getInstance();

        // Get the bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selection = extras.getString("selection");
            firstName = extras.getString("firstname");
            lastName = extras.getString("lastname");
        }
    }

    /**
     * Opens the teacher information fragment
     */
    private void openTeacherInformation() {
        TeacherContactFragment fragment = new TeacherContactFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }

    /**
     * Opens the teacher information fragment
     */
    private void loadInitialFragment() {
        InitialFragment fragment = new InitialFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }

    /**
     * Gets all the teachers
     * @return
     */
    private void getAllTeachers() {
        final ChooseTeacherActivity currentActivity = this;
        Query query = mDatabase.getReference();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i(TAG, "Data Snap - Getting all teachers");
                Log.i(TAG, "Data Snap Shot count - " + dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext()) {
                    DataSnapshot ds = iterator.next();
                    Teacher t = new Teacher();
                    /*Log.i(TAG, "Saving full name: " + ds.child("full_name").getValue());
                    Log.i(TAG, "Saving first name: " + ds.child("first_name").getValue());
                    Log.i(TAG, "Saving last name: " + ds.child("last_name").getValue());
                    Log.i(TAG, "Saving email: " + ds.child("email").getValue());
                    Log.i(TAG, "Saving office: " + ds.child("office").getValue());
                    Log.i(TAG, "Saving department: " + ds.child("departments").child("0").getValue());
                    Log.i(TAG, "Saving position: " + ds.child("positions").child("0").getValue());
                    Log.i(TAG, "Saving sectors: " + ds.child("sectors").child("0").getValue());*/

                    t.setFirstName((String)ds.child("first_name").getValue());
                    t.setLastName((String)ds.child("last_name").getValue());
                    t.setFullName((String)ds.child("full_name").getValue());
                    t.setEmail((String)ds.child("email").getValue());
                    t.setOffice((String)ds.child("office").getValue());
                    t.setLocal((String)ds.child("local").getValue());
                    t.setDepartment((String)ds.child("departments").child("0").getValue());
                    t.setPosition((String)ds.child("positions").child("0").getValue());
                    t.setSector((String)ds.child("sectors").child("0").getValue());

                    //Log.i(TAG, "Teacher obj: " + t.toString());
                    allTeachers.add(t);
                    //Log.i(TAG, "Teacher size: " + allTeachers.size());

                    //test
                    testArrayTeacher(allTeachers);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled called - Unexpected Error ");
                Log.e(TAG, "Code : " + databaseError.getCode()
                        + " - Details : " + databaseError.getDetails()
                        + " - Message : " + databaseError.getMessage());
            }
        });
    }

    private void testArrayTeacher(ArrayList<Teacher> list) {
        final ChooseTeacherActivity currentActivity = this;
        final ListView lv = (ListView)findViewById(R.id.listViewTeachers);
        if (list.size() == 0) {
            ((TextView)findViewById(R.id.test4)).setText("FUCKING BULLSHIT");
        } else {
            ((TextView)findViewById(R.id.test4)).setText("SIZE IS FUCKING " + list.size());
            lv.setAdapter(new TeacherAdapter(currentActivity, R.id.listViewTeachers, list));
        }
    }
}
