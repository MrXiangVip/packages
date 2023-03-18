package com.example.camera.loader;

import android.content.Context;

import com.example.camera.mode.CameraApiHelper;
import com.example.camera.mode.photo.PhotoModeEntry;

import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

public class FeatureLoader {
    private static ConcurrentHashMap<String, IFeatureEntry>
            sBuildInEntries = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, IFeatureEntry>
            sPluginEntries = new ConcurrentHashMap<>();

    private static final String PHOTO_MODE = "com.mediatek.camera.common.mode.photo.PhotoModeEntry";

    public static ConcurrentHashMap<String, IFeatureEntry> loadBuildInFeatures(Context context) {
        if (sBuildInEntries.size() > 0) {
            return sBuildInEntries;
        }
//        IPerformanceProfile profile = PerformanceTracker.create(TAG,
//                "Build-in Loading");
//        profile.start();
        sBuildInEntries = new ConcurrentHashMap<>(loadClasses(context));
//        profile.stop();
        return sBuildInEntries;
    }
    private static LinkedHashMap<String, IFeatureEntry> loadClasses(Context context) {
        LinkedHashMap<String, IFeatureEntry> entries = new LinkedHashMap<>();
        DeviceSpec deviceSpec = CameraApiHelper.getDeviceSpec(context);


        IFeatureEntry photoEntry = new PhotoModeEntry(context, context.getResources());
        photoEntry.setDeviceSpec(deviceSpec);
        entries.put(PHOTO_MODE, photoEntry);
        return  entries;
    }

    public static ConcurrentHashMap<String, IFeatureEntry> loadPluginFeatures(
            final Context context) {
        return sPluginEntries;
    }
}
