package com.example.camera.common;

import android.app.Activity;

import com.example.camera.loader.FeatureProvider;
import com.example.camera.relation.DataStore;

public interface ICameraContext {

    void create( IApp app,  Activity activity);

    FeatureProvider getFeatureProvider();
    DataStore getDataStore();

}
