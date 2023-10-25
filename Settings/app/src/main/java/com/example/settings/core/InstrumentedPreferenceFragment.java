package com.example.settings.core;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.XmlRes;
import androidx.preference.PreferenceFragmentCompat;

public abstract  class InstrumentedPreferenceFragment extends PreferenceFragmentCompat {

    protected int getPreferenceScreenResId() {
        return -1;
    }

    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        final int resId = getPreferenceScreenResId();
        if (resId > 0) {
            addPreferencesFromResource(resId);
        }
    }

    protected final Context getPrefContext() {
        return getPreferenceManager().getContext();
    }

    public void addPreferencesFromResource(@XmlRes int preferencesResId) {
        super.addPreferencesFromResource(preferencesResId);
//        updateActivityTitleWithScreenTitle(getPreferenceScreen());
    }
}
