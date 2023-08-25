package com.example.launcher;

import android.net.Uri;
import android.provider.BaseColumns;

import com.example.launcher.provider.LauncherProvider;

public class LauncherSettings {

    public static final class Favorites implements BaseColumns {
        public static final String TABLE_NAME = "favorites";

        public static final Uri CONTENT_URI = Uri.parse("content://"
                + LauncherProvider.AUTHORITY + "/" + TABLE_NAME);
    }
}