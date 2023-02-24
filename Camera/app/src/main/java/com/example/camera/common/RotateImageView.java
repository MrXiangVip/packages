package com.example.camera.common;

import android.content.Context;
import android.util.AttributeSet;

public class RotateImageView extends androidx.appcompat.widget.AppCompatImageView implements Rotatable {
    public RotateImageView(Context context) {
        super(context);
    }
    public RotateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setOrientation(int orientation, boolean animation) {

    }
}
