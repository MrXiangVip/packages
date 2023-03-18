package com.example.camera.modepicker;

import android.view.View;
import android.view.ViewGroup;

import com.example.camera.common.IApp;
import com.example.camera.common.RotateImageView;
import com.example.camera.ui.AbstractViewManager;
import com.example.camerabg.R;

public class ModePickerManager extends AbstractViewManager {
    private final IApp mApp;
    private RotateImageView mModePickerView;

    public ModePickerManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
        mApp = app;

    }

    @Override
    protected View getView() {
        mModePickerView = (RotateImageView) mParentView
                .findViewById(R.id.mode);
        return mModePickerView;
    }
}
