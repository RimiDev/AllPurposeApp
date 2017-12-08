package cs.dawson.dawsonelectriccurrents.notes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;

import cs.dawson.dawsonelectriccurrents.R;
import cs.dawson.dawsonelectriccurrents.database.DBHelper;

public class CustomNoteAdapter extends SimpleCursorAdapter
{
    private final LayoutInflater inflater;
    private int layout;
    private Context noteContext;
    private Cursor noteCursor;

    public CustomNoteAdapter(Context context, int layout, Cursor c, String[] from, int[] to)
    {
        super(context,layout,c,from,to);
        this.inflater=LayoutInflater.from(context);
        this.layout=layout;
        this.noteContext = context;
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent)
    {
        return inflater.inflate(layout, null);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        super.bindView(view, context, cursor);
        TextView tv = (TextView)view.findViewById(R.id.noteView);
        ImageButton btn = (ImageButton) view.findViewById(R.id.deleteNoteBtn);
        noteCursor = cursor;

        tv.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                String note = noteCursor.getString(2);

                Intent intent = new Intent(noteContext, ItemNoteActivity.class);
                intent.putExtra("note", note);
                noteContext.startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                int position = noteCursor.getInt(noteCursor.getColumnIndex("_id"));
                DBHelper dbHelper = DBHelper.getDBHelper(noteContext);
                dbHelper.removeNote(position);

                noteCursor = dbHelper.getNotes();
                changeCursor(noteCursor);
                notifyDataSetChanged();
            }
        });
    }
}