package com.example.camera.preview;

import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.camera.common.IApp;
import com.example.camera.common.IAppUiListener;
import com.example.camerabg.R;
import com.example.camera.common.IAppUiListener.ISurfaceStatusListener;

public class TextureViewController implements  IController {
    private IApp mApp;
    private ViewGroup mPreviewRoot;
    private View.OnTouchListener mOnTouchListener;
    private View.OnLayoutChangeListener mOnLayoutChangeListener;

    private String TAG = "TextureViewController.";
    private PreviewTextureView mTextureView;
    private int mPreviewWidth = 0;
    private int mPreviewHeight = 0;
    private double mPreviewAspectRatio = 0.0d;
    private SurfaceChangeCallback mSurfaceChangeCallback;
    private ViewGroup mPreviewContainer;

    public TextureViewController(IApp app) {
        mApp = app;
        mPreviewRoot = (ViewGroup) mApp.getActivity().findViewById(R.id.preview_frame_root);
    }

    @Override
    public View getView() {
        return null;
    }

    @Override
    public void updatePreviewSize(int width, int height, ISurfaceStatusListener listener) {
        Log.d(TAG, "updatePreviewSize");
        mPreviewWidth = width;
        mPreviewHeight = height;
        mPreviewAspectRatio = (double) Math.max(width, height)
                / Math.min(width, height);
        if (mTextureView == null) {
            attachTextureView(listener);
        } else {
            ISurfaceStatusListener l = mSurfaceChangeCallback.getBindStatusListener();
            if (listener != null && listener != l) {
                mTextureView.setSurfaceTextureListener(null);
                mSurfaceChangeCallback = new SurfaceChangeCallback(listener);
                mTextureView.setSurfaceTextureListener(mSurfaceChangeCallback);
                listener.surfaceAvailable(mTextureView.getSurfaceTexture(),
                        mPreviewWidth, mPreviewHeight);
            }
        }
        mTextureView.setAspectRatio(mPreviewAspectRatio);
    }

    @Override
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    @Override
    public void setOnLayoutChangeListener(View.OnLayoutChangeListener layoutChangeListener) {
        mOnLayoutChangeListener = layoutChangeListener;
    }

    public void  attachTextureView(ISurfaceStatusListener listener) {
        ViewGroup container = (ViewGroup) mApp.getActivity().getLayoutInflater().inflate(
                R.layout.textureview_layout, null);
        PreviewTextureView textureView =
                (PreviewTextureView) container.findViewById(R.id.preview_surface);
        mSurfaceChangeCallback = new SurfaceChangeCallback(listener);
        textureView.setSurfaceTextureListener(mSurfaceChangeCallback);
        textureView.setOnTouchListener(mOnTouchListener);
        mPreviewRoot.addView(container, 0);
        mTextureView = textureView;
        mPreviewContainer = container;

    }

    private class SurfaceChangeCallback implements TextureView.SurfaceTextureListener {
        private ISurfaceStatusListener mListener;
        SurfaceChangeCallback(ISurfaceStatusListener listener) {
            mListener = listener;
        }
        ISurfaceStatusListener getBindStatusListener() {
            return mListener;
        }

        @Override
        public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

        }
    }

}