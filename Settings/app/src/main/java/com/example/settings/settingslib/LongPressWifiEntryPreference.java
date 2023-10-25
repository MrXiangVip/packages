package com.example.settings.settingslib;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LongPressWifiEntryPreference extends WifiEntryPreference{

    private final Fragment mFragment;

    public LongPressWifiEntryPreference(Context context, ScanResult wifiEntry, Fragment fragment) {
        super(context, wifiEntry);
        mFragment = fragment;
    }
}
