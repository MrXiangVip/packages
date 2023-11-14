package com.example.camera.mode;

import com.example.camera.common.IApp;
import com.example.camera.common.ICameraContext;
import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;
import com.example.camera.relation.DataStore;

public interface ICameraMode {

    String MODE_DEVICE_STATE_UNKNOWN = "unknown";
    String MODE_DEVICE_STATE_OPENED = "opened";
    String MODE_DEVICE_STATE_CLOSED = "closed";
    String MODE_DEVICE_STATE_PREVIEWING = "previewing";
    String MODE_DEVICE_STATE_CAPTURING = "capturing";
    String MODE_DEVICE_STATE_RECORDING = "recording";

    enum ModeType {
        PHOTO,
        VIDEO,
    }
    void init(IApp app, ICameraContext cameraContext, boolean isFromLaunch);

    CameraApi getCameraApi();
    String getModeKey();
    DeviceUsage getDeviceUsage(DataStore dataStore, DeviceUsage oldDeviceUsage);

}
