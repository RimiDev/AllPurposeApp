package cs.dawson.dawsonelectriccurrents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

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
    private String fullName;
    private ArrayList<String> allTeachersFullName;
    private ArrayList<Teacher> teachers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teacher);
        // Loads the initial fragment
        loadInitialFragment();
        mDatabase = FirebaseDatabase.getInstance();

        getAllTeachers();

        // Get the bundle
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selection = extras.getString("selection");
            firstName = extras.getString("firstname");
            lastName = extras.getString("lastname");
        }

        fullName = firstName + " " + lastName;
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
        final ListView lv = (ListView)findViewById(R.id.listViewTeachers);
        Query query = mDatabase.getReference();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allTeachersFullName = new ArrayList<String>();
                teachers = new ArrayList<Teacher>();
                Log.i(TAG, "Data Snap - Getting all teachers");
                Log.i(TAG, "Data Snap Shot count - " + dataSnapshot.getChildrenCount());

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();

                while(iterator.hasNext()) {
                    DataSnapshot ds = iterator.next();

                    String teacherFullName = (String)ds.child("full_name").getValue();
                    // Check if office and local are in the database, because some teachers dont have
                    String office = (String)ds.child("office").getValue();
                    if (office == null) {
                        office = "";
                    } else {
                        office = (String)ds.child("office").getValue();
                    }
                    String local = (String)ds.child("local").getValue();
                    if (local == null) {
                        local = "";
                    } else {
                        local = (String)ds.child("local").getValue();
                    }

                    // Check for similar names
                    if (selection.equals("like")) {
                        // Check if first name contains the users search
                        if (!firstName.equals("") && lastName.equals("")) {
                            String fn = teacherFullName.substring(0, teacherFullName.indexOf(" "));
                            if (fn.toLowerCase().trim().contains(firstName.toLowerCase().trim())) {
                                allTeachersFullName.add(teacherFullName);
                                Teacher t = new Teacher((String)ds.child("first_name").getValue(), (String)ds.child("last_name").getValue(),
                                        (String)ds.child("full_name").getValue(), (String)ds.child("email").getValue(),
                                        office, local, (String)ds.child("departments").child("0").getValue(), (String)ds.child("positions").child("0").getValue(),
                                        (String)ds.child("sectors").child("0").getValue());
                                teachers.add(t);
                            }
                        // Check if the last name contains the user search
                        } else if (firstName.equals("") && !lastName.equals("")) {
                            String ln = teacherFullName.substring(teacherFullName.indexOf(" ") + 1);
                            if (ln.toLowerCase().trim().contains(lastName.toLowerCase().trim())) {
                                allTeachersFullName.add(teacherFullName);
                                Teacher t = new Teacher((String)ds.child("first_name").getValue(), (String)ds.child("last_name").getValue(),
                                        (String)ds.child("full_name").getValue(), (String)ds.child("email").getValue(),
                                        office, local, (String)ds.child("departments").child("0").getValue(), (String)ds.child("positions").child("0").getValue(),
                                        (String)ds.child("sectors").child("0").getValue());
                                teachers.add(t);
                            }
                        // Both fields were set
                        } else if (!firstName.equals("") && !lastName.equals("")) {
                            if (teacherFullName.toLowerCase().trim().contains(fullName.toLowerCase().trim())) {
                                allTeachersFullName.add(teacherFullName);
                                Teacher t = new Teacher((String)ds.child("first_name").getValue(), (String)ds.child("last_name").getValue(),
                                        (String)ds.child("full_name").getValue(), (String)ds.child("email").getValue(),
                                        office, local, (String)ds.child("departments").child("0").getValue(), (String)ds.child("positions").child("0").getValue(),
                                        (String)ds.child("sectors").child("0").getValue());
                                teachers.add(t);
                            }
                        }
                    // Check for exact names
                    } else if (selection.equals("exact")) {
                        // Check if first name contains the users search
                        if (!firstName.equals("") && lastName.equals("")) {
                            String fn = teacherFullName.substring(0, teacherFullName.indexOf(" "));
                            if (fn.toLowerCase().trim().equals(firstName.toLowerCase().trim())) {
                                allTeachersFullName.add(teacherFullName);
                                Teacher t = new Teacher((String)ds.child("first_name").getValue(), (String)ds.child("last_name").getValue(),
                                        (String)ds.child("full_name").getValue(), (String)ds.child("email").getValue(),
                                        office, local, (String)ds.child("departments").child("0").getValue(), (String)ds.child("positions").child("0").getValue(),
                                        (String)ds.child("sectors").child("0").getValue());
                                teachers.add(t);
                            }
                        // Check if the last name contains the user search
                        } else if (firstName.equals("") && !lastName.equals("")) {
                            String ln = teacherFullName.substring(teacherFullName.indexOf(" ") + 1);
                            if (ln.toLowerCase().trim().equals(lastName.toLowerCase().trim())) {
                                allTeachersFullName.add(teacherFullName);
                                Teacher t = new Teacher((String)ds.child("first_name").getValue(), (String)ds.child("last_name").getValue(),
                                        (String)ds.child("full_name").getValue(), (String)ds.child("email").getValue(),
                                        office, local, (String)ds.child("departments").child("0").getValue(), (String)ds.child("positions").child("0").getValue(),
                                        (String)ds.child("sectors").child("0").getValue());
                                teachers.add(t);
                            }
                            // Both fields were set
                        } else if (!firstName.equals("") && !lastName.equals("")) {
                            if (teacherFullName.toLowerCase().trim().equals(fullName.toLowerCase().trim())) {
                                allTeachersFullName.add(teacherFullName);
                                Teacher t = new Teacher((String)ds.child("first_name").getValue(), (String)ds.child("last_name").getValue(),
                                        (String)ds.child("full_name").getValue(), (String)ds.child("email").getValue(),
                                        office, local, (String)ds.child("departments").child("0").getValue(), (String)ds.child("positions").child("0").getValue(),
                                        (String)ds.child("sectors").child("0").getValue());
                                teachers.add(t);

                            }
                        }
                    } // End if else
                } // End while iterator

                // Start fragment if results == 1
                if (teachers.size() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("fullname", teachers.get(0).getFullName());
                    bundle.putString("email", teachers.get(0).getEmail());
                    bundle.putString("office", teachers.get(0).getOffice());
                    bundle.putString("local", teachers.get(0).getLocal());
                    bundle.putString("position", teachers.get(0).getPosition());
                    bundle.putString("department", teachers.get(0).getDepartment());
                    bundle.putString("sector", teachers.get(0).getSector());
                    TeacherContactFragment fragment = new TeacherContactFragment();
                    fragment.setArguments(bundle);
                    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
                } else {
                    // Set the adapter to the list view
                    lv.setAdapter(new TeacherAdapter(currentActivity, allTeachersFullName, dataSnapshot, selection, firstName, lastName));
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
}
