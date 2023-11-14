package com.example.camera;

import android.preference.PreferenceFragment;

public interface ICameraSettingView {
    void loadView(PreferenceFragment fragment);
    boolean isEnabled();

}
