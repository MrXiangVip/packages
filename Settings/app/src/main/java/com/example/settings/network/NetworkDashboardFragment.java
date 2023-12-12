package com.example.settings.network;

import android.os.Bundle;
import android.util.Log;

import com.example.settings.dashboard.DashboardFragment;
import com.example.settings.R;
public class NetworkDashboardFragment extends DashboardFragment{
    private static final String TAG = "NetworkDashboardFrag";

    protected String getLogTag() {
        return TAG;
    }

    protected int getPreferenceScreenResId() {
        return R.xml.network_and_internet;
    }

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
        Log.d(TAG, "onCreatePreference "+rootKey);
        super.onCreatePreferences(savedInstanceState, rootKey);
    }
}
