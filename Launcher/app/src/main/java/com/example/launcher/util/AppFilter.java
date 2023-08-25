package com.example.launcher.util;

import android.content.Context;

import com.example.launcher.R;

public class AppFilter implements ResourceBasedOverride{
    public static AppFilter newInstance(Context context) {
        return Overrides.getObject(AppFilter.class, context, R.string.app_filter_class);
    }

}
