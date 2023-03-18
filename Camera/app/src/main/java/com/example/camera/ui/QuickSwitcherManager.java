package com.example.camera.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.camera.common.IApp;
import com.example.camerabg.R;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class QuickSwitcherManager extends AbstractViewManager {

    private IApp mIApp;
    private View mTopBar;
    private View mModeView;
    private ViewGroup mOptionRoot;
    private LinearLayout mQuickSwitcherLayout;
    private static final int MARGIN_IN_DP = 50;
    private ConcurrentSkipListMap<Integer, View> mQuickItems = new ConcurrentSkipListMap<>();

    public QuickSwitcherManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
        mIApp = app;
        mTopBar = app.getActivity().findViewById(R.id.top_bar);
        mOptionRoot = (ViewGroup) mApp.getActivity().findViewById(R.id.quick_switcher_option);
//        mOrientationChangeListener = new OnOrientationChangeListenerImpl();
//        mVisualSearchSettingViewController= new VisualSearchSettingViewController(mIApp);
    }

    protected View getView() {
        mQuickSwitcherLayout = (LinearLayout) mParentView.findViewById(R.id.quick_switcher);
        updateQuickItems();
        return mQuickSwitcherLayout;
    }
    private void updateQuickItems() {
        float density = mApp.getActivity().getResources().getDisplayMetrics().density;
        int marginInPix = (int) (MARGIN_IN_DP * density);
        int imageSize = (int) (50 * density);
        if (mQuickSwitcherLayout != null ) {
            mModeView = mQuickSwitcherLayout.findViewById(R.id.mode);
            mQuickSwitcherLayout.removeAllViews();
        }
        if (mQuickSwitcherLayout != null) {
            Iterator iterator = mQuickItems.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry map = (Map.Entry) iterator.next();
                View view = (View) map.getValue();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        imageSize,
                        imageSize);
                params.setMargins(marginInPix, 0, 0, 0);
                view.setLayoutParams(params);
                mQuickSwitcherLayout.addView(view);
            }
        }
        if (mModeView != null){
            mQuickSwitcherLayout.addView(mModeView);
        }
        updateViewOrientation();
    }

    public void updateViewOrientation() {
//        int orientation = mApp.getGSensorOrientation();
//        CameraUtil.rotateRotateLayoutChildView(mApp.getActivity(), mView, orientation, false);
    }
}
