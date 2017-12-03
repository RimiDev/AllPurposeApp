package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * This fragment takes care of displaying the teachers information
 * @author  Kevin
 * @version 1.0
 */

public class TeacherContactFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teacher_contact, container, false);
        if (this.getArguments() == null) {
            ((TextView)view.findViewById(R.id.noteachersfound)).setText(R.string.noteacherfound);
        } else {
            ((TextView)view.findViewById(R.id.fullNameTv)).setText(this.getArguments().getString("fullname").toString());
            ((TextView)view.findViewById(R.id.emailTeacherTv)).setText(this.getArguments().getString("email").toString());
            ((TextView)view.findViewById(R.id.officeTv)).setText(this.getArguments().getString("office").toString());
            ((TextView)view.findViewById(R.id.localTv)).setText(this.getArguments().getString("local").toString());
            ((TextView)view.findViewById(R.id.positionTv)).setText(this.getArguments().getString("position").toString());
            ((TextView)view.findViewById(R.id.departmentTv)).setText(this.getArguments().getString("department").toString());
            ((TextView)view.findViewById(R.id.sectorTv)).setText(this.getArguments().getString("sector").toString());
        }

        return view;
    }
}
