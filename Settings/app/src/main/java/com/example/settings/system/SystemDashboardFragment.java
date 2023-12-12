package com.example.settings.system;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class SystemDashboardFragment extends DashboardFragment {
    private static final String TAG = "SystemDashboardFrag";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.system_dashboard_fragment;
    }
}
