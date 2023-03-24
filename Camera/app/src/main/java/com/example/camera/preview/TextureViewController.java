package com.example.camera.preview;

import android.view.View;
import android.view.ViewGroup;

import com.example.camera.common.IApp;
import com.example.camerabg.R;

public class TextureViewController implements  IController {
    private IApp mApp;
    private ViewGroup mPreviewRoot;
    private View.OnTouchListener mOnTouchListener;

    public TextureViewController(IApp app) {
        mApp = app;
        mPreviewRoot = (ViewGroup) mApp.getActivity().findViewById(R.id.preview_frame_root);
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;

    }

    public View attachTextureView( ) {
        ViewGroup container = (ViewGroup) mApp.getActivity().getLayoutInflater().inflate(
                R.layout.textureview_layout, null);
        PreviewTextureView textureView =
                (PreviewTextureView) container.findViewById(R.id.preview_surface);
        textureView.setOnTouchListener(mOnTouchListener);
        mPreviewRoot.addView(container, 0);
        return  mPreviewRoot;
    }
}
