package cs.dawson.dawsonelectriccurrents;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AboutActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void showCreatorBlurb(View view)
    {
        String name = "";
        String info = "";
        switch (view.getId())
        {
            case R.id.maxTextView:
                name = getString(R.string.maxText);
                info = getString(R.string.maxInfo);
                break;
            case R.id.aleTextView:
                name = getString(R.string.aleText);
                info = getString(R.string.aleInfo);
                break;
            case R.id.hannahTextView:
                name = getString(R.string.hannahText);
                info = getString(R.string.hannahInfo);
                break;
            case R.id.kevinTextView:
                name = getString(R.string.kevinText);
                info = getString(R.string.kevinInfo);
                break;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(info)
                .setTitle(name);

        builder.setNegativeButton(R.string.discard, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                //When the button is Pressed, dismiss the dialog.
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
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
}
