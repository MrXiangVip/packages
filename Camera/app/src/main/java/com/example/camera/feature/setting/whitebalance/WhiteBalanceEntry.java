package com.example.camera.feature.setting.whitebalance;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.example.camera.device.CameraDeviceManagerFactory;
import com.example.camera.loader.FeatureEntryBase;

public class WhiteBalanceEntry extends FeatureEntryBase {
    public WhiteBalanceEntry(Context context, Resources resources) {
        super(context, resources);
    }

    @Override
    public boolean isSupport(CameraDeviceManagerFactory.CameraApi currentCameraApi, Activity activity) {
        return false;
    }

    @Override
    public Object createInstance() {
        return null;
    }
}
