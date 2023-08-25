package com.example.launcher.model;

import android.util.Log;

import com.example.launcher.LauncherAppState;
import com.example.launcher.LauncherModel;
import com.example.launcher.util.ComponentKey;
import com.example.launcher.util.IntArray;
import com.example.launcher.util.LooperExecutor;
import com.example.launcher.model.BgDataModel.Callbacks;
import com.example.launcher.widget.WidgetListRowEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Executor;

public abstract class BaseLoaderResults {

    protected final LooperExecutor mUiExecutor;
    protected final LauncherAppState mApp;
    protected final BgDataModel mBgDataModel;
    private final AllAppsList mBgAllAppsList;

    private final Callbacks[] mCallbacksList;
    private int mMyBindingId;
    private String TAG="";

    public BaseLoaderResults(LauncherAppState app, BgDataModel dataModel,
                             AllAppsList allAppsList, Callbacks[] callbacksList, LooperExecutor uiExecutor) {
        mUiExecutor = uiExecutor;
        mApp = app;
        mBgDataModel = dataModel;
        mBgAllAppsList = allAppsList;
        mCallbacksList = callbacksList;
    }

    public void bindWorkspace() {
        Log.d(TAG, "bindWorkspace");
        // Save a copy of all the bg-thread collections
        ArrayList<ItemInfo> workspaceItems = new ArrayList<>();
        ArrayList<LauncherAppWidgetInfo> appWidgets = new ArrayList<>();
        final IntArray orderedScreenIds = new IntArray();

        synchronized (mBgDataModel) {
            workspaceItems.addAll(mBgDataModel.workspaceItems);
            appWidgets.addAll(mBgDataModel.appWidgets);
            orderedScreenIds.addAll(mBgDataModel.collectWorkspaceScreens());
            mBgDataModel.lastBindId++;
            mMyBindingId = mBgDataModel.lastBindId;
        }

        for (Callbacks cb : mCallbacksList) {
            new WorkspaceBinder(cb, mUiExecutor, mApp, mBgDataModel, mMyBindingId,
                    workspaceItems, appWidgets, orderedScreenIds).bind();
        }
    }

    public void bindAllApps() {
        Log.d(TAG, "bindAllApps");
        // shallow copy
        AppInfo[] apps = mBgAllAppsList.copyData();
        int flags = mBgAllAppsList.getFlags();
        executeCallbacksTask(c -> c.bindAllApplications(apps, flags), mUiExecutor);
    }

    public void bindDeepShortcuts() {
        Log.d(TAG, "bindDeepShortcuts");
        final HashMap<ComponentKey, Integer> shortcutMapCopy;
        synchronized (mBgDataModel) {
            shortcutMapCopy = new HashMap<>(mBgDataModel.deepShortcutMap);
        }
        executeCallbacksTask(c -> c.bindDeepShortcutMap(shortcutMapCopy), mUiExecutor);
    }

    public void bindWidgets() {
        Log.d(TAG, "bindWidgets");
        final ArrayList<WidgetListRowEntry> widgets =
                mBgDataModel.widgetsModel.getWidgetsList(mApp.getContext());
        executeCallbacksTask(c -> c.bindAllWidgets(widgets), mUiExecutor);
    }

    protected void executeCallbacksTask(LauncherModel.CallbackTask task, Executor executor) {
        executor.execute(() -> {
            if (mMyBindingId != mBgDataModel.lastBindId) {
                Log.d(TAG, "Too many consecutive reloads, skipping obsolete data-bind");
                return;
            }
            for (Callbacks cb : mCallbacksList) {
                task.execute(cb);
            }
        });
    }

    private static class WorkspaceBinder {
        private final Executor mUiExecutor;
        private final Callbacks mCallbacks;

        private final LauncherAppState mApp;
        private final BgDataModel mBgDataModel;

        private final int mMyBindingId;
        private final ArrayList<ItemInfo> mWorkspaceItems;
        private final ArrayList<LauncherAppWidgetInfo> mAppWidgets;
        private final IntArray mOrderedScreenIds;

        WorkspaceBinder(Callbacks callbacks,
                        Executor uiExecutor,
                        LauncherAppState app,
                        BgDataModel bgDataModel,
                        int myBindingId,
                        ArrayList<ItemInfo> workspaceItems,
                        ArrayList<LauncherAppWidgetInfo> appWidgets,
                        IntArray orderedScreenIds) {
            mCallbacks = callbacks;
            mUiExecutor = uiExecutor;
            mApp = app;
            mBgDataModel = bgDataModel;
            mMyBindingId = myBindingId;
            mWorkspaceItems = workspaceItems;
            mAppWidgets = appWidgets;
            mOrderedScreenIds = orderedScreenIds;
        }

        private void bind() {

        }
    }

}