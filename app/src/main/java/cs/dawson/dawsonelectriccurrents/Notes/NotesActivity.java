package cs.dawson.dawsonelectriccurrents.Notes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import cs.dawson.dawsonelectriccurrents.Database.DBHelper;
import cs.dawson.dawsonelectriccurrents.MenuActivity;
import cs.dawson.dawsonelectriccurrents.R;

/**
 * Notes activity which will display a list of notes from the database which are populated
 * by the user when they enter any information into the text field and press the "Add Note" button.
 *
 * @author Alessandro Ciotola
 * @author Hannah Ly
 * @author Kevin Bui
 * @author Maxime Lacasse
 * @version 2017/11/25
 *
 */
public class NotesActivity extends MenuActivity
{
    private static DBHelper dbHelper ;
    private SimpleCursorAdapter sCurAdapter;
    private Cursor cursor;

    /**
     * Method which is called when the activity is first opened.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        dbHelper = DBHelper.getDBHelper(this);

       showNotes();
    }

    /**
     * Method which calls the super method on onCreateOptionsMenu to display the menu. Required
     * so code to show the menu will not have to be repeated for each activity.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    /**
     * Method which calls the super method on onOptionsItemSelected to add functionality to the menu
     * buttons without having to repeat the code for each activity.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    /**
     * Method which is called when the add button is pressed. If information is added to the
     * text field, then add that data to the database and refresh the view.
     *
     * @param view
     */
    public void insertNote(View view)
    {
        EditText et = (EditText) findViewById(R.id.noteText);
        String note  = et.getText().toString();

        if(!note.equals("") && note.length() > 10)
        {
            et.setText("");
            dbHelper.insertNote(note.substring(0, 8), note);
        }
        else
            et.setText(R.string.errorNotesText);
        refreshView();
    }

    /**
     * After the Activity has begun, display the list of notes from the database.
     *
     */
    private void showNotes()
    {
        ListView lv = (ListView) findViewById(R.id.noteList);
        cursor = dbHelper.getNotes();
        String[] from = { DBHelper.COL_SHORTNOTE };
        int[] to = { R.id.noteView };

        sCurAdapter = new SimpleCursorAdapter(this, R.layout.note_row, cursor, from, to, 0);
        lv.setAdapter(sCurAdapter);
        lv.setOnItemClickListener(displayNote);
    }

    /**
     * Event Listener which is called when one of the items in the List view has been pressed.
     * Will start the ItemNoteActivity and pass the note information to the intent.
     *
     */
    public AdapterView.OnItemClickListener displayNote = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Cursor tempCursor = (Cursor) parent.getItemAtPosition(position);
            String note = tempCursor.getString(2);

            Intent intent = new Intent(NotesActivity.this, ItemNoteActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
        }
    };

    /**
     * Method which will reset the view in order to display all the notes when a new note is
     * added.
     *
     */
    public void refreshView()
    {
        cursor = dbHelper.getNotes();
        sCurAdapter.changeCursor(cursor);
        sCurAdapter.notifyDataSetChanged();
    }
}
