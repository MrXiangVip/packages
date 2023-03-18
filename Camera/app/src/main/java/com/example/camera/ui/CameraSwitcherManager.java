package com.example.camera.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.camera.common.IApp;
import com.example.camerabg.R;

public class CameraSwitcherManager extends AbstractViewManager {
    private LinearLayout mCameraSwitcherLayout;


    public CameraSwitcherManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
    }

    @Override
    protected View getView() {
        mCameraSwitcherLayout = (LinearLayout) mParentView.findViewById(R.id.camera_switcher_button);
        updateCameraItems();
        return mCameraSwitcherLayout;
    }

    private void updateCameraItems() {
        View switcher = mApp.getActivity().getLayoutInflater().inflate(R.layout.camera_switcher, null);
        mCameraSwitcherLayout.addView(switcher);

    }
}