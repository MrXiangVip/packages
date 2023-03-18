package com.example.camera.common;

import android.app.Activity;

import com.example.camera.loader.FeatureProvider;
import com.example.camera.relation.DataStore;

public class CameraContext implements ICameraContext{

    private Activity mActivity;
    private FeatureProvider mFeatureProvider;

    @Override
    public void create(IApp app, Activity activity) {
        mActivity = activity;
        mFeatureProvider = new FeatureProvider(app);

    }

    @Override
    public FeatureProvider getFeatureProvider() {
        return mFeatureProvider;

    }

    @Override
    public DataStore getDataStore() {
        return null;
    }
}
