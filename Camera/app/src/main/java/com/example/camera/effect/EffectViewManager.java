package com.example.camera.effect;

import android.view.View;
import android.view.ViewGroup;

import com.example.camera.common.IApp;
import com.example.camera.ui.AbstractViewManager;
import com.example.camerabg.R;

public class EffectViewManager extends AbstractViewManager {
    private ViewGroup mEffectViewGroup;

    public EffectViewManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
    }

    @Override
    protected View getView() {
        mEffectViewGroup = (ViewGroup) mParentView.findViewById(R.id.effect);
        return mEffectViewGroup;
    }
}
