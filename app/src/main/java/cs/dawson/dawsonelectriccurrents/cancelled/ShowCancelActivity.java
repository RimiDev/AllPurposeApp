package cs.dawson.dawsonelectriccurrents.cancelled;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;

import cs.dawson.dawsonelectriccurrents.MenuActivity;
import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.TeacherContactFragment;
import cs.dawson.dawsonelectriccurrents.beans.CancelledClass;

public class ShowCancelActivity extends MenuActivity implements Serializable{
    private CancelledClass cancelledClass;
    private TextView title;
    private TextView course;
    private TextView teacher;
    private TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cancel);

        Intent intent = getIntent();
        cancelledClass = (CancelledClass) intent.getSerializableExtra("ClassCancelled");

        title = (TextView) findViewById(R.id.tvTextTitle);
        course = (TextView) findViewById(R.id.tvTextCourse);
        teacher = (TextView) findViewById(R.id.tvTextTeacher);
        date = (TextView) findViewById(R.id.tvTextDate);

        title.setText(cancelledClass.getTitle());
        course.setText(cancelledClass.getCourse());
        teacher.setText(cancelledClass.getTeacher());
        date.setText(cancelledClass.getDateTimeCancelled());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    public void getTeacherInformation(View view){

        Bundle bundle = new Bundle();
        bundle.putString("fullname", cancelledClass.getTeacher());
        TeacherContactFragment fragment = new TeacherContactFragment();
        fragment.setArguments(bundle);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }
}
