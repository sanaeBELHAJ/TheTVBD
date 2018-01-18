package com.example.sanaebelhaj.thetvbd.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sanae BELHAJ on 16/01/2018.
 */

public class SeriesDB extends SQLiteOpenHelper {

    public static final String SERIES_KEY = "id";
    public static final String SERIES_TITLE = "title";
    public static final String SERIES_NOTEUSER = "note_user";
    public static final String SERIES_NOTETVDB = "note_tvdb";
    public static final String SERIES_IMAGE = "image";
    public static final String SERIES_POSTER = "poster";

    public static final String SERIES_TABLE_NAME = "series";
    public static final String SERIES_TABLE_CREATE =
            "CREATE TABLE " + SERIES_TABLE_NAME + " (" +
                    SERIES_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SERIES_TITLE +" TEXT, "+
                    SERIES_NOTEUSER + " INTEGER, " +
                    SERIES_NOTETVDB + " INTEGER, " +
                    SERIES_IMAGE + " TEXT, " +
                    SERIES_POSTER + " TEXT);";
    public static final String SERIES_TABLE_DROP = "DROP TABLE IF EXISTS " + SERIES_TABLE_NAME + ";";




    public SeriesDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SERIES_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(SERIES_TABLE_DROP);
        onCreate(db);
    }
}
