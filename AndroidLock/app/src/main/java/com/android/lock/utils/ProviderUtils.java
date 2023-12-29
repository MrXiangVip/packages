package com.android.lock.utils;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.lock.bean.PackageBean;

public class ProviderUtils {
    public static final Uri uri_package = Uri.parse("content://android.provider/lock");
    private ContentResolver resolver;
    private static Context  context;
    private static ProviderUtils sInstance;
    private String TAG="ProviderUtils.";

    private ProviderUtils(Context context) {
        this.context = context;
        resolver = context.getContentResolver();
    }

    public static ProviderUtils getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ProviderUtils(context);
        }
        return sInstance;
    }

    public PackageBean queryPackageBeanByPackageName( String packageName ){
        PackageBean bean = null;
        String selection = "packageName  " + " = ?";
        String[] selectionArgs = new String[] {packageName};
        Cursor cursor = resolver.query(uri_package, null, selection, selectionArgs, null,null);
        while( cursor.moveToNext() ){
             int flag = cursor.getInt( 1);
             bean = new PackageBean( packageName, flag==0? false:true);
        }
        if( cursor!= null ){
            cursor.close();
        }
        return bean;
    }


    public long insertPackageBean(ContentValues values ){
        Log.d(TAG, "insertPackageBean "+values.toString());
        Uri uri=resolver.insert(uri_package,values);
        long rowId =ContentUris.parseId(uri);
        return rowId;
    }
}
