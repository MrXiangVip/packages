package com.example.camera.feature.setting.scenemode;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.example.camera.device.CameraDeviceManagerFactory;
import com.example.camera.loader.FeatureEntryBase;

public class SceneModeEntry extends FeatureEntryBase {

    public SceneModeEntry(Context context, Resources resources) {
        super(context, resources);
    }

    @Override
    public boolean isSupport(CameraDeviceManagerFactory.CameraApi currentCameraApi, Activity activity) {
        return false;
    }

    @Override
    public Object createInstance() {
        return new SceneMode();
    }
}
