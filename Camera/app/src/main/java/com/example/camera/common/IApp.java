package com.example.camera.common;

import android.app.Activity;

public interface IApp {

    Activity getActivity();

    IAppUi getAppUi();

    interface OnOrientationChangeListener {
        /**
         * Notify the new orientation value, the value will be one of 0,90,180,270.
         * @param orientation The changed orientation value.
         */
        void onOrientationChanged(int orientation);
    }
}
