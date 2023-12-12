package com.example.settings.connecteddevice;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;

public class ConnectedDeviceDashboardFragment extends DashboardFragment {
    private static final String TAG = "ConnectedDeviceFrag";

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.connected_devices;
    }
}
