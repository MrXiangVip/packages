package com.example.launcher.allapps;

import android.content.Context;
import android.util.Log;

import com.example.launcher.BaseDraggingActivity;
import com.example.launcher.model.AppInfo;
import com.example.launcher.util.ComponentKey;
import com.example.launcher.util.ItemInfoMatcher;

import java.util.ArrayList;
import java.util.List;

public class AlphabeticalAppsList implements AllAppsStore.OnUpdateListener {
    private final List<AppInfo> mApps = new ArrayList<>();
    private final AllAppsStore mAllAppsStore;
    private final BaseDraggingActivity mLauncher;
    private AppInfoComparator mAppNameComparator;
    private final boolean mIsWork;
    private AllAppsGridAdapter mAdapter;
    private final ArrayList<AdapterItem> mAdapterItems = new ArrayList<>();
    private ItemInfoMatcher mItemFilter;
    private ArrayList<ComponentKey> mSearchResults;
    private final List<AppInfo> mFilteredApps = new ArrayList<>();
    private final int mNumAppsPerRow;


    private String TAG="AlphabeticalAppsList";

    public AlphabeticalAppsList(Context context, AllAppsStore appsStore, boolean isWork) {
        mAllAppsStore = appsStore;
        mLauncher = BaseDraggingActivity.fromContext(context);
        mAppNameComparator = new AppInfoComparator(context);
        mIsWork = isWork;
        mNumAppsPerRow = mLauncher.getDeviceProfile().inv.numColumns;
        mAllAppsStore.addUpdateListener(this);
    }

    @Override
    public void onAppsUpdated() {
        mApps.clear();
        for (AppInfo app : mAllAppsStore.getApps()) {
            if (mItemFilter == null || mItemFilter.matches(app, null) || hasFilter()) {
                mApps.add(app);
            }
        }
        updateAdapterItems();

    }
    private void updateAdapterItems() {
        Log.d(TAG, "updateAdapterItems");
        refillAdapterItems();
        refreshRecyclerView();
    }
    private void refillAdapterItems() {
        Log.d(TAG, "refillAdapterItems");
        int position = 0;
        int appIndex = 0;

        mAdapterItems.clear();
        for (AppInfo info : getFiltersAppInfos()) {
            String sectionName = info.sectionName;

            // Create a new section if the section names do not match


            // Create an app item
            AdapterItem appItem = AdapterItem.asApp(position++, sectionName, info, appIndex++);

            mAdapterItems.add(appItem);
            mFilteredApps.add(info);
        }
    }
    private void refreshRecyclerView() {
        Log.d(TAG, "refreshRecyclerView");
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
    public void setAdapter(AllAppsGridAdapter adapter) {
        mAdapter = adapter;
    }

    public List<AdapterItem> getAdapterItems() {
        return mAdapterItems;
    }
    public boolean hasFilter() {
        return (mSearchResults != null);
    }

    private List<AppInfo> getFiltersAppInfos() {
        if (mSearchResults == null) {
            return mApps;
        }
        ArrayList<AppInfo> result = new ArrayList<>();
        for (ComponentKey key : mSearchResults) {
            AppInfo match = mAllAppsStore.getApp(key);
            if (match != null) {
                result.add(match);
            }
        }
        return result;
    }

    public static class AdapterItem {
        public int position;
        // The type of this item
        public int viewType;

        // sectionNames in the same section
        public String sectionName = null;
        // The row that this item shows up on
        public int rowIndex;
        // The index of this app in the row
        public int rowAppIndex;
        // The associated AppInfo for the app
        public AppInfo appInfo = null;
        // The index of this app not including sections
        public int appIndex = -1;

        public static AdapterItem asApp(int pos, String sectionName, AppInfo appInfo,
                                        int appIndex) {
            AdapterItem item = new AdapterItem();
            item.viewType = AllAppsGridAdapter.VIEW_TYPE_ICON;
            item.position = pos;
            item.sectionName = sectionName;
            item.appInfo = appInfo;
            item.appIndex = appIndex;
            return item;
        }
    }

}