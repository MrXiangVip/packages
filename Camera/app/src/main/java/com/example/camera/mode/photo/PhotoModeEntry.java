package com.example.camera.mode.photo;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.example.camera.common.IAppUi;
import com.example.camera.device.CameraDeviceManagerFactory;
import com.example.camera.loader.FeatureEntryBase;

public class PhotoModeEntry extends FeatureEntryBase {
    public PhotoModeEntry(Context context, Resources resources) {
        super(context, resources);
    }

    @Override
    public boolean isSupport(CameraDeviceManagerFactory.CameraApi currentCameraApi, Activity activity) {
        return true;
    }

    @Override
    public Object createInstance() {
        return new PhotoMode();

    }


}
