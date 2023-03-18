package com.example.camera.mode;

import android.content.Context;

import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;
import com.example.camera.loader.DeviceDescription;
import com.example.camera.loader.DeviceSpec;

import java.util.concurrent.ConcurrentHashMap;

public class CameraApiHelper {
    private static DeviceSpec sDeviceSpec = new DeviceSpec();
    private static Boolean sForceCreate = false;

    public static DeviceSpec getDeviceSpec(Context context) {
        createDeviceSpec(context);
        return sDeviceSpec;
    }

    public static CameraApi getCameraApiType( String modeName) {
        return CameraApi.API2;
    }

    private static void createDeviceSpec(Context context) {
        if (!sForceCreate && sDeviceSpec.getDefaultCameraApi() != null) {
            return;
        }

        CameraApi defaultCameraApi = getCameraApiType(null);
        ConcurrentHashMap<String, DeviceDescription>
                deviceDescriptionMap = new ConcurrentHashMap<>();

        sDeviceSpec.setDefaultCameraApi(defaultCameraApi);
        sDeviceSpec.setDeviceDescriptions(deviceDescriptionMap);
    }
}