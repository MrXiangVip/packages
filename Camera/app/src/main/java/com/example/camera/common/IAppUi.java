package com.example.camera.common;

import android.view.GestureDetector;

import java.util.List;

public interface IAppUi {




    class ModeItem {

    }

    void setModeChangeListener(IAppUiListener.OnModeChangeListener listener);
    void registerMode(List<ModeItem> items);
    void updateCurrentMode(String mode);
    PreviewFrameLayout getPreviewFrameLayout();

    void registerGestureListener(GestureDetector.OnGestureListener listener, int priority);

}
