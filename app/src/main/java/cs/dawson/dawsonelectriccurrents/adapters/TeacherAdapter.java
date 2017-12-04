package cs.dawson.dawsonelectriccurrents.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.widget.BaseAdapter;
import android.widget.TextView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import cs.dawson.dawsonelectriccurrents.ChooseTeacherActivity;
import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.TeacherContactFragment;
import cs.dawson.dawsonelectriccurrents.beans.Teacher;

/**
 * An adapter that will load a ListView in the UI and set the appropriate onClick() listener
 *
 * @author Kevin
 * @version 1.0
 */

public class TeacherAdapter extends BaseAdapter {
    private static final String TAG = TeacherAdapter.class.getName();
    private Context context;
    private ArrayList<String> teachers;
    private DataSnapshot snapShot;
    private String selection;
    private String firstName;
    private String lastName;
    private String fullName;
    private static LayoutInflater inflater;

    /**
     * Constructor
     * @param cta
     * @param teachers
     * @param ss
     * @param sel
     * @param fn
     * @param ln
     */
    public TeacherAdapter(ChooseTeacherActivity cta, ArrayList<String> teachers, DataSnapshot ss, String sel, String fn, String ln) {
        this.context = cta;
        this.teachers = teachers;
        this.snapShot = ss;
        this.selection = sel;
        this.firstName = fn;
        this.lastName = ln;
        this.fullName = fn + " " + ln;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public String getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PlaceHolder pc = new PlaceHolder();
        View rowView = inflater.inflate(R.layout.list_teacher, null);

        pc.tName = (TextView)rowView.findViewById(R.id.listItem);

        // Display the current teacher in the UI
        pc.tName.setText(teachers.get(position));

        //Container of the list of Teachers from the search
        final List<Teacher> searchedTeachers = new ArrayList<>();

        /**
         * Setting an onClickListener on the current row view
         */
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString("fullname", searchedTeachers.get(position).getFullName());
                bundle.putString("email", searchedTeachers.get(position).getEmail());
                bundle.putString("office", searchedTeachers.get(position).getOffice());
                bundle.putString("local", searchedTeachers.get(position).getLocal());
                bundle.putString("position", searchedTeachers.get(position).getPosition());
                bundle.putString("department", searchedTeachers.get(position).getDepartment());
                bundle.putString("sector", searchedTeachers.get(position).getSector());
                TeacherContactFragment fragment = new TeacherContactFragment();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager manager = ((ChooseTeacherActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
            }
        });

        /**
         * Querying the teachers searched
         */
        final Query query = snapShot.getRef();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()) {
                    DataSnapshot teacherSnap = iterator.next();
                    String teacherFullName = (String)teacherSnap.child("full_name").getValue();
                    String office = (String)teacherSnap.child("office").getValue();
                    if (office == null) {
                        office = "";
                    } else {
                        office = (String)teacherSnap.child("office").getValue();
                    }
                    String local = (String)teacherSnap.child("local").getValue();
                    if (local == null) {
                        local = "";
                    } else {
                        local = (String)teacherSnap.child("local").getValue();
                    }

                    // Check for similar names
                    if (selection.equals("like")) {
                        // Check if first name contains the users search
                        if (!firstName.equals("") && lastName.equals("")) {
                            String fn = teacherFullName.substring(0, teacherFullName.indexOf(" "));
                            if (fn.toLowerCase().trim().contains(firstName.toLowerCase().trim())) {
                                Teacher t = new Teacher((String)teacherSnap.child("first_name").getValue(), (String)teacherSnap.child("last_name").getValue(),
                                        (String)teacherSnap.child("full_name").getValue(), (String)teacherSnap.child("email").getValue(),
                                        office, local, (String)teacherSnap.child("departments").child("0").getValue(), (String)teacherSnap.child("positions").child("0").getValue(),
                                        (String)teacherSnap.child("sectors").child("0").getValue());
                                searchedTeachers.add(t);
                                Log.i(TAG, "Added: " + t.getFullName());
                            }
                            // Check if the last name contains the user search
                        } else if (firstName.equals("") && !lastName.equals("")) {
                            String ln = teacherFullName.substring(teacherFullName.indexOf(" ") + 1);
                            if (ln.toLowerCase().trim().contains(lastName.toLowerCase().trim())) {
                                Teacher t = new Teacher((String)teacherSnap.child("first_name").getValue(), (String)teacherSnap.child("last_name").getValue(),
                                        (String)teacherSnap.child("full_name").getValue(), (String)teacherSnap.child("email").getValue(),
                                        office, local, (String)teacherSnap.child("departments").child("0").getValue(), (String)teacherSnap.child("positions").child("0").getValue(),
                                        (String)teacherSnap.child("sectors").child("0").getValue());
                                searchedTeachers.add(t);
                            }
                            // Both fields were set
                        } else if (!firstName.equals("") && !lastName.equals("")) {
                            if (teacherFullName.toLowerCase().trim().contains(fullName.toLowerCase().trim())) {
                                Teacher t = new Teacher((String)teacherSnap.child("first_name").getValue(), (String)teacherSnap.child("last_name").getValue(),
                                        (String)teacherSnap.child("full_name").getValue(), (String)teacherSnap.child("email").getValue(),
                                        office, local, (String)teacherSnap.child("departments").child("0").getValue(), (String)teacherSnap.child("positions").child("0").getValue(),
                                        (String)teacherSnap.child("sectors").child("0").getValue());
                                searchedTeachers.add(t);
                            }
                        }
                        // Check for exact names
                    } else if (selection.equals("exact")) {
                        // Check if first name contains the users search
                        if (!firstName.equals("") && lastName.equals("")) {
                            String fn = teacherFullName.substring(0, teacherFullName.indexOf(" "));
                            if (fn.toLowerCase().trim().equals(firstName.toLowerCase().trim())) {
                                Teacher t = new Teacher((String)teacherSnap.child("first_name").getValue(), (String)teacherSnap.child("last_name").getValue(),
                                        (String)teacherSnap.child("full_name").getValue(), (String)teacherSnap.child("email").getValue(),
                                        office, local, (String)teacherSnap.child("departments").child("0").getValue(), (String)teacherSnap.child("positions").child("0").getValue(),
                                        (String)teacherSnap.child("sectors").child("0").getValue());
                                searchedTeachers.add(t);
                            }
                            // Check if the last name contains the user search
                        } else if (firstName.equals("") && !lastName.equals("")) {
                            String ln = teacherFullName.substring(teacherFullName.indexOf(" ") + 1);
                            if (ln.toLowerCase().trim().equals(lastName.toLowerCase().trim())) {
                                Teacher t = new Teacher((String)teacherSnap.child("first_name").getValue(), (String)teacherSnap.child("last_name").getValue(),
                                        (String)teacherSnap.child("full_name").getValue(), (String)teacherSnap.child("email").getValue(),
                                        office, local, (String)teacherSnap.child("departments").child("0").getValue(), (String)teacherSnap.child("positions").child("0").getValue(),
                                        (String)teacherSnap.child("sectors").child("0").getValue());
                                searchedTeachers.add(t);
                            }
                            // Both fields were set
                        } else if (!firstName.equals("") && !lastName.equals("")) {
                            if (teacherFullName.toLowerCase().trim().equals(fullName.toLowerCase().trim())) {
                                Teacher t = new Teacher((String)teacherSnap.child("first_name").getValue(), (String)teacherSnap.child("last_name").getValue(),
                                        (String)teacherSnap.child("full_name").getValue(), (String)teacherSnap.child("email").getValue(),
                                        office, local, (String)teacherSnap.child("departments").child("0").getValue(), (String)teacherSnap.child("positions").child("0").getValue(),
                                        (String)teacherSnap.child("sectors").child("0").getValue());
                                searchedTeachers.add(t);
                            }
                        }
                    } // End if else
                } // End while iterator
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled called - Unexpected Error ");
                Log.e(TAG, "Code : " + databaseError.getCode()
                        + " - Details : " + databaseError.getDetails()
                        + " - Message : " + databaseError.getMessage());
            }
        });

        return rowView;
    }

    /**
     * This class holds the View objects for 1 row
     */
    class PlaceHolder
    {
        TextView tName;
    }
}

