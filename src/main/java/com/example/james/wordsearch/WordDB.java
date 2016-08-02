package com.example.james.wordsearch;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by James on 8/1/2016.
 */
public class WordDB {

    // database constants
    public static final String DB_NAME = "word.db";
    public static final int DB_VERSION = 1;

    // word table constants
    public static final String WORD_TABLE = "word";

    public static final String WORD_ID = "_id";
    public static final int WORD_ID_COL = 0;

    public static final String WORD_NAME = "word_name";
    public static final int WORD_NAME_COL = 1;

    public static final String WORD_DEFINITION = "word_definition";
    public static final int WORD_DEFINITION_COL = 2;

    public static final String WORD_OTHER = "word_other";
    public static final int WORD_OTHER_COL = 3;

    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public WordDB(Context context){
        dbHelper = new DBHelper (context, DB_NAME, null, DB_VERSION);
    }

    // CREATE and DROP table statements

    public static final String CREATE_WORD_TABLE =
            "CREATE TABLE " + WORD_TABLE + " (" +
                    WORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    WORD_NAME + " TEXT NOT NULL, " +
                    WORD_DEFINITION + " TEXT, " +
                    WORD_OTHER + " TEXT);";

    public static final String DROP_WORD_TABLE =
            "DROP TABLE IF EXISTS " + WORD_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory, int version){
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            db.execSQL(CREATE_WORD_TABLE);

            // insert test data
            db.execSQL("INSERT INTO word VALUES (0, 'thingy', 'some sort of thingy', 'etc.')");
            db.execSQL("INSERT INTO word VALUES (1, 'select', 'choose or designate', 'whatever')");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            Log.d("word search", "Upgrading db from version " + oldVersion + " to " + newVersion);

            db.execSQL(WordDB.DROP_WORD_TABLE);
            onCreate(db);
        }
    }

    private void openReadableDB(){
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB(){
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB(){
        if (db != null)
            db.close();
    }

    public ArrayList<Word> getWords(){

        this.openReadableDB();
        Cursor cursor = db.query(WORD_TABLE, null, null, null, null, null,null);
        ArrayList<Word> words = new ArrayList<Word>();
        while (cursor.moveToNext()){
            Word word = new Word();
            word.setId(cursor.getInt(WORD_ID_COL));
            word.setName(cursor.getString(WORD_NAME_COL));
            word.setDefinition(cursor.getString(WORD_DEFINITION_COL));
            word.setOther(cursor.getString(WORD_OTHER_COL));

            words.add(word);
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();

        return words;

    }

    public ArrayList<Word> searchWords(String wordSearch){

        String where =
                WORD_NAME + " LIKE ?";
        String[] whereArgs = {wordSearch};

        this.openReadableDB();
        Cursor cursor = db.query(WORD_TABLE, null, where, whereArgs, null, null,null);
        ArrayList<Word> words = new ArrayList<Word>();
        while (cursor.moveToNext()){
            Word word = new Word();
            word.setId(cursor.getInt(WORD_ID_COL));
            word.setName(cursor.getString(WORD_NAME_COL));
            word.setDefinition(cursor.getString(WORD_DEFINITION_COL));
            word.setOther(cursor.getString(WORD_OTHER_COL));

            words.add(word);
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();

        return words;

    }
}
