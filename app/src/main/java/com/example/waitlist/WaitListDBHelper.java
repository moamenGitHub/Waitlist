package com.example.waitlist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.waitlist.WaitListContract.*;

public class WaitListDBHelper extends SQLiteOpenHelper {

    public static final String DataBaseName="waitlist.db";
    public static final int DataBaseVersion=1;

    public WaitListDBHelper( Context context)
    {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE="CREATE TABLE " +
        WaitListEntry.Table_Name + " (" +
        WaitListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        WaitListEntry.Column_Name + " TEXT NOT NULL, " +
        WaitListEntry.Column_Amount + " TEXT NOT NULL, " +
        WaitListEntry.Column_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+ WaitListEntry.Column_Name);
        onCreate(db);

    }
}
