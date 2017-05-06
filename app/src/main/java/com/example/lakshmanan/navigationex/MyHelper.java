package com.example.lakshmanan.navigationex;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lakshmanan on 14/11/15.
 */
public class MyHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "MemoriesDB";

    public MyHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_MEMORY_TABLE = "CREATE TABLE Memories ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "moment TEXT )";

        // create books table
        db.execSQL(CREATE_MEMORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS Memories");

        // create fresh books table
        this.onCreate(db);
    }

    /* ALL CRUD Ops goes here
    * */
    // Memory table name
    private static final String TABLE_MEMORIES = "Memories";

    // Memories Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MOMENT = "moment";

    private static final String[] COLUMNS = {KEY_ID,KEY_MOMENT};

    public void makeMemory(Moment m){
        Log.d("Make a memory", m.getMoment());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_MOMENT,m.getMoment());

        db.insert(TABLE_MEMORIES, null, values);
        db.close();
    }

    public List<Moment> retrieveAllMemories(){
        List<Moment> moments = new LinkedList<Moment>();
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_MEMORIES + " ORDER BY id DESC" ;
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Moment m = null;
        if(cursor.moveToFirst()) {
            do {
                m = new Moment();
                m.setId(Integer.parseInt(cursor.getString(0)));
                m.setMoment(cursor.getString(1));
                moments.add(m);
            }while (cursor.moveToNext());
        }
        db.close();
        return moments;
    }

    public void eraseMemory(Moment m){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMORIES,KEY_ID+"=?",new String[]{ String.valueOf(m.getId()) });
        db.close();
        Log.d("EraseMemory", m.toString());
    }
}
