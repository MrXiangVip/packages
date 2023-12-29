package com.android.lock.bean;

import android.graphics.drawable.Drawable;

public class PackageBean {
    private String packageName;
    private Drawable packageIcon;
    private String packageLabel;
    private boolean lockFlag;

    public PackageBean(String name, Drawable drawable, String label, boolean flag) {
        packageName = name;
        packageIcon = drawable;
        packageLabel = label;
        lockFlag= flag;
    }

    public PackageBean(String name, boolean flag){
        packageName = name;
        lockFlag = flag;
    }

    public void setPackageName( String name){
        packageName = name;
    }
    public String getPackageName( ){
        return packageName;
    }

    public void setPackageIcon(Drawable packageIcon) {
        this.packageIcon = packageIcon;
    }

    public Drawable getPackageIcon() {
        return packageIcon;
    }

    public void setPackageLabel(String packageLabel) {
        this.packageLabel = packageLabel;
    }

    public String getPackageLabel() {
        return packageLabel;
    }

    public void setLockFlag(boolean lockFlag) {
        this.lockFlag = lockFlag;
    }

     public boolean getLockFlag( ){
        return  lockFlag;
     }
}
