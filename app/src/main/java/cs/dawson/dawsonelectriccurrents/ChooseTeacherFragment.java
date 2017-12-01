package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This fragment displays a list of teachers according to the user's search.
 * @author Kevin
 * @version 1.0
 */

public class ChooseTeacherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_teacher, container, false);
    }
}
