package com.example.camera.loader;

import android.app.Activity;

import com.example.camera.common.IAppUi.ModeItem;
import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;

public interface IFeatureEntry {

    boolean isSupport(CameraApi currentCameraApi, Activity activity);
    Object createInstance();
    void setDeviceSpec( DeviceSpec deviceSpec);
    ModeItem getModeItem();

}
