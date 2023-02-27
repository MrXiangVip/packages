package com.example.settings;

import android.content.Context;

public class TopLevelSettings extends  DashboardFragment{

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
