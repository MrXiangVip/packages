package com.example.camera.loader;

import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;

import java.util.concurrent.ConcurrentHashMap;

public class DeviceSpec {
    private CameraApi mDefaultCameraApi;
    private ConcurrentHashMap<String, DeviceDescription> mDeviceDescriptions;

    public void setDefaultCameraApi( CameraApi cameraApi) {
        mDefaultCameraApi = cameraApi;
    }

    public CameraApi getDefaultCameraApi() {
        return mDefaultCameraApi;
    }

    public void setDeviceDescriptions(
             ConcurrentHashMap<String, DeviceDescription> cameraDescriptions) {
        mDeviceDescriptions = cameraDescriptions;
    }

    public ConcurrentHashMap<String, DeviceDescription> getDeviceDescriptionMap() {
        return mDeviceDescriptions;
    }
}
