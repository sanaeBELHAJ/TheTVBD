package com.example.sanaebelhaj.thetvbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sanae BELHAJ on 11/01/2018.
 */

public class Model  extends SQLiteOpenHelper {

    String tag ="verified";
    public Model(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user (id integer primary-key, firstname varcher(50), lastname varchar(50), email varchar(250), pwd varchar(50)");
        Log.i(tag, "creation done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
