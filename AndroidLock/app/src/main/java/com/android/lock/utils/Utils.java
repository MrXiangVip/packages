package com.android.lock.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;

import com.android.lock.bean.PackageBean;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static final int  UNCHECKED=0;
    public static final int  CHECKED =1;
    public static final int  GONE=2;

    public static void initLaunchedPackageBean(Context context){

        ContentValues values = new ContentValues();
        values.put("packageName", "com.android.settings");
        values.put("lockCondition", GONE);// 乐乐智学 不可见
        ProviderUtils.getInstance(context).insertPackageBean( values);

//        ContentValues values = new ContentValues();
//        values.put("packageName", "com.android.settings");
//        values.put("lockCondition", 3);// 不可见
//        ProviderUtils.getInstance(context).insertPackageBean( values);

    }


    public static List<PackageBean> getLaunchedPackageBean(Context context ){
        PackageBean tmpBean = null;
        List<PackageBean>  list = new ArrayList();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(mainIntent, 0);
        for( ResolveInfo resolveInfo : apps) {
            ApplicationInfo applicationInfo = resolveInfo.activityInfo.applicationInfo;
            String packageName = applicationInfo.packageName;
            Drawable drawable = resolveInfo.loadIcon( context.getPackageManager());
            String  label = (String) resolveInfo.loadLabel( context.getPackageManager());
            PackageBean bean = ProviderUtils.getInstance(context).queryPackageBeanByPackageName( packageName);
            if( bean != null ){
//          如果数据库中有 对应package 的 lock 状态
                if( bean.getLockFlag() != GONE ){
                    tmpBean = new PackageBean(packageName, drawable, label, bean.getLockFlag());
                    list.add( tmpBean );
                }

            }else{
//          如果数据库中没有设置过 flag ，使用默认的  CHECKED ,默认上锁
                tmpBean = new PackageBean(packageName, drawable, label, CHECKED);//
                list.add( tmpBean );
            }
        }
        return  list;
    }

}
