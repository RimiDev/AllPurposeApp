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
    private ArrayList<Teacher> sTeacher;
    private static LayoutInflater inflater;

    /**
     * Constructor
     * @param cta
     * @param teachers
     */
    public TeacherAdapter(ChooseTeacherActivity cta, ArrayList<String> teachers, ArrayList<Teacher> sTeacher) {
        this.context = cta;
        this.teachers = teachers;
        this.sTeacher = sTeacher;
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
                bundle.putString("fullname", sTeacher.get(position).getFullName());
                bundle.putString("email", sTeacher.get(position).getEmail());
                bundle.putString("office", sTeacher.get(position).getOffice());
                bundle.putString("local", sTeacher.get(position).getLocal());
                bundle.putString("position", sTeacher.get(position).getPosition());
                bundle.putString("department", sTeacher.get(position).getDepartment());
                bundle.putString("sector", sTeacher.get(position).getSector());
                TeacherContactFragment fragment = new TeacherContactFragment();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager manager = ((ChooseTeacherActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
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

