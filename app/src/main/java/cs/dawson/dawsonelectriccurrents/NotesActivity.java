package cs.dawson.dawsonelectriccurrents;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class NotesActivity extends MenuActivity
{
    private static DBHelper dbHelper;
    private SimpleCursorAdapter sCurAdapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        showNotes();
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

    private void showNotes()
    {
        ListView lv = (ListView) findViewById(R.id.noteList);
        dbHelper = DBHelper.getDBHelper(this);
        cursor = dbHelper.getNotes();
        String[] from = { DBHelper.COL_NOTE };
        int[] to = { R.id.noteView };

        sCurAdapter = new SimpleCursorAdapter(this, R.layout.note_row, cursor, from, to, 0);
        lv.setAdapter(sCurAdapter);
        lv.setOnItemClickListener(displayNote);
    }

    public AdapterView.OnItemClickListener displayNote = new AdapterView.OnItemClickListener()
    {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            Cursor tempCursor = (Cursor) parent.getItemAtPosition(position);
            String note = tempCursor.getString(0);

            Intent intent = new Intent(NotesActivity.this, ItemNoteActivity.class);
            intent.putExtra("note", note);
            startActivity(intent);
        }
    };
}
