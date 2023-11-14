package com.example.camera.preview;

import android.view.View;
import com.example.camera.common.IAppUiListener.ISurfaceStatusListener;

public interface IController {
    View getView();

    void updatePreviewSize(int width, int height, ISurfaceStatusListener listener);

    void setOnTouchListener(View.OnTouchListener onTouchListener);

    void setOnLayoutChangeListener(View.OnLayoutChangeListener layoutChangeListener);

}
