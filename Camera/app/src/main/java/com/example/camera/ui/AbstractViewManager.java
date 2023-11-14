package com.example.camera.ui;

import android.view.View;
import android.view.ViewGroup;

import com.example.camera.common.IApp;
import com.example.camera.host.IViewManager;

public abstract class AbstractViewManager implements IViewManager {
    protected final IApp mApp;
    protected final ViewGroup mParentView;
    private View mView;
//    private final OnOrientationChangeListenerImpl mOrientationChangeListener;
    public AbstractViewManager(IApp app, ViewGroup parentView) {
        mApp = app;
        mParentView = parentView;
//        mOrientationChangeListener = new OnOrientationChangeListenerImpl();
    }

    public void setVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
            show();
        } else if (visibility == View.INVISIBLE) {
            hide(View.INVISIBLE);
        } else if (visibility == View.GONE) {
            hide(View.GONE);
        }
    }

    public int getVisibility() {
        if (mView != null) {
            return mView.getVisibility();
        }
        return -1;
    }

    protected abstract View getView();

    private void show() {
        if (mView == null) {
            mView = getView();
        }
        if (mView != null) {
            mView.setVisibility(View.VISIBLE);
            mView.setClickable(true);
        }
    }

    private void hide(int visibility) {
        if (mView == null) {
            mView = getView();
        }
        if (mView != null) {
            mView.setVisibility(visibility);
        }
    }
    public void onCreate() {

    }
    public void onResume() {

    }

}
