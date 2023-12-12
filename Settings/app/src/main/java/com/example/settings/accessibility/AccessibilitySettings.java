package com.example.settings.accessibility;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class AccessibilitySettings extends DashboardFragment {
    private static final String TAG = "AccessibilitySettings";

    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.accessibility_settings;
    }
}
