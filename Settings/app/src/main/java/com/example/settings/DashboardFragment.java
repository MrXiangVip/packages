package com.example.settings;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceFragmentCompat;

public abstract class DashboardFragment extends PreferenceFragmentCompat implements LifecycleOwner {
    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        final int resId = getPreferenceScreenResId();
        if (resId > 0) {
            addPreferencesFromResource(resId);
        }

    }
    protected abstract String getLogTag();

    protected abstract int getPreferenceScreenResId();

//    private void refreshAllPreferences(final String tag) {
//        final PreferenceScreen screen = getPreferenceScreen();
//        if (screen != null) {
//            // Intentionally do not cache PreferenceScreen because it will be recreated later.
//            screen.removeAll();
//        }
//    }

}