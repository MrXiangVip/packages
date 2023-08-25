package com.example.launcher;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.launcher.icons.IconCache;
import com.example.launcher.util.AppFilter;
import com.example.launcher.util.MainThreadInitializedObject;
import com.example.launcher.widget.WidgetPreviewLoader;

public class LauncherAppState {

    private final LauncherModel mModel;
    private final Context mContext;

    private final IconCache mIconCache;
    private final WidgetPreviewLoader mWidgetCache;

    private final InvariantDeviceProfile mInvariantDeviceProfile;

    public static final MainThreadInitializedObject<LauncherAppState> INSTANCE =
            new MainThreadInitializedObject<>(LauncherAppState::new);

    private String TAG="LauncherAppState";

    public static LauncherAppState getInstance(final Context context) {

        return INSTANCE.get(context);
    }
    public LauncherAppState(Context context) {
        this(context, LauncherFiles.APP_ICONS_DB);
    }
    public LauncherAppState(Context context, @Nullable String iconCacheFileName) {
        mContext = context;
        mInvariantDeviceProfile = InvariantDeviceProfile.INSTANCE.get(context);
        mIconCache = new IconCache(mContext, mInvariantDeviceProfile, iconCacheFileName);
        mWidgetCache = new WidgetPreviewLoader(mContext, mIconCache);
        mModel = new LauncherModel(this, mIconCache, AppFilter.newInstance(mContext));
    }
    public LauncherModel getModel() {
        Log.d(TAG, "getModel");
        return mModel;
    }
    public Context getContext() {
        return mContext;
    }

    public IconCache getIconCache() {
        return mIconCache;
    }

    public InvariantDeviceProfile getInvariantDeviceProfile() {
        return mInvariantDeviceProfile;
    }
}
