package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This fragment takes care of displaying the teachers information
 * @author  Kevin
 * @version 1.0
 */

public class TeacherContactFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_contact, container, false);
    }
}
