package com.example.camera.host;

import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;

import com.example.camera.common.RotateLayout;
import com.example.camerabg.R;
import com.example.camera.common.IApp;
import com.example.camera.common.IAppUi;

import java.util.ArrayList;
import java.util.List;

public class CameraAppUI implements IAppUi {
    private final IApp mApp;

    private final OnOrientationChangeListenerImpl mOrientationChangeListener;

    private final List<IViewManager> mViewManagers;

    private QuickSwitcherManager mQuickSwitcherManager;

    public CameraAppUI(IApp app) {
        mApp = app;
        mOrientationChangeListener = new OnOrientationChangeListenerImpl();
//        xshx add 20230204
//        mXdfAppUiGestureListener = new XdfAppUiGestureListener(mApp.getActivity());
        mViewManagers = new ArrayList<>();
    }

    public void onCreate() {
        ViewGroup rootView = (ViewGroup) mApp.getActivity().findViewById(R.id.app_ui_root);
        ViewGroup parentView = (ViewGroup) mApp.getActivity().getLayoutInflater().inflate( R.layout.camera_ui_root, rootView, true);
        View appUI = parentView.findViewById( R.id.camera_ui_root);


        mQuickSwitcherManager = new QuickSwitcherManager(mApp, parentView);
        mQuickSwitcherManager.setVisibility(View.VISIBLE);
//        mQuickSwitcherManager.setModeChangeListener(new OnQuickModeChangedListenerImpl());
        mViewManagers.add(mQuickSwitcherManager);

    }

    public void onResume() {
        RotateLayout root = (RotateLayout) mApp.getActivity().findViewById(R.id.app_ui);
        Configuration newConfig = mApp.getActivity().getResources().getConfiguration();
//        for (int count = 0; count < mViewManagers.size(); count ++) {
//            mViewManagers.get(count).onResume();
//        }
    }

    private  class  OnOrientationChangeListenerImpl implements  IApp.OnOrientationChangeListener{

        @Override
        public void onOrientationChanged(int orientation) {

        }
    }
}
