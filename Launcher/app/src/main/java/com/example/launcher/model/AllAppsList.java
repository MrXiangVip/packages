package com.example.launcher.model;

import static com.example.launcher.model.AppInfo.EMPTY_ARRAY;

import android.os.LocaleList;

import com.example.launcher.icons.IconCache;
import com.example.launcher.util.AppFilter;

import java.util.ArrayList;
import java.util.Arrays;

public class AllAppsList {
    public static final int DEFAULT_APPLICATIONS_NUMBER = 42;

    public final ArrayList<AppInfo> data = new ArrayList<>(DEFAULT_APPLICATIONS_NUMBER);


    private IconCache mIconCache;
    private AppFilter mAppFilter;
    private int mFlags;

    public AllAppsList(IconCache iconCache, AppFilter appFilter) {
        mIconCache = iconCache;
        mAppFilter = appFilter;
//        mIndex = new AlphabeticIndexCompat(LocaleList.getDefault());
    }

    public AppInfo[] copyData() {
        AppInfo[] result = data.toArray(EMPTY_ARRAY);
//        Arrays.sort(result, COMPONENT_KEY_COMPARATOR);
        return result;
    }
    public int getFlags() {
        return mFlags;
    }
}
