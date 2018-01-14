package com.example.sanaebelhaj.thetvbd.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Sanae BELHAJ on 14/01/2018.
 */

public class UserDB extends SQLiteOpenHelper {

    public static final String USER_KEY = "id";
    public static final String USER_FIRSTNAME = "firstname";
    public static final String USER_LASTNAME = "lastname";
    public static final String USER_EMAIL = "email";
    public static final String USER_PWD = "pwd";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    USER_FIRSTNAME + " TEXT, " +
                    USER_LASTNAME + " TEXT, " +
                    USER_EMAIL + " TEXT, " +
                    USER_PWD + " TEXT);";
    public static final String METIER_TABLE_DROP = "DROP TABLE IF EXISTS " + USER_TABLE_NAME + ";";

    public UserDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(USER_TABLE_NAME);
        onCreate(db);
    }
}
