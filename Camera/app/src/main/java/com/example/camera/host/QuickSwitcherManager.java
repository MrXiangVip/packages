package com.example.camera.host;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.camera.common.IApp;
import com.example.camerabg.R;

public class QuickSwitcherManager extends AbstractViewManager{

    private IApp mIApp;
    private View mTopBar;
    private ViewGroup mOptionRoot;
    private LinearLayout mQuickSwitcherLayout;

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
//        updateQuickItems();
        return mQuickSwitcherLayout;
    }
}
