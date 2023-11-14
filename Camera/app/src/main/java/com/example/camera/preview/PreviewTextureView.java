package com.example.camera.preview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PreviewTextureView extends TextureView {
    private double mAspectRatio = 0.0;

    public PreviewTextureView(@NonNull Context context) {
        this(context, null);
    }

    public PreviewTextureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PreviewTextureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PreviewTextureView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAspectRatio(double aspectRatio) {
        if (mAspectRatio != aspectRatio) {
            mAspectRatio = aspectRatio;
            requestLayout();
        }
    }
}
