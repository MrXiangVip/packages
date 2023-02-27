package com.example.settings.deviceinfo;

import android.os.Bundle;

import com.example.settings.R;
import com.example.settings.core.InstrumentedPreferenceFragment;

public class StorageSettings extends InstrumentedPreferenceFragment {

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.device_info_storage);

    }
}