package com.example.launcher.model;

import android.content.ComponentName;

import java.util.Comparator;

public class AppInfo extends ItemInfoWithIcon{
    public static final AppInfo[] EMPTY_ARRAY = new AppInfo[0];

    public String sectionName = "";
    public ComponentName componentName;

    public static final Comparator<AppInfo> COMPONENT_KEY_COMPARATOR = (a, b) -> {
        int uc = a.user.hashCode() - b.user.hashCode();
        return uc != 0 ? uc : a.componentName.compareTo(b.componentName);
    };
}
