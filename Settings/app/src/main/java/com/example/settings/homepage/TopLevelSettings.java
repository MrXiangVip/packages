package com.example.settings.homepage;

import android.content.Context;

import com.example.settings.R;
import com.example.settings.dashboard.DashboardFragment;

public class TopLevelSettings extends DashboardFragment {

    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    protected String getLogTag() {
        return null;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.top_level_settings;

    }
}
