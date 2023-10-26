package com.example.settings;

import android.content.Context;

import com.example.settings.settingslib.bluetooth.LocalBluetoothManager;

public class Utils {

    public static LocalBluetoothManager getLocalBtManager(Context context) {
        return LocalBluetoothManager.getInstance(context);
    }
}
