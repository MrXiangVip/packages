package com.example.camera.mode;

import com.example.camera.common.IApp;
import com.example.camera.common.ICameraContext;
import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;
import com.example.camera.relation.DataStore;

public abstract class CameraModeBase implements  ICameraMode{
    protected CameraApi mCameraApi;
    protected IApp mIApp;
    protected ICameraContext mICameraContext;

    public void init(IApp app,  ICameraContext cameraContext,
                     boolean isFromLaunch) {
        mIApp = app;
        mICameraContext = cameraContext;

    }
    @Override
    public CameraApi getCameraApi() {
        updateModeDefinedCameraApi();
        return mCameraApi;

    }
    protected void updateModeDefinedCameraApi() {
        if (mCameraApi == null) {
            mCameraApi = CameraApiHelper.getCameraApiType(getClass().getSimpleName());
        }
    }
    @Override
    public String getModeKey() {
        return getClass().getName();
    }

    @Override
    public DeviceUsage getDeviceUsage(DataStore dataStore, DeviceUsage oldDeviceUsage) {
        return null;
    }
}
