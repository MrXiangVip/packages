package com.mediatek.hralauncher.model.impl;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.holder.BaseHolder;
import com.mediatek.hralauncher.holder.ModuleThirdPartAppHolder;
import com.mediatek.hralauncher.holder.ThirdPartAppHolder;
import com.mediatek.hralauncher.model.interf.IAppPosMgr;
import com.mediatek.hralauncher.model.interf.ILauncherPageFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LauncherAppViewHolderMgr implements ILauncherPageFactory {
    Context mContext;
    IAppPosMgr mPosMgr;
    private int EVER_PAGE_SIZE = 12;

    private String TAG = "LauncherAppViewHolderMgr.";

    public LauncherAppViewHolderMgr(Context context, IAppPosMgr posMgr) {
        mContext = context;
        mPosMgr = posMgr;
    }

    @Override
    public List<BaseHolder> createHolders(String gradePeriod, List<AppInfo> appInfoList) {
        Log.d(TAG,"createHolders "+gradePeriod);
//        List<List<AppInfo>> pages = mPosMgr.buildPages(gradePeriod, appInfoList);
//        return createByPage(gradePeriod, pages);
        return  createNormal(gradePeriod,  appInfoList);
    }


    private List<BaseHolder> createNormal(String type, List<AppInfo> appInfoList) {
        Log.d(TAG, "createNormal " + type);
        int allAppCount = appInfoList.size();
        ArrayList<BaseHolder> result = new ArrayList<BaseHolder>();
        for (int i = 0; i < allAppCount; ) {
            ArrayList<AppInfo> appList = new ArrayList(EVER_PAGE_SIZE);
            BaseHolder holder;
            int endIndex=0;
            if( (i+EVER_PAGE_SIZE) >= allAppCount ){
                endIndex = allAppCount;
            }else{
                endIndex =i+EVER_PAGE_SIZE;
            }
            for (int j = i; j < endIndex; j++) {
                appList.add(appInfoList.get(j));
            }
            if (i == 0) {
                holder = new ModuleThirdPartAppHolder(mContext, appList);
            } else {
                holder = new ThirdPartAppHolder(mContext, appList);
            }
            holder.onCreate();
            result.add(holder);
            i += EVER_PAGE_SIZE;
        }
        return result;
    }

    private List<BaseHolder> createByPage(String type, List<List<AppInfo>> appInfoList) {
        Log.d(TAG,"createByPage ");
        int pages = appInfoList.size();
        ArrayList<BaseHolder> result = new ArrayList<BaseHolder>(pages);
        for (int i = 0; i < pages; i++) {
            ArrayList<AppInfo> appList = new ArrayList(appInfoList.get(i));
            if (i == 0) {
                BaseHolder holder = new ModuleThirdPartAppHolder(mContext, appList);
                holder.onCreate();
                result.add(holder);
            } else {
                List<BaseHolder> list = createNormalNotModule(appList);
                if (list != null) {
                    result.addAll(list);
                }
            }
        }
        return result;
    }

    private List<BaseHolder> createNormalNotModule(List<AppInfo> appInfoList) {
        int allAppCount = appInfoList.size();
        ArrayList<BaseHolder> result = new ArrayList<>( (allAppCount + EVER_PAGE_SIZE - 1) / EVER_PAGE_SIZE );
        for(int i=0; i<allAppCount; ){
            ArrayList<AppInfo> appList = new ArrayList(EVER_PAGE_SIZE);
            int endIndex =0 ;
            if( (i+EVER_PAGE_SIZE) >= allAppCount ){
                endIndex = allAppCount;
            }else{
                endIndex =i+EVER_PAGE_SIZE;
            }
            for( int j=i; j< endIndex;j++ ){
                appList.add( appInfoList.get(j));
            }
            ThirdPartAppHolder  holder = new ThirdPartAppHolder(mContext, appList);
            holder.onCreate();
            result.add(holder);
//           到下一页
            i+=EVER_PAGE_SIZE;
        }
        return result;
    }
}
