package com.example.settings.network;

import com.example.settings.DashboardFragment;
import com.example.settings.R;
public class NetworkDashboardFragment extends DashboardFragment{
    private static final String TAG = "NetworkDashboardFrag";

    protected String getLogTag() {
        return TAG;
    }

    protected int getPreferenceScreenResId() {
        return R.xml.network_and_internet;
    }

}
