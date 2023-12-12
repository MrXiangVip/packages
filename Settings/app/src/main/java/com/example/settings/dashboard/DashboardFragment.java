package com.example.settings.dashboard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.PreferenceFragmentCompat;

import com.example.settings.core.BasePreferenceController;
import com.example.settings.core.PreferenceControllerListHelper;
import com.example.settings.settingslib.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;

public abstract class DashboardFragment extends PreferenceFragmentCompat implements LifecycleOwner {

    private final List<AbstractPreferenceController> mControllers = new ArrayList<>();

    public void onAttach(Context context) {
        super.onAttach(context);
        final List<AbstractPreferenceController> controllersFromCode =
                createPreferenceControllers(context);

        final List<BasePreferenceController> controllersFromXml = PreferenceControllerListHelper
                .getPreferenceControllersFromXml(context, getPreferenceScreenResId());
        // Filter xml-based controllers in case a similar controller is created from code already.
        final List<BasePreferenceController> uniqueControllerFromXml =
                PreferenceControllerListHelper.filterControllers(
                        controllersFromXml, controllersFromCode);

        // Add unique controllers to list.
        if (controllersFromCode != null) {
            mControllers.addAll(controllersFromCode);
        }
        mControllers.addAll(uniqueControllerFromXml);


    }
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        final int resId = getPreferenceScreenResId();
        if (resId > 0) {
            addPreferencesFromResource(resId);
        }

    }
    protected abstract String getLogTag();

    protected abstract int getPreferenceScreenResId();

    protected List<AbstractPreferenceController> createPreferenceControllers(Context context) {
        return null;
    }

    protected final Context getPrefContext() {
        return getPreferenceManager().getContext();
    }

//    private void refreshAllPreferences(final String tag) {
//        final PreferenceScreen screen = getPreferenceScreen();
//        if (screen != null) {
//            // Intentionally do not cache PreferenceScreen because it will be recreated later.
//            screen.removeAll();
//        }
//    }

}