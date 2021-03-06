package com.example.sanaebelhaj.thetvbd.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sanaebelhaj.thetvbd.Models.User;
import com.example.sanaebelhaj.thetvbd.Repository.UserDB;

/**
 * Created by Sanae BELHAJ on 16/01/2018.
 */

public class UserController  extends UserDB{


    public UserController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public boolean create(User user) {

        ContentValues values = new ContentValues();

        values.put("firstname", user.firstname);
        values.put("lastname", user.firstname);
        values.put("pseudo", user.pseudo);
        values.put("email", user.email);
        values.put("pwd", user.pwd);
        values.put("language", user.language);

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert("user", null, values) > 0;
        db.close();

        return createSuccessful;
    }
    public Cursor getUserById(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user where id", new String[] { String.valueOf(id) });
        return res;
    }

}
