package com.example.launcher;

import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;

import com.example.launcher.graphics.GridOptionsProvider;

import java.util.Arrays;

public class Utilities {

    public static SharedPreferences getPrefs(Context context) {
        // Use application context for shared preferences, so that we use a single cached instance
        return context.getApplicationContext().getSharedPreferences(
                LauncherFiles.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public static int boundToRange(int value, int lowerBound, int upperBound) {
        return Math.max(lowerBound, Math.min(value, upperBound));
    }

    public static int calculateTextHeight(float textSizePx) {
        Paint p = new Paint();
        p.setTextSize(textSizePx);
        Paint.FontMetrics fm = p.getFontMetrics();
        return (int) Math.ceil(fm.bottom - fm.top);
    }
    public static boolean isGridOptionsEnabled(Context context) {
        return isComponentEnabled(context.getPackageManager(),
                context.getPackageName(),
                GridOptionsProvider.class.getName());
    }
    private static boolean isComponentEnabled(PackageManager pm, String pkgName, String clsName) {
        ComponentName componentName = new ComponentName(pkgName, clsName);
        int componentEnabledSetting = pm.getComponentEnabledSetting(componentName);

        switch (componentEnabledSetting) {
            case PackageManager.COMPONENT_ENABLED_STATE_DISABLED:
                return false;
            case PackageManager.COMPONENT_ENABLED_STATE_ENABLED:
                return true;
            case PackageManager.COMPONENT_ENABLED_STATE_DEFAULT:
            default:
                // We need to get the application info to get the component's default state
                try {
                    PackageInfo packageInfo = pm.getPackageInfo(pkgName,
                            PackageManager.GET_PROVIDERS | PackageManager.GET_DISABLED_COMPONENTS);

                    if (packageInfo.providers != null) {
                        return Arrays.stream(packageInfo.providers).anyMatch(
                                pi -> pi.name.equals(clsName) && pi.isEnabled());
                    }

                    // the component is not declared in the AndroidManifest
                    return false;
                } catch (PackageManager.NameNotFoundException e) {
                    // the package isn't installed on the device
                    return false;
                }
        }
    }

}
