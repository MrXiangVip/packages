package com.example.settings.core;

import android.content.Context;

import com.example.settings.settingslib.ObservablePreferenceFragment;

public abstract class InstrumentedPreferenceFragment extends ObservablePreferenceFragment {

    public void onAttach(Context context) {
//        mMetricsFeatureProvider = FeatureFactory.getFactory(context).getMetricsFeatureProvider();
//        // Mixin that logs visibility change for activity.
//        mVisibilityLoggerMixin = new VisibilityLoggerMixin(getMetricsCategory(),
//                mMetricsFeatureProvider);
//        getSettingsLifecycle().addObserver(mVisibilityLoggerMixin);
//        getSettingsLifecycle().addObserver(new SurveyMixin(this, getClass().getSimpleName()));
        super.onAttach(context);
    }
}
