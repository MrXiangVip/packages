package com.mediatek.hralauncher.common;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class AppInfo implements Serializable, Comparable<AppInfo>{
    public String appName;
    private Drawable icon;
    public String packageName;

    public AppInfo() {
    }

    public AppInfo(String packageName, String appName, Drawable icon) {
        this.packageName = packageName;
        this.appName = appName;
        this.icon = icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
    public Drawable getIcon(){
        return  icon;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
    public String getAppName() {
        return appName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getPackageName( ){
        return  packageName;
    }
    @Override
    public int compareTo(AppInfo o) {
        return 0;
    }
}
