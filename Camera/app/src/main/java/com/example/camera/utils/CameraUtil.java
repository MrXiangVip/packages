package com.example.camera.utils;

import android.app.Activity;
import android.util.Log;

import com.example.camera.loader.FeatureLoader;

public final  class CameraUtil {

    private static String TAG="CameraUtil.";

    public static void launchCamera(Activity activity) {
        Log.d(TAG, "launchCamera");
        String defaultMode = "com.example.camera.mode.photo.PhotoMode";

        FeatureLoader.updateSettingCurrentModeKey(activity, defaultMode);

    }
}