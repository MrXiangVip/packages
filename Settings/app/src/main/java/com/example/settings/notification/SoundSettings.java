package com.example.settings.notification;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class SoundSettings extends DashboardFragment {
    private static final String TAG = "SoundSettings";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.sound_settings;
    }
}
