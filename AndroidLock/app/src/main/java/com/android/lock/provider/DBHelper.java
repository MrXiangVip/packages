package com.android.lock.provider;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.lock.utils.DBUtils;

public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = "DBHelper.XSHX";
    private static final String DATABASE_NAME = "application.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "CREATE TABLE IF NOT EXISTS");
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DBUtils.LOCK_TABLE_NAME + "("
                +" packageName TEXT PRIMARY KEY,"
                +" lockCondition BOOLEAN DEFAULT 1" //默认上锁
                +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
