package cs.dawson.dawsonelectriccurrents;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Timestamp;

public class SettingsActivity extends MenuActivity {

    private static final String TAG = SettingsActivity.class.getName();

    // TextViews
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView password;
    private TextView lastUpdated;

    // Strings
    private String firstNameStr;
    private String lastNameStr;
    private String emailStr;
    private String passwordStr;
    private String timeStampStr;

    // EditText
    private EditText editFirstName;
    private EditText editLastName;
    private EditText editEmail;
    private EditText editPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent extras = getIntent();
        String firstNameStr;
        String lastNameStr;
        String emailStr;
        String passwordStr;
        Timestamp timeStampStr;

        // TextView
        firstName = (TextView) findViewById(R.id.firstNameSp);
        lastName = (TextView) findViewById(R.id.lastNameSp);
        email = (TextView) findViewById(R.id.emailSp);
        password = (TextView) findViewById(R.id.passwordSp);
        lastUpdated = (TextView) findViewById(R.id.lastUpdatedSp);

        editFirstName = (EditText) findViewById(R.id.editFirstName);
        editLastName = (EditText) findViewById(R.id.editLastName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);

        Button saveBtn = (Button) findViewById(R.id.saveButton);

        if (extras != null) {
            firstNameStr = extras.getExtras().getString("firstName");
            lastNameStr = extras.getExtras().getString("lastName");
            emailStr = extras.getExtras().getString("email");
            passwordStr = extras.getExtras().getString("password");
            timeStampStr = (Timestamp)  extras.getExtras().getSerializable("timeStamp");
        }

        // Sets the settings
        setSettingsTextView();
    }

    private void setSettingsTextView() {
        firstName.setText(firstNameStr);
        lastName.setText(lastNameStr);
        email.setText(emailStr);
        password.setText(passwordStr);
        lastUpdated.setText(timeStampStr);
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
                save();
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

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();

        e.putString("firstname", firstName.getText().toString());
        e.putString("lastname", lastName.getText().toString());
        e.putString("email", email.getText().toString());
        e.putString("password", password.getText().toString());
        e.putString("lastupdate", lastUpdated.getText().toString());

        e.commit();
    }

    public void save() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor e = prefs.edit();

        e.putString("firstnameedit", editFirstName.getText().toString());
        e.putString("lastnameedit", editLastName.getText().toString());
        e.putString("emailedit", editEmail.getText().toString());
        e.putString("passwordedit", editPassword.getText().toString());

        e.commit();
    }

    /**
     * Saves the settings when the user clicks the button save
     * @param v
     */
    public void saveSettings(View v) {
        save();
    }



}
