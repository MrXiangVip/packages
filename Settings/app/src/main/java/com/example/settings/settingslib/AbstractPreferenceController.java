package com.example.settings.settingslib;

import android.content.Context;

public abstract  class AbstractPreferenceController {
    protected final Context mContext;

    public AbstractPreferenceController(Context context) {
        mContext = context;
    }

    public abstract String getPreferenceKey();

}
