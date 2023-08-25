package com.example.launcher;

import static com.example.launcher.util.Executors.MAIN_EXECUTOR;
import static com.example.launcher.util.Executors.MODEL_EXECUTOR;

import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.util.Log;

import com.example.launcher.icons.IconCache;
import com.example.launcher.model.AllAppsList;
import com.example.launcher.model.AppInfo;
import com.example.launcher.model.BgDataModel;
import com.example.launcher.model.BgDataModel.Callbacks;
import com.example.launcher.model.LoaderResults;
import com.example.launcher.model.LoaderTask;
import com.example.launcher.model.ModelWriter;
import com.example.launcher.util.AppFilter;
import com.example.launcher.util.LooperExecutor;

import java.util.ArrayList;

public class LauncherModel extends  LauncherApps.Callback {
    private final LauncherAppState mApp;
    private final AllAppsList mBgAllAppsList;
    private final Object mLock = new Object();
    private final ArrayList<Callbacks> mCallbacksList = new ArrayList<>(1);
    private LoaderTask mLoaderTask;
    private final LooperExecutor mMainExecutor = MAIN_EXECUTOR;
    private final BgDataModel mBgDataModel = new BgDataModel();
    private boolean mModelLoaded;
    private boolean mIsLoaderTaskRunning;

    private String TAG="LauncherModel";

    LauncherModel(LauncherAppState app, IconCache iconCache, AppFilter appFilter) {
        mApp = app;
        mBgAllAppsList = new AllAppsList(iconCache, appFilter);
    }

    @Override
    public void onPackageRemoved(String packageName, UserHandle user) {

    }

    @Override
    public void onPackageAdded(String packageName, UserHandle user) {

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

    public boolean addCallbacksAndLoad(Callbacks callbacks) {
        Log.d(TAG, "addCallbacksAndLoad");
        synchronized (mLock) {
            addCallbacks(callbacks);
            return startLoader();

        }
    }

    /**
     * Adds a callbacks to receive model updates
     */
    public void addCallbacks(Callbacks callbacks) {
//        Preconditions.assertUIThread();
        synchronized (mCallbacksList) {
            mCallbacksList.add(callbacks);
        }
    }

    public boolean startLoader() {
        Log.d(TAG, "startLoader");
//        InstallShortcutReceiver.enableInstallQueue(InstallShortcutReceiver.FLAG_LOADER_RUNNING);
        synchronized (mLock) {
            // Don't bother to start the thread if we know it's not going to do anything
            final Callbacks[] callbacksList = getCallbacks();
            if (callbacksList.length > 0) {
                // Clear any pending bind-runnables from the synchronized load process.
                for (Callbacks cb : callbacksList) {
                    mMainExecutor.execute(cb::clearPendingBinds);
                }

                // If there is already one running, tell it to stop.
                stopLoader();
                LoaderResults loaderResults = new LoaderResults(
                        mApp, mBgDataModel, mBgAllAppsList, callbacksList, mMainExecutor);
                if (mModelLoaded && !mIsLoaderTaskRunning) {
                    // Divide the set of loaded items into those that we are binding synchronously,
                    // and everything else that is to be bound normally (asynchronously).
                    loaderResults.bindWorkspace();
                    // For now, continue posting the binding of AllApps as there are other
                    // issues that arise from that.
                    loaderResults.bindAllApps();
                    loaderResults.bindDeepShortcuts();
                    loaderResults.bindWidgets();
                    return true;
                } else {
                    startLoaderForResults(loaderResults);
                }
            }
        }
        return false;
    }

    public Callbacks[] getCallbacks() {
        synchronized (mCallbacksList) {
            return mCallbacksList.toArray(new Callbacks[mCallbacksList.size()]);
        }
    }

    public boolean stopLoader() {
        synchronized (mLock) {
            LoaderTask oldTask = mLoaderTask;
            mLoaderTask = null;
            if (oldTask != null) {
                oldTask.stopLocked();
                return true;
            }
            return false;
        }
    }

    public void startLoaderForResults(LoaderResults results) {
        Log.d(TAG, "startLoaderForResults");
        synchronized (mLock) {
            stopLoader();
            mLoaderTask = new LoaderTask(mApp, mBgAllAppsList, mBgDataModel, results);

            // Always post the loader task, instead of running directly (even on same thread) so
            // that we exit any nested synchronized blocks
            MODEL_EXECUTOR.post(mLoaderTask);
        }
    }
    public ModelWriter getWriter(boolean hasVerticalHotseat, boolean verifyChanges) {
        return new ModelWriter(mApp.getContext(), this, mBgDataModel,
                hasVerticalHotseat, verifyChanges);
    }

    public interface CallbackTask {

        void execute(Callbacks callbacks);

    }

}