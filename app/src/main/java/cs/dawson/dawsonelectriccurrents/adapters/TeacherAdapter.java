package cs.dawson.dawsonelectriccurrents.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import android.widget.TextView;
import android.util.Log;

import cs.dawson.dawsonelectriccurrents.ChooseTeacherActivity;
import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.beans.Teacher;

/**
 * An adapter that will load a ListView in the UI and set the appropriate onClick() listener
 *
 * @author Kevin
 * @version 1.0
 */

public class TeacherAdapter extends ArrayAdapter<Teacher> {
    private static final String TAG = TeacherAdapter.class.getName();
    private Context context;
    private ArrayList<Teacher> teachers;
    private static LayoutInflater inflater;

    /**
     * Constructor
     * @param cta
     * @param teachers
     */
    public TeacherAdapter(ChooseTeacherActivity cta, int layout, ArrayList<Teacher> teachers) {
        super(cta, layout, teachers);
        this.context = cta;
        this.teachers = teachers;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Teacher getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        PlaceHolder pc = new PlaceHolder();
        View rowView = inflater.inflate(R.layout.list_teacher, null);

        pc.tName = (TextView)rowView.findViewById(R.id.listItem);

        // Display the current teacher in the UI
        Teacher t = teachers.get(position);
        Log.i(TAG, "Teacher full name: " + t.getFullName());
        pc.tName.setText(t.getFullName());

        /**
         * Setting an onClickListener on the current row view
         */
        /*rowView.setOnClickListener(new View.OnClickListener() {

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
        });*/

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

