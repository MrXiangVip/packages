package com.example.launcher.pm;

import static com.example.launcher.Utilities.getPrefs;

import android.content.Context;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInstaller;

import com.example.launcher.util.IntArray;
import com.example.launcher.util.IntSet;
import com.example.launcher.util.MainThreadInitializedObject;

public class InstallSessionHelper {
    private final PackageInstaller mInstaller;
    private final Context mAppContext;
    private final LauncherApps mLauncherApps;
    private final IntSet mPromiseIconIds;
    protected static final String PROMISE_ICON_IDS = "promise_icon_ids";

    public static final MainThreadInitializedObject<InstallSessionHelper> INSTANCE =
            new MainThreadInitializedObject<>(InstallSessionHelper::new);

    public InstallSessionHelper(Context context) {
        mInstaller = context.getPackageManager().getPackageInstaller();
        mAppContext = context.getApplicationContext();
        mLauncherApps = context.getSystemService(LauncherApps.class);

        mPromiseIconIds = IntSet.wrap(IntArray.fromConcatString(
                getPrefs(context).getString(PROMISE_ICON_IDS, "")));

//        cleanUpPromiseIconIds();
    }
}
