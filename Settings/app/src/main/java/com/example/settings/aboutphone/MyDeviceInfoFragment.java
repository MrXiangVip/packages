package com.example.settings.aboutphone;

import com.example.settings.DashboardFragment;
import com.example.settings.R;

public class MyDeviceInfoFragment extends DashboardFragment {
    private static final String LOG_TAG = "MyDeviceInfoFragment";

    @Override
    protected String getLogTag() {
        return LOG_TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.my_device_info;
    }
}
