package com.android.lock.bean;

import android.graphics.drawable.Drawable;

public class PackageBean {
    private String packageName;
    private Drawable packageIcon;
    private String packageLabel;
    private int lockFlag;  //0. 关锁    1.开锁     2.不可见

    public PackageBean(String name, Drawable drawable, String label, int flag) {
        packageName = name;
        packageIcon = drawable;
        packageLabel = label;
        lockFlag= flag;
    }

    public PackageBean(String name, int flag){
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

    public void setLockFlag(int lockFlag) {
        this.lockFlag = lockFlag;
    }

     public int getLockFlag( ){
        return  lockFlag;
     }
}
