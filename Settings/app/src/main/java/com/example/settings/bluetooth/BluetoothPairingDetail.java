package com.example.settings.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.companion.BluetoothDeviceFilter;
import android.os.Bundle;

import androidx.preference.PreferenceGroup;

import com.example.settings.R;

public class BluetoothPairingDetail extends DeviceListPreferenceFragment{

    public static final String TAG ="BluetoothPairingDetail ";
    BluetoothProgressCategory mAvailableDevicesCategory;
    static final String KEY_AVAIL_DEVICES = "available_devices";
    private boolean mInitialScanStarted;
    PreferenceGroup mDeviceListGroup;

    @Override
    protected String getLogTag() {
        return null;
    }

    protected int getPreferenceScreenResId() {
        return R.xml.bluetooth_pairing_detail;
    }


    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInitialScanStarted = false;

    }

    public void onStart() {
        super.onStart();
        updateBluetooth();

    }

    public void onResume() {
        final Activity activity = getActivity();
        super.onResume();
    }

    void updateBluetooth() {
        if (mBluetoothAdapter.isEnabled()) {
            updateContent(mBluetoothAdapter.getState());
        } else {
            // Turn on bluetooth if it is disabled
            mBluetoothAdapter.enable();

        }
    }
    void updateContent(int bluetoothState) {
        switch (bluetoothState) {
            case BluetoothAdapter.STATE_ON:
//                mBluetoothAdapter.enable();
                mAvailableDevicesCategory.setVisible(true);
                addDeviceCategory(mAvailableDevicesCategory,
                        R.string.bluetooth_preference_found_media_devices, mInitialScanStarted);
                enableScanning();
                break;
            case BluetoothAdapter.STATE_OFF:
                mAvailableDevicesCategory.setVisible(false);

                break;
        }
    }

    @Override
    void initPreferencesFromPreferenceScreen() {
        mAvailableDevicesCategory = (BluetoothProgressCategory) findPreference(KEY_AVAIL_DEVICES);

    }

    @Override
    public String getDeviceListKey() {
        return  KEY_AVAIL_DEVICES;
    }

    void enableScanning(){
        if( !mInitialScanStarted ){
            mInitialScanStarted = true;
        }
        super.enableScanning();
    }
}
