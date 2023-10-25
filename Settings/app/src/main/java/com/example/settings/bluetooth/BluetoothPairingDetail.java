package com.example.settings.bluetooth;

import com.example.settings.R;

public class BluetoothPairingDetail extends DeviceListPreferenceFragment{

    public static final String TAG ="BluetoothPairingDetail ";
    @Override
    protected String getLogTag() {
        return null;
    }

    protected int getPreferenceScreenResId() {
        return R.xml.bluetooth_pairing_detail;
    }

}
