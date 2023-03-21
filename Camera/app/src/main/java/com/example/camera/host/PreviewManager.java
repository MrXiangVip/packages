package com.example.camera.host;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.example.camera.common.IAppUi;
import com.example.camera.exposure.ExposureViewController;
import com.example.camerabg.R;
import com.example.camera.common.IApp;
import com.example.camera.common.PreviewFrameLayout;

public class PreviewManager implements GestureDetector.OnGestureListener {
    protected IAppUi mAppUi;
    private IApp mApp;
    private PreviewFrameLayout mPreviewFrameLayout;
    ExposureViewController mExposureViewController;
    private static final int EXPOSURE_VIEW_PRIORITY = 9;
    private String TAG="PreviewManager";

    public PreviewManager(IApp app) {
        mApp = app;

        mPreviewFrameLayout =
                (PreviewFrameLayout) mApp.getActivity().findViewById(R.id.preview_layout_container);


    }

    public void init( ){
        mExposureViewController = new ExposureViewController(mApp);
        mAppUi = mApp.getAppUi();

        mAppUi.registerGestureListener(this, EXPOSURE_VIEW_PRIORITY);

    }
    public PreviewFrameLayout getPreviewFrameLayout() {
        return mPreviewFrameLayout;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(TAG, "onScroll "+distanceX +" " +distanceY);
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling "+velocityX +" " +velocityY);

        return false;
    }
}
