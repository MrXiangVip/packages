package com.example.settings.security;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class SecuritySettings extends DashboardFragment {
    private static final String TAG = "SecuritySettings";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.security_dashboard_settings;
    }
}
