package com.example.camera.shutter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.camera.common.IApp;
import com.example.camera.ui.AbstractViewManager;
import com.example.camerabg.R;

public class ShutterButtonManager extends AbstractViewManager {

    private LayoutInflater mInflater;
    private ShutterRootLayout mShutterLayout;
    private String TAG="ShutterButtonManager";

    public ShutterButtonManager(IApp app, ViewGroup parentView) {
        super(app, parentView);
        mInflater = (LayoutInflater) app.getActivity()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    protected View getView() {
        mShutterLayout = (ShutterRootLayout) mApp.getActivity().findViewById(R.id.shutter_root);

        return mShutterLayout;
    }

    public void registerDone() {
        Log.d(TAG, "registerDone");
    }

}