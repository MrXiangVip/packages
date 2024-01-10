package com.android.lock.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.lock.utils.DBUtils;

public class LockProvider extends ContentProvider {
    Context mContext;
    private static final UriMatcher mMatcher;
    public static final String AUTOHORITY = "android.provider";
    public static final int Lock_Code = 1;
//    DBHelper mDbHelper = null;
//    SQLiteDatabase db = null;
    private String TAG="LockProvider.";

    static {
        mMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        // 初始化
        mMatcher.addURI(AUTOHORITY, "lock", Lock_Code);
    }


    @Override
    public boolean onCreate() {
        mContext = getContext();
//        mDbHelper = new DBHelper(mContext);
//        db = mDbHelper.getWritableDatabase();

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String table = getTableName(uri);
        SQLiteDatabase db = new DBHelper(mContext).getWritableDatabase();
        return db.query(table, projection, selection, selectionArgs, null, null, sortOrder, null);

    }



    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.d(TAG, "insert "+values.toString());
        Uri url = null;
        String table = getTableName(uri);
        SQLiteDatabase db = new DBHelper(mContext).getWritableDatabase();
        long rowID = db.replace(table, null, values);
        if (rowID > 0) {
            url = ContentUris.withAppendedId(uri, rowID);
        }
        return url;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (mMatcher.match(uri)) {
            case Lock_Code:
                tableName = DBUtils.LOCK_TABLE_NAME;
                break;

        }
        return tableName;
    }
}