package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class CalendarWVFragment extends Fragment {

    private static final String TAG = CalendarWVFragment.class.getName();
    private String url;

    private static final String YEAR = "year";
    private static final String SEMESTER = "semester";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar_web_view, container, false);
        WebView wv = (WebView)view.findViewById(R.id.academicCalendarWv);
        if (this.getArguments() == null) {
            url = "https://www.dawsoncollege.qc.ca/registrar/fall-2017-day-division/";
        } else {
            String year = this.getArguments().getString(YEAR).toString();
            String semester = this.getArguments().getString(SEMESTER).toString();
            url = "https://www.dawsoncollege.qc.ca/registrar/" + semester + "-" + year + "-day-division/";
        }
        wv.loadUrl(url);

        return view;
    }

}
