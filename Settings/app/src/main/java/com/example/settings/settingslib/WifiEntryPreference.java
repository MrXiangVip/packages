package com.example.settings.settingslib;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

import com.example.settings.R;

public class WifiEntryPreference extends Preference {

    private ScanResult mWifiEntry;
    private Context mContext;

    public WifiEntryPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WifiEntryPreference(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WifiEntryPreference(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WifiEntryPreference(@NonNull Context context) {
        super(context);
    }
    public WifiEntryPreference(@NonNull Context context, @NonNull ScanResult wifiEntry) {
        super(context );
        mWifiEntry = wifiEntry;
        setLayoutResource(R.layout.preference_access_point);

    }
}
