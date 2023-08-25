package com.example.launcher.allapps;

import static com.example.launcher.model.AppInfo.COMPONENT_KEY_COMPARATOR;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.launcher.model.AppInfo;
import com.example.launcher.model.ItemInfo;
import com.example.launcher.util.ComponentKey;
import com.example.launcher.util.PackageUserKey;
import com.example.launcher.widget.BubbleTextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class AllAppsStore {

    public static final AppInfo[] EMPTY_ARRAY = new AppInfo[0];

    private AppInfo[] mApps = EMPTY_ARRAY;
    private int mModelFlags;
    private PackageUserKey mTempKey = new PackageUserKey(null, null);
    private final ArrayList<ViewGroup> mIconContainers = new ArrayList<>();
    private int mDeferUpdatesFlags = 0;
    private boolean mUpdatePending = false;
    private final List<OnUpdateListener> mUpdateListeners = new CopyOnWriteArrayList<>();
    private AppInfo mTempInfo = new AppInfo();


    private String TAG="AllAppsStore";

    public void setApps(AppInfo[] apps, int flags) {
        Log.d(TAG, "setApps");
        mApps = apps;
        mModelFlags = flags;
        notifyUpdate();
    }

    public AppInfo[] getApps() {
        return mApps;
    }
    public AppInfo getApp(ComponentKey key) {
        mTempInfo.componentName = key.componentName;
        mTempInfo.user = key.user;
        int index = Arrays.binarySearch(mApps, mTempInfo, COMPONENT_KEY_COMPARATOR);
        return index < 0 ? null : mApps[index];
    }

    private void notifyUpdate() {
        Log.d(TAG, "notifyUpdate");
        if (mDeferUpdatesFlags != 0) {
            mUpdatePending = true;
            return;
        }
        for (OnUpdateListener listener : mUpdateListeners) {
            listener.onAppsUpdated();
        }
    }

    public void updateNotificationDots(Predicate<PackageUserKey> updatedDots) {
        updateAllIcons((child) -> {
            if (child.getTag() instanceof ItemInfo) {
                ItemInfo info = (ItemInfo) child.getTag();
                if (mTempKey.updateFromItemInfo(info) && updatedDots.test(mTempKey)) {
                    child.applyDotState(info, true /* animate */);
                }
            }
        });
    }
    private void updateAllIcons(Consumer<BubbleTextView> action) {
        for (int i = mIconContainers.size() - 1; i >= 0; i--) {
            ViewGroup parent = mIconContainers.get(i);
            int childCount = parent.getChildCount();

            for (int j = 0; j < childCount; j++) {
                View child = parent.getChildAt(j);
                if (child instanceof BubbleTextView) {
                    action.accept((BubbleTextView) child);
                }
            }
        }
    }
    public void addUpdateListener(OnUpdateListener listener) {
        mUpdateListeners.add(listener);
    }

    public void registerIconContainer(ViewGroup container) {
        if (container != null) {
            mIconContainers.add(container);
        }
    }

    public void unregisterIconContainer(ViewGroup container) {
        mIconContainers.remove(container);
    }


    public interface OnUpdateListener {
        void onAppsUpdated();
    }

    public boolean hasModelFlag(int mask) {
        return (mModelFlags & mask) != 0;
    }
}
