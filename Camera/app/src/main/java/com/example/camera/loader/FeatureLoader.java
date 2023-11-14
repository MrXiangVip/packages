package com.example.camera.loader;

import android.content.Context;
import android.util.Log;

import com.example.camera.feature.setting.scenemode.SceneModeEntry;
import com.example.camera.feature.setting.whitebalance.WhiteBalanceEntry;
import com.example.camera.mode.CameraApiHelper;
import com.example.camera.mode.photo.PhotoModeEntry;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureLoader {
    private static ConcurrentHashMap<String, IFeatureEntry>
            sBuildInEntries = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, IFeatureEntry>
            sPluginEntries = new ConcurrentHashMap<>();

    private static final String PHOTO_MODE = "com.example.camera.mode.photo.PhotoModeEntry";
    private static final String SCENE_MODE = "com.example.camera.feature.setting.scenemode.SceneModeEntry";
    private static final String WHITE_BALANCE = "com.example.camera.feature.setting.whitebalance.WhiteBalanceEntry";
    private static String TAG="FeatureLoader.";

    public static ConcurrentHashMap<String, IFeatureEntry> loadBuildInFeatures(Context context) {
        if (sBuildInEntries.size() > 0) {
            return sBuildInEntries;
        }
        sBuildInEntries = new ConcurrentHashMap<>(loadClasses(context));
        return sBuildInEntries;
    }
    private static LinkedHashMap<String, IFeatureEntry> loadClasses(Context context) {
        LinkedHashMap<String, IFeatureEntry> entries = new LinkedHashMap<>();
        DeviceSpec deviceSpec = CameraApiHelper.getDeviceSpec(context);


        IFeatureEntry photoEntry = new PhotoModeEntry(context, context.getResources());
        photoEntry.setDeviceSpec(deviceSpec);
        entries.put(PHOTO_MODE, photoEntry);

        IFeatureEntry sceneModeEntry = new SceneModeEntry(context, context.getResources());
        sceneModeEntry.setDeviceSpec(deviceSpec);
        entries.put(SCENE_MODE, sceneModeEntry);

        IFeatureEntry whiteBalanceEntry = new WhiteBalanceEntry(context, context.getResources());
        whiteBalanceEntry.setDeviceSpec(deviceSpec);
        entries.put(WHITE_BALANCE, whiteBalanceEntry);

        return  entries;
    }

    public static ConcurrentHashMap<String, IFeatureEntry> loadPluginFeatures(
            final Context context) {
        return sPluginEntries;
    }

    public static void updateSettingCurrentModeKey( Context context, String currentModeKey) {
        Log.d(TAG, "[updateCurrentModeKey] current mode key:" + currentModeKey);
        if (sBuildInEntries.size() <= 0) {
            loadBuildInFeatures(context);
        }
    }
}
