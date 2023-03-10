package com.example.camera.host;

import android.view.View;
import android.view.ViewGroup;

import com.example.camera.common.IApp;

public abstract class AbstractViewManager implements  IViewManager{
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
}
