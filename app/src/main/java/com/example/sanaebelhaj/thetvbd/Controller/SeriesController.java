package com.example.sanaebelhaj.thetvbd.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sanaebelhaj.thetvbd.Models.Series;
import com.example.sanaebelhaj.thetvbd.Repository.SeriesDB;

/**
 * Created by Sanae BELHAJ on 16/01/2018.
 */

public class SeriesController extends SeriesDB {
    public SeriesController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public boolean create(Series serie) {

        ContentValues values = new ContentValues();

        values.put("title", serie.title);
        values.put("note_user", serie.note_user);
        values.put("note_tvdb", serie.note_tvdb);
        values.put("image", serie.image);
        values.put("poster", serie.poster);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("series", null, values) > 0;
        db.close();

        return createSuccessful;
    }
    public Cursor getAllSeries() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from series",null);
        return res;
    }

    public Cursor getSerrieByTitle(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from series where title ", new String[] { title });
        return res;
    }
    public Cursor getSerrieById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from series where id", new String[] { String.valueOf(id) });
        return res;
    }



}
