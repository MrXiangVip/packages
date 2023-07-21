package com.mediatek.hralauncher.model.impl;

import android.util.Log;

import com.mediatek.hralauncher.classify.PrimitiveSimpleAdapter;
import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.holder.BaseHolder;
import com.mediatek.hralauncher.holder.ThirdPartAppHolder;
import com.mediatek.hralauncher.model.interf.IAppPosMgr;
import com.mediatek.hralauncher.model.interf.ILauncherPageFactory;
import com.mediatek.hralauncher.model.interf.ILauncherPagersView;
import com.mediatek.hralauncher.model.interf.ILauncherView;

import java.util.ArrayList;
import java.util.List;

public class LauncherModelView implements ILauncherView {
    String                  mGradePeriod;
    ILauncherPagersView     pagersView;
    ILauncherPageFactory    launcherPageFactory;
    IAppPosMgr              appPosMgr;
    Boolean                 isFirstStart;
    private ArrayList<BaseHolder> mAppViewHolders = new ArrayList<>();
    private String TAG="LauncherModelView.";

    public LauncherModelView(ILauncherPageFactory pageFactory, IAppPosMgr posMgr, ILauncherPagersView launcherPagersView, Boolean firstStart){
        launcherPageFactory = pageFactory;
        appPosMgr = posMgr;
        pagersView =launcherPagersView ;
        isFirstStart = firstStart;
    }

    @Override
    public void initAppPages(String gradePeriod, List<AppInfo> appInfos) {
        Log.d(TAG,"initAppPages");
        mGradePeriod = gradePeriod;
        mAppViewHolders.clear();
        List<BaseHolder> holders = launcherPageFactory.createHolders(gradePeriod, appInfos );
        mAppViewHolders.addAll( holders );
        addPagesToActivity(mGradePeriod);
    }

    @Override
    public void onCreateCardPages(String gradePeriod) {
        Log.d(TAG,"onCreateCardPages");
        pagersView.onCreateCardPages(gradePeriod);
    }

    private void addPagesToActivity(String gradePeriod){
        Log.d(TAG,"addPagesToActivity "+gradePeriod);
        for (BaseHolder viewHolder : mAppViewHolders) {
            viewHolder.onCreate();
        }
        pagersView.initAppPages(gradePeriod, mAppViewHolders);
    }
}
