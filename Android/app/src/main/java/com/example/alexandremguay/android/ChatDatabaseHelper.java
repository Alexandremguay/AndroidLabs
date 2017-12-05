package com.example.alexandremguay.android;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Alexandre M Guay on 2017-10-17.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final String ACTIVITY_NAME = "ChatDatabaseHelper";
    private static final String DATABASE_NAME = "Messages.db";
    private static int VERSION_NUM = 3;
    public static final String i = "KEY_ID";
    public static final String m = "KEY_MESSAGE";
    public static final String c = "CHAT";


    public ChatDatabaseHelper (Context ctx){
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       db.execSQL("CREATE TABLE " + c + "(" + i + " INTEGER PRIMARY KEY AUTOINCREMENT, " + m + " TEXT);");
        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS " + c);
        Log.i(ACTIVITY_NAME, "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {

    }


}
