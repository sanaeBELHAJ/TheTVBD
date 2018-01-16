package com.example.sanaebelhaj.thetvbd.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sanae BELHAJ on 16/01/2018.
 */

public class SeriesDB extends SQLiteOpenHelper {

    public static final String SERIES_KEY = "id";
    public static final String SERIES_NOTEUSER = "note_user";
    public static final String SERIES_NOTETVDB = "note_tvdb";
    public static final String SERIES_IMAGE = "image";
    public static final String SERIES_POSTER = "poster";




    public SeriesDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
