package cs.dawson.dawsonelectriccurrents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseTeacherActivity extends AppCompatActivity {

    //TESTING
    private Button test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_teacher);
        // Loads the initial fragment
        loadInitialFragment();

        test = (Button)findViewById(R.id.test);
        test.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                openTeacherInformation();
            }
        });
    }

    /**
     * Opens the teacher information fragment
     */
    public void openTeacherInformation() {
        TeacherContactFragment fragment = new TeacherContactFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }

    /**
     * Opens the teacher information fragment
     */
    public void loadInitialFragment() {
        InitialFragment fragment = new InitialFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.findTeacherFragment, fragment, fragment.getTag()).commit();
    }
}
