package com.example.camera.host;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.example.camera.common.IAppUi;
import com.example.camera.exposure.ExposureView;
import com.example.camera.exposure.ExposureViewController;
import com.example.camera.focus.FocusView;
import com.example.camera.preview.IController;
import com.example.camera.preview.PreviewTextureView;
import com.example.camera.preview.TextureViewController;
import com.example.camera.ui.AbstractViewManager;
import com.example.camerabg.R;
import com.example.camera.common.IApp;
import com.example.camera.common.PreviewFrameLayout;
import com.example.camera.common.IAppUiListener.ISurfaceStatusListener;

public class PreviewManager extends AbstractViewManager  {
    protected IAppUi mAppUi;
    private IApp mApp;
    private Activity mActivity;

    private PreviewFrameLayout mPreviewFrameLayout;

    private FocusView mFocusView;
    private RelativeLayout mExpandView;
    private ExposureView mExposureView;
    private int mPreviewWidth = 0;
    private int mPreviewHeight = 0;
    ExposureViewController mExposureViewController;
    private static final int EXPOSURE_VIEW_PRIORITY = 9;
    private IController mPreviewController;

    private String TAG="PreviewManager";

    private View.OnTouchListener mOnTouchListener;
    private View.OnTouchListener mOnTouchListenerImpl = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (mOnTouchListener != null) {
                return mOnTouchListener.onTouch(v, event);
            } else {
                return false;
            }
        }
    };

    public PreviewManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
        mApp = app;
        mActivity = app.getActivity();

        mPreviewFrameLayout =
                (PreviewFrameLayout) mApp.getActivity().findViewById(R.id.preview_layout_container);
        mPreviewController = new TextureViewController(app);
        mPreviewController.setOnLayoutChangeListener(mOnLayoutChangeCallback);
        mPreviewController.setOnTouchListener(mOnTouchListenerImpl);

    }

    public void setOnTouchListener(View.OnTouchListener listener) {
        mOnTouchListener = listener;
    }

    protected View getView() {
//        addFocusView();
//       return  ((TextureViewController)mPreviewController).attachTextureView();
        return mPreviewController.getView();
    }

    public void init( ){
        mExposureViewController = new ExposureViewController(mApp);
        mAppUi = mApp.getAppUi();

    }
    public PreviewFrameLayout getPreviewFrameLayout() {
        return mPreviewFrameLayout;
    }

    protected void addFocusView() {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // focus view may inflate by other instance or exposure view.
                mPreviewFrameLayout = mApp.getAppUi().getPreviewFrameLayout();
                if (mPreviewFrameLayout.findViewById(R.id.focus_view) == null) {
                    mApp.getActivity().getLayoutInflater().inflate(R.layout.focus_view,
                            mPreviewFrameLayout, true);
                }
                mExpandView = (RelativeLayout) mPreviewFrameLayout.findViewById(R.id.expand_view);
                Log.d(TAG, "ExposureViewController current EV = " + mPreviewFrameLayout
                        .findViewById(R.id.exposure_view));
                if (mPreviewFrameLayout.findViewById(R.id.exposure_view) == null) {
                    mApp.getActivity().getLayoutInflater().inflate(R
                                    .layout.exposure_view,
                            mExpandView, true);
                }
                mExposureView = (ExposureView) mPreviewFrameLayout.findViewById(R.id.exposure_view);
                Log.d(TAG, "ExposureViewController mExposureView EV = " + mExposureView);
                setFocusLocation(mPreviewFrameLayout.getWidth() /2, mPreviewFrameLayout.getHeight() /2);
            }
        });
    }

    private void setFocusLocation(int x, int y) {
        if (mFocusView == null) {
            return;
        }
        // Use margin to set the focus indicator to the touched area.
        FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) mFocusView.getLayoutParams();
        int left = 0;
        int top = 0;
        left = x - mFocusView.getWidth() / 2;
        top = y - mFocusView.getHeight() / 2;
        p.setMargins(left, top, 0, 0);
        mFocusView.requestLayout();
    }

    public void updatePreviewSize(int width, int height, ISurfaceStatusListener listener) {
        Log.i(TAG, "updatePreviewSize: new size (" + width + " , " + height + " )"
                + " current size (" + mPreviewWidth + " , " + mPreviewHeight + " )");
        mPreviewWidth = width;
        mPreviewHeight = height;
        if (mPreviewController != null) {
            mPreviewController.updatePreviewSize(width, height, listener);
        }
    }

    private View.OnLayoutChangeListener mOnLayoutChangeCallback = new View.OnLayoutChangeListener(){

        @Override
        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        }
    };
}
