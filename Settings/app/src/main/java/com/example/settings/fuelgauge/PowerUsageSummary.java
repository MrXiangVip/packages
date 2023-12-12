package com.example.settings.fuelgauge;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class PowerUsageSummary extends DashboardFragment {
    private static final String TAG = "PowerUsageBase";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.power_usage_summary;
    }
}
