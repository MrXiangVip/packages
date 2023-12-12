package com.example.settings.accounts;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class AccountDashboardFragment extends DashboardFragment {
    private static final String TAG = "AccountDashboardFrag";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.accounts_dashboard_settings;
    }
}
