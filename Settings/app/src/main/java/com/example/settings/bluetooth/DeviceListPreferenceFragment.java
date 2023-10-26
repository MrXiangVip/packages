package com.example.settings.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import androidx.preference.PreferenceGroup;

import com.example.settings.Utils;
import com.example.settings.dashboard.RestrictedDashboardFragment;
import com.example.settings.settingslib.bluetooth.BluetoothCallback;
import com.example.settings.settingslib.bluetooth.LocalBluetoothManager;

import java.util.Collection;

public abstract class DeviceListPreferenceFragment extends RestrictedDashboardFragment implements BluetoothCallback {
    BluetoothAdapter mBluetoothAdapter;
    PreferenceGroup mDeviceListGroup;
    LocalBluetoothManager mLocalManager;

    abstract void initPreferencesFromPreferenceScreen();

    public abstract String getDeviceListKey();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocalManager = Utils.getLocalBtManager(getActivity());

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        initPreferencesFromPreferenceScreen();
    }

    public void onStart() {
        super.onStart();
        mLocalManager.registerCallback(this);
    }

    public void addDeviceCategory(PreferenceGroup preferenceGroup, int titleId, boolean addCachedDevices) {
//        cacheRemoveAllPrefs(preferenceGroup);
        preferenceGroup.setTitle(titleId);
        mDeviceListGroup = preferenceGroup;
        if (addCachedDevices) {
            // Don't show bonded devices when screen turned back on
//            setFilter(BluetoothDeviceFilter.UNBONDED_DEVICE_FILTER);
            addCachedDevices();
        }
//        setFilter(filter);
        preferenceGroup.setEnabled(true);
//        removeCachedPrefs(preferenceGroup);
    }

    void addCachedDevices() {
//        Collection<CachedBluetoothDevice> cachedDevices =
//                mLocalManager.getCachedDeviceManager().getCachedDevicesCopy();

    }

    void enableScanning() {
        startScanning();

    }

    void startScanning() {
        if (!mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.startDiscovery();
        }
    }

    public void onDeviceAdded(BluetoothDevice cachedDevice) {
        createDevicePreference(cachedDevice);

    }

    void createDevicePreference(BluetoothDevice cachedDevice) {
        String key = cachedDevice.getAddress();
        BluetoothDevicePreference preference = (BluetoothDevicePreference) mDeviceListGroup.findPreference(key);

        if (preference == null) {
            preference = new BluetoothDevicePreference(getPrefContext());
            preference.setKey(key);
            preference.setTitle( cachedDevice.getAlias() );
            preference.setSummary( cachedDevice.getAddress() );
//            preference.setOnGearClickListener(mDeviceProfilesListener);
            //Set hideSecondTarget is true if it's bonded device.
//            preference.hideSecondTarget(true);
            mDeviceListGroup.addPreference(preference);
        }

    }
}