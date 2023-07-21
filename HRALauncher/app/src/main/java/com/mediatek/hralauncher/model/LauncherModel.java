package com.mediatek.hralauncher.model;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.util.Log;

import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.model.impl.LauncherSystemPM;
import com.mediatek.hralauncher.model.interf.ILauncherView;
import com.mediatek.hralauncher.model.interf.ISystemPMHooker;
import com.mediatek.hralauncher.model.interf.ISystemPackageManager;
import com.mediatek.hralauncher.util.LauncherExecutors;
import com.mediatek.hralauncher.util.MainThreadExecutor;
import com.mediatek.hralauncher.util.WorkThreadExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LauncherModel {

    Context mContext;
    private static LauncherModel sModel;
    private ILauncherView mLauncherView;
    String mGradePeriod;
    MainThreadExecutor mUIExecutor = LauncherExecutors.sMainExecutor;

    WorkThreadExecutor mWorkExecutor = LauncherExecutors.sAppLoaderExecutor;
    ArrayList<AppInfo> mAllAppList = new ArrayList();
    ArrayList<AppInfo> mShowAppList = new ArrayList();
    HashMap<String, AppInfo> mAppListIndex = new HashMap();
    ISystemPackageManager mPM;
    private PackageCallback mPackageCallback;
    private LauncherApps mLauncherApps;
    private String TAG="LauncherModel.";

    class PackageCallback extends LauncherApps.Callback {

        @Override
        public void onPackageRemoved(String packageName, UserHandle user) {
            Log.d(TAG, "onPackageRemoved "+packageName);
        }

        @Override
        public void onPackageAdded(String packageName, UserHandle user) {
            Log.d(TAG, "onPackageAdded "+packageName);
        }

        @Override
        public void onPackageChanged(String packageName, UserHandle user) {

        }

        @Override
        public void onPackagesAvailable(String[] packageNames, UserHandle user, boolean replacing) {

        }

        @Override
        public void onPackagesUnavailable(String[] packageNames, UserHandle user, boolean replacing) {

        }
    }

    private LauncherModel() {
    }

    public static LauncherModel getInstance() {
        if (sModel == null) {
            sModel = new LauncherModel();
        }
        return sModel;
    }

    public void init(Context context, ISystemPMHooker hooker) {
        if (mContext == null) {
            mContext = context.getApplicationContext();
            mPM = new LauncherSystemPM(mContext, hooker);
            mPackageCallback = new PackageCallback();
            mLauncherApps = (LauncherApps) mContext.getSystemService(Context.LAUNCHER_APPS_SERVICE);
            mLauncherApps.registerCallback(this.mPackageCallback);
        }
    }

    public void setLauncherView(ILauncherView launcherView) {
        mLauncherView = launcherView;
    }

    public void start(String gradePeriod) {
        Log.d(TAG, "start "+gradePeriod);
        mGradePeriod = gradePeriod;
        mWorkExecutor.execute(new GetAllAppsTask(mGradePeriod));
        mLauncherView.onCreateCardPages(mGradePeriod);
    }


    class GetAllAppsTask implements Runnable {
        private String mLocalGradePeriod;

        GetAllAppsTask(String localGradePeriod) {
            mLocalGradePeriod = localGradePeriod;
        }

        @Override
        public void run() {
            Log.d(TAG, "run");
            List<AppInfo> appInfos = mPM.queryAll(mLocalGradePeriod);
            List<AppInfo> showAppInfos = filterAndSort(mLocalGradePeriod, appInfos);
            HashMap<String, AppInfo> indexMap = createIndex(appInfos);
            mUIExecutor.execute(
                    new GetAllAppsResultTask(
                            mLocalGradePeriod,
                            appInfos,
                            showAppInfos,
                            indexMap
                    )
            );
            mUIExecutor.execute(new InitTaskAfterAppInited());
        }
    }

    class GetAllAppsResultTask implements Runnable {
        String mLocalGradePeriod;
        List<AppInfo> allAppInfos;
        List<AppInfo> mShowAppInfos;
        Map<String, AppInfo> mIndexMap;

        public GetAllAppsResultTask(String localGradePeriod, List<AppInfo> appInfos, List<AppInfo> showAppInfos, HashMap<String, AppInfo> indexMap) {
            mLocalGradePeriod = localGradePeriod;
            allAppInfos = appInfos;
            mShowAppInfos = showAppInfos;
            mIndexMap = indexMap;
        }

        @Override
        public void run() {
            Log.d(TAG, "run");
            mAllAppList.clear();
            mAllAppList.addAll(allAppInfos);
            mShowAppList.clear();
            mShowAppList.addAll(mShowAppInfos);
            mAppListIndex.clear();
//            mAppListIndex.putAll(mIndexMap);
            bindShowApp(mShowAppList);
            startUploadAllAppInfos();
        }

        private void bindShowApp(List<AppInfo> appList) {
            Log.d(TAG, "bindShowApp");
//            mLauncherView.initAppPages(mLocalGradePeriod, AppShowHelper.filter(appList));
            mLauncherView.initAppPages(mLocalGradePeriod, appList);
        }
    }

    class InitTaskAfterAppInited implements Runnable {
        @Override
        public void run() {
            Log.d(TAG,"run");
        }
    }

    private void startUploadAllAppInfos() {
        Log.d(TAG,"startUploadAllAppInfos");
    }

    List<AppInfo> filterAndSort(String gradePeriod, List<AppInfo> appInfos) {
        return appInfos;
    }

    private  HashMap<String, AppInfo> createIndex(List<AppInfo> appInfos)
    {
        return  null;
    }
}