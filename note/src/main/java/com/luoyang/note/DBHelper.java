package com.luoyang.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author luoyang
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "ly_note.db";
    private static final int DB_VERSION = 1;
    public static final String DB_TABLE = "BiJi_number";

    public DBHelper(Context paramContext) {
        super(paramContext, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(TAG, "onCreate: " + DB_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        sqLiteDatabase.execSQL("create table if not exists " + DB_TABLE + " (_id integer primary key autoincrement,number varchar)");
        sqLiteDatabase.execSQL("insert into " + DB_TABLE + " (number) values ('202207100054-jun')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(TAG, "onUpgrade");
    }
}
