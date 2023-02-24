package com.example.settings.dashboard;

import android.content.Context;

import com.example.settings.SettingsPreferenceFragment;
import com.example.settings.core.BasePreferenceController;
import com.example.settings.core.PreferenceControllerListHelper;

import java.util.List;

public abstract class DashboardFragment extends SettingsPreferenceFragment {

    protected abstract int getPreferenceScreenResId();

    public void onAttach(Context context) {
        super.onAttach(context);

        final List<BasePreferenceController> controllersFromXml = PreferenceControllerListHelper
                .getPreferenceControllersFromXml(context, getPreferenceScreenResId());
    }
}
