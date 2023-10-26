package com.example.settings.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;

public interface BluetoothCallback {

    default void onDeviceAdded(BluetoothDevice cachedDevice) {}
}
