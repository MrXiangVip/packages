package com.example.launcher.util;

import android.os.UserHandle;

import com.example.launcher.model.ItemInfo;

import java.util.Arrays;

public class PackageUserKey {
    public String mPackageName;
    public UserHandle mUser;
    private int mHashCode;
    public PackageUserKey(String packageName, UserHandle user) {
        update(packageName, user);
    }
    public void update(String packageName, UserHandle user) {
        mPackageName = packageName;
        mUser = user;
        mHashCode = Arrays.hashCode(new Object[] {packageName, user});
    }
    public boolean updateFromItemInfo(ItemInfo info) {
        if (info.getTargetComponent() == null) return false;
//        if (ShortcutUtil.supportsShortcuts(info)) {
//            update(info.getTargetComponent().getPackageName(), info.user);
//            return true;
//        }
        return false;
    }
}
