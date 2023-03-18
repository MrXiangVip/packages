package com.example.camera.loader;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;

import com.example.camera.common.IAppUi;
import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;

abstract public class FeatureEntryBase implements IFeatureEntry{
    protected Context mContext;
    protected Resources mResources;
    protected DeviceSpec mDeviceSpec;
    protected CameraApi mDefaultCameraApi;

    public FeatureEntryBase(Context context, Resources resources) {
        if (context instanceof Activity) {
            mContext = ((Activity) context).getApplicationContext();
        } else {
            mContext = context;
        }

        mResources = mContext.getResources();
    }

    public void setDeviceSpec( DeviceSpec deviceSpec) {
        mDeviceSpec = deviceSpec;
        mDefaultCameraApi = mDeviceSpec.getDefaultCameraApi();
    }
    public IAppUi.ModeItem getModeItem() {
        return null;
    }

}
