package com.example.settings.privacy;

import com.example.settings.DashboardFragment;
import com.example.settings.R;

public class PrivacyDashboardFragment   extends DashboardFragment {
    private static final String TAG = "PrivacyDashboardFrag";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return  R.xml.privacy_dashboard_settings;
    }
}
