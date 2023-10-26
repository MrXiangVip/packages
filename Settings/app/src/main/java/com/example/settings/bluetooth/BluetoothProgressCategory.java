package com.example.settings.bluetooth;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.PreferenceCategory;

public class BluetoothProgressCategory extends PreferenceCategory {
    public BluetoothProgressCategory(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public BluetoothProgressCategory(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BluetoothProgressCategory(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BluetoothProgressCategory(@NonNull Context context) {
        super(context);
    }
}
