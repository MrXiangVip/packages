package com.example.settings.applications;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class AppAndNotificationDashboardFragment extends DashboardFragment {
    private static final String TAG = "AppAndNotifDashboard";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.app_and_notification;
    }
}
