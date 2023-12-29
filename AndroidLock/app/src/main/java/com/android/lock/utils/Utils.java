package com.android.lock.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import com.android.lock.bean.PackageBean;

import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static List<PackageBean> getLaunchedPackageBean(Context context ){
        PackageBean tmpBean;
        List<PackageBean>  list = new ArrayList();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        for( ResolveInfo resolveInfo : apps) {
            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
            String packageName = applicationInfo.packageName;
            Drawable drawable = resolveInfo.loadIcon( context.getPackageManager());
            String  label = (String) resolveInfo.loadLabel( context.getPackageManager());
//          如果数据库中没有 对应package 的 lock 状态， 则使用默认的lock =true 默认上锁
            PackageBean bean = ProviderUtils.getInstance(context).queryPackageBeanByPackageName( packageName);
            if( bean != null ){
                 tmpBean = new PackageBean(packageName, drawable, label, bean.getLockFlag());

            }else{
                tmpBean = new PackageBean(packageName, drawable, label, true);

            }
            list.add( tmpBean );
        }
        return  list;
    }

}
