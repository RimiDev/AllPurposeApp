package cs.dawson.dawsonelectriccurrents.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import android.widget.TextView;
import android.util.Log;

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
    private static LayoutInflater inflater;

    public TeacherAdapter(ChooseTeacherActivity cta, ArrayList<String> teachers) {
        this.context = cta;
        this.teachers = teachers;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Object getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        View rowView = inflater.inflate(R.layout.list_teacher, null);

        TextView tv = (TextView) rowView.findViewById(R.id.listItem);

        // Display the current teacher in the UI
        tv.setText(teachers.get(position));

        // Container of the list of teachers
        final List<Teacher> teacherList = new ArrayList<>();

        /**
         * Setting an onClickListener on the current row view
         */
        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(TAG, "Teacher is " + teachers.get(position));
                Log.i(TAG, "Number of teachers in current list is: " + teacherList.size());

                Bundle bundle = new Bundle();
                //set the bundle
                //bundle.putString("year", "test");
                TeacherContactFragment fragment = new TeacherContactFragment();
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentManager manager = ((ChooseTeacherActivity)context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.academicCalendarFragment, fragment, fragment.getTag()).commit();
            }
        });

        return rowView;
    }
}

