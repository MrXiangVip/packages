/*
* vendor/mediatek/proprietary/packages/apps/Launcher3/src/com/android/launcher3/model/LoaderTask.java
* */
package com.example.launcher.model;

import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import android.net.Uri;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Log;

import com.example.launcher.LauncherAppState;
import com.example.launcher.LauncherSettings;
import com.example.launcher.config.FeatureFlags;
import com.example.launcher.icons.ComponentWithLabelAndIcon;
import com.example.launcher.icons.IconCache;
import com.example.launcher.pm.InstallSessionHelper;
import com.example.launcher.pm.UserCache;

import java.util.ArrayList;
import java.util.List;

public class LoaderTask implements Runnable {
    private boolean mStopped;

    protected final LauncherAppState mApp;
    private final AllAppsList mBgAllAppsList;
    protected final BgDataModel mBgDataModel;
    private final LoaderResults mResults;
    private final LauncherApps mLauncherApps;
    private final UserManager mUserManager;
    private final UserCache mUserCache;
    private final InstallSessionHelper mSessionHelper;
    private final IconCache mIconCache;
    private String TAG = "LoaderTask";

    public LoaderTask(LauncherAppState app, AllAppsList bgAllAppsList, BgDataModel dataModel,
                      LoaderResults results) {
        mApp = app;
        mBgAllAppsList = bgAllAppsList;
        mBgDataModel = dataModel;
        mResults = results;

        mLauncherApps = mApp.getContext().getSystemService(LauncherApps.class);
        mUserManager = mApp.getContext().getSystemService(UserManager.class);
        mUserCache = UserCache.INSTANCE.get(mApp.getContext());
        mSessionHelper = InstallSessionHelper.INSTANCE.get(mApp.getContext());
        mIconCache = mApp.getIconCache();
    }

    @Override
    public void run() {
        Log.d(TAG, "LoaderTask run");
        try {
            List<ShortcutInfo> allShortcuts = new ArrayList<>();
            loadWorkspace(allShortcuts);
            mResults.bindWorkspace();

            // second step
            List<LauncherActivityInfo> allActivityList = loadAllApps();
            mResults.bindAllApps();
            // third step
            List<ShortcutInfo> allDeepShortcuts = loadDeepShortcuts();
            mResults.bindDeepShortcuts();
            // fourth step
            List<ComponentWithLabelAndIcon> allWidgetsList =
                    mBgDataModel.widgetsModel.update(mApp, null);
            mResults.bindWidgets();
            // fifth step
            if (FeatureFlags.FOLDER_NAME_SUGGEST.get()) {
                loadFolderNames();
            }

        } catch (Exception e) {

        } finally {

        }

    }

    public synchronized void stopLocked() {
        mStopped = true;
        this.notify();
    }

    private void loadWorkspace(List<ShortcutInfo> allDeepShortcuts) {
        Log.d(TAG, "loadWorkspace");
        loadWorkspace(allDeepShortcuts, LauncherSettings.Favorites.CONTENT_URI);
    }

    protected void loadWorkspace(List<ShortcutInfo> allDeepShortcuts, Uri contentUri) {

    }

    private List<LauncherActivityInfo> loadAllApps() {
        Log.d(TAG, "loadAllApps");
        final List<UserHandle> profiles = mUserCache.getUserProfiles();

        List<LauncherActivityInfo> allActivityList = new ArrayList<>();
        for (UserHandle user : profiles) {
            final List<LauncherActivityInfo> apps = mLauncherApps.getActivityList(null, user);
            if (apps == null || apps.isEmpty()) {
                return allActivityList;
            }
            allActivityList.addAll(apps);

        }
        return allActivityList;
    }

    private List<ShortcutInfo> loadDeepShortcuts() {
        Log.d(TAG, "loadDeepShortcuts");
        List<ShortcutInfo> allShortcuts = new ArrayList<>();
        return allShortcuts;

    }
    private void loadFolderNames() {
        Log.d(TAG, "loadFolderNames");

    }
}