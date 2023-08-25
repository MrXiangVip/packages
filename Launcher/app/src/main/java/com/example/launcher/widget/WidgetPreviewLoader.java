package com.example.launcher.widget;

import android.content.Context;

import com.example.launcher.icons.IconCache;
import com.example.launcher.pm.UserCache;
import com.example.launcher.util.SQLiteCacheHelper;

public class WidgetPreviewLoader {

    private final Context mContext;
    private final IconCache mIconCache;
    private final UserCache mUserCache;
    private final CacheDb mDb;

    public WidgetPreviewLoader(Context context, IconCache iconCache) {
        mContext = context;
        mIconCache = iconCache;
        mUserCache = UserCache.INSTANCE.get(context);
        mDb = new CacheDb(context);
    }

    private static class CacheDb extends SQLiteCacheHelper {

        public CacheDb(Context context) {
            super();
        }
    }

}