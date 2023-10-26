package com.example.settings.bluetooth;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

import com.example.settings.R;

public class BluetoothDevicePreference extends Preference {
    public BluetoothDevicePreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BluetoothDevicePreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BluetoothDevicePreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BluetoothDevicePreference(@NonNull Context context) {
        super(context, null);
        setLayoutResource(R.layout.bluetooth_device_preference);

    }

}
