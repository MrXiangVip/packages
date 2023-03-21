package com.example.camera.exposure;


import android.app.Activity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.camera.common.IApp;
import com.example.camerabg.R;

public class ExposureViewController {
    private Activity mActivity;

//    private Exposure mExposure;

    private ViewGroup mFeatureRootView;
    private RelativeLayout mExpandView;
    private ExposureView mExposureView;

    private String TAG="ExposureViewController";

    public ExposureViewController(final IApp app ) {
        mActivity = app.getActivity();
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                mExposure = exposure;
                mFeatureRootView = app.getAppUi().getPreviewFrameLayout();
                if (mFeatureRootView.findViewById(R.id.focus_view) == null) {
                    app.getActivity().getLayoutInflater().inflate(R.layout.focus_view,
                            mFeatureRootView, true);
                }
                mExpandView = (RelativeLayout) mFeatureRootView.findViewById(R.id.expand_view);
                Log.d(TAG, "ExposureViewController current EV = " + mFeatureRootView
                        .findViewById(R.id.exposure_view));
                if (mFeatureRootView.findViewById(R.id.exposure_view) == null) {
                    app.getActivity().getLayoutInflater().inflate(R
                                    .layout.exposure_view,
                            mExpandView, true);
                }
                mExposureView = (ExposureView) mFeatureRootView.findViewById(R.id.exposure_view);
                Log.d(TAG, "ExposureViewController mExposureView EV = " + mExposureView);
//                mExposureView.setListener(mExposure);
            }
        });
    }
}