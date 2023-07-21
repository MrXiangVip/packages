package com.mediatek.hralauncher.model.impl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.model.interf.ISystemPMHooker;
import com.mediatek.hralauncher.model.interf.ISystemPackageManager;

import java.util.ArrayList;
import java.util.List;

public class LauncherSystemPM implements ISystemPackageManager {

    private final Context mContext;
    private final ISystemPMHooker mHooker;
    private final PackageManager mPM;

    public LauncherSystemPM(Context context, ISystemPMHooker hooker) {
        this.mContext = context;
        this.mPM = context.getPackageManager();
        this.mHooker = hooker;
    }

    @Override
    public List<AppInfo> queryAll(String str) {
        List<AppInfo> result= new ArrayList();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> apps = mPM.queryIntentActivities(mainIntent, 0);
        for( ResolveInfo resolveInfo : apps){
            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
            AppInfo appInfo = new AppInfo();
            appInfo.setPackageName( applicationInfo.packageName );
            appInfo.setAppName( resolveInfo.activityInfo.name);
            appInfo.setIcon( applicationInfo.loadIcon(mPM));
            result.add( appInfo);
        }

        return result;
    }
}
