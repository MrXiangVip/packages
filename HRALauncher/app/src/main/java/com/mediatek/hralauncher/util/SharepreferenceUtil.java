package com.mediatek.hralauncher.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharepreferenceUtil {
    private Context context;
    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;
    public static SharepreferenceUtil sharepreferenceUtil = null;
    public static String SHAREPREFERENCE_NAME = "mylanuchinfo";

    private SharepreferenceUtil() {
    }
    public void setContext(Context context2) {
        this.context = context2;
    }
    public static SharepreferenceUtil getSharepferenceInstance(Context context2) {
        sharedPreferences = context2.getSharedPreferences(SHAREPREFERENCE_NAME, 0);
        editor = sharedPreferences.edit();
        if (sharepreferenceUtil != null) {
            return sharepreferenceUtil;
        }
        sharepreferenceUtil = new SharepreferenceUtil();
        return sharepreferenceUtil;
    }
}
