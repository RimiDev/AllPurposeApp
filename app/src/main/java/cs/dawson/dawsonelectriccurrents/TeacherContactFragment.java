package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * This fragment takes care of displaying the teachers information
 * @author  Kevin
 * @version 1.0
 */

public class TeacherContactFragment extends Fragment {

    private static final String TAG = TeacherContactFragment.class.getName();
    private String email;
    private String local;
    private TextView emailTv;
    private TextView localTv;
    View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_contact, container, false);

        if (savedInstanceState == null) {

        } else {
            ((TextView)view.findViewById(R.id.fullNameTv)).setText(savedInstanceState.getString("fullname"));
            ((TextView)view.findViewById(R.id.emailTeacherTv)).setText(savedInstanceState.getString("email"));
            ((TextView)view.findViewById(R.id.officeTv)).setText(savedInstanceState.getString("office"));
            ((TextView)view.findViewById(R.id.localTv)).setText(savedInstanceState.getString("local"));
            ((TextView)view.findViewById(R.id.positionTv)).setText(savedInstanceState.getString("position"));
            ((TextView)view.findViewById(R.id.departmentTv)).setText(savedInstanceState.getString("department"));
            ((TextView)view.findViewById(R.id.sectorTv)).setText(savedInstanceState.getString("sector"));
        }


        if (this.getArguments() == null) {
            ((TextView)view.findViewById(R.id.noteachersfound)).setText(R.string.noteacherfound);
            (view.findViewById(R.id.nameStatic)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.emailStatic)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.officeStatic)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.localStatic)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.positionStatic)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.departmentStatic)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.sectorStatic)).setVisibility(View.INVISIBLE);
            (view.findViewById(R.id.teacherInfoTv)).setVisibility(View.INVISIBLE);
        } else {
            ((TextView)view.findViewById(R.id.fullNameTv)).setText(this.getArguments().getString("fullname").toString());
            ((TextView)view.findViewById(R.id.emailTeacherTv)).setText(this.getArguments().getString("email").toString());
            ((TextView)view.findViewById(R.id.officeTv)).setText(this.getArguments().getString("office").toString());
            ((TextView)view.findViewById(R.id.localTv)).setText(this.getArguments().getString("local").toString());
            ((TextView)view.findViewById(R.id.positionTv)).setText(this.getArguments().getString("position").toString());
            ((TextView)view.findViewById(R.id.departmentTv)).setText(this.getArguments().getString("department").toString());
            ((TextView)view.findViewById(R.id.sectorTv)).setText(this.getArguments().getString("sector").toString());
            email = this.getArguments().getString("email");
            local = this.getArguments().getString("local");
            emailTv = (TextView)view.findViewById(R.id.emailTeacherTv);
            localTv = (TextView)view.findViewById(R.id.localTv);
            emailTv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent sendEMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                    sendEMail.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.from) + " " + getResources().getString(R.string.app_name));
                    getActivity().startActivity(Intent.createChooser(sendEMail, getResources().getString(R.string.sendemail)));
                }
            });

            localTv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent call = new Intent(Intent.ACTION_DIAL);
                    call.setData(Uri.parse("tel:" + local));
                    getActivity().startActivity(Intent.createChooser(call, getResources().getString(R.string.call)));
                }
            });
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("fullname", ((TextView)view.findViewById(R.id.fullNameTv)).getText().toString());
        outState.putString("email", ((TextView)view.findViewById(R.id.emailTeacherTv)).getText().toString());
        outState.putString("local", ((TextView)view.findViewById(R.id.localTv)).getText().toString());
        outState.putString("position", ((TextView)view.findViewById(R.id.positionTv)).getText().toString());
        outState.putString("office", ((TextView)view.findViewById(R.id.officeTv)).getText().toString());
        outState.putString("department", ((TextView)view.findViewById(R.id.departmentTv)).getText().toString());
        outState.putString("sector", ((TextView)view.findViewById(R.id.sectorTv)).getText().toString());
    }
}
