package cs.dawson.dawsonelectriccurrents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsActivity extends MenuActivity {

    private static final String TAG = SettingsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent extras = getIntent();
        String firstName;
        String lastName;
        String email;
        String password;
        Timestamp timeStamp;

        // TODO: Get the textviews for all infos

        // TODO: Button to save

        if (extras != null) {
            firstName = extras.getExtras().getString("firstName");
            lastName = extras.getExtras().getString("lastName");
            email = extras.getExtras().getString("email");
            password = extras.getExtras().getString("password");
            timeStamp = (Timestamp)  extras.getExtras().getSerializableExtra("timeStamp");
        }

        // Sets the settings
        setSettingsTextView();
    }

    private void setSettingsTextView() {
        // TODO : set the textviews
        // firstNameTv.setText(attributed);
        // lastNameTv.setText(dateOfBirth);
        // emailTv.setText(quoteShort);
        // passwordTv.setText(quoteFull);
        // timeStampTv.setText(reference);
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Save");
        builder.setMessage("Do you want to save the settings?");
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Save the result
                // saveResult();
                SettingsActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SettingsActivity.super.onBackPressed();
            }
        });
        builder.show();
    }

}
