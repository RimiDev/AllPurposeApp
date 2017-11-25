package cs.dawson.dawsonelectriccurrents;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Alessandro Ciotola
 */

public class DBHelper extends SQLiteOpenHelper
{
    private static final String DB_NAME = "notes.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NOTES = "notes";
    public static final String COL_ID = "_id";
    public static final String COL_NOTE = "note";
    private static DBHelper dbHelper = null;

    public DBHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(DBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion);
        try
        {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        }
        catch (SQLException ex)
        {
            Log.e(DBHelper.class.getName(), "onUpgrade: " + ex.getMessage());
            throw ex;
        }
        createDatabase(db);
    }

    private void createDatabase(SQLiteDatabase db)
    {
        try
        {
            String createNoteDB = "create table " + TABLE_NOTES + "(" +
                    COL_ID + " integer primary key autoincrement, " +
                    COL_NOTE + " text not null);";
            db.execSQL(createNoteDB);
        }
        catch(SQLException ex)
        {
            Log.d(DBHelper.class.getName(), "CreateDatabase: " + ex.getMessage());
            throw ex;
        }
    }

    public static DBHelper getDBHelper(Context context)
    {
        if (dbHelper == null)
            dbHelper = new DBHelper(context.getApplicationContext());
        return dbHelper;
    }

    public Cursor getNotes()
    {
        return getReadableDatabase().query(TABLE_NOTES, null, null, null, null, null, null);
    }

    public Cursor getNote(int id)
    {
        return getReadableDatabase().query(TABLE_NOTES, null, COL_ID + "=?", new String[] { String.valueOf(id) }, null, null, null);
    }

    public long insertNote(String note)
    {
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTE, note);

        long code = getWritableDatabase().insert(TABLE_NOTES, null, cv);
        return code;
    }
}
