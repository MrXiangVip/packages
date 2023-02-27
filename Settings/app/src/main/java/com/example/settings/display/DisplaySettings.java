package com.example.settings.display;

import com.example.settings.DashboardFragment;
import com.example.settings.R;

public class DisplaySettings extends DashboardFragment {
    private static final String TAG = "DisplaySettings";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.display_settings;
    }
}
