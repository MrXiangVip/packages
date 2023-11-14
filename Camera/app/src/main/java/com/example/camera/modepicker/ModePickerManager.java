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
    private OnModeChangedListener mModeChangeListener;
    private View.OnClickListener mSettingClickedListener;

    public interface OnModeChangedListener {
        /**
         * Notify the new mode info.
         * @param modeName The selected mode item.
         */
        void onModeChanged(String modeName);
    }

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

    public void setModeChangeListener(OnModeChangedListener listener) {
        mModeChangeListener = listener;
    }

    public void setSettingClickedListener(View.OnClickListener listener) {
        mSettingClickedListener = listener;
    }
}
