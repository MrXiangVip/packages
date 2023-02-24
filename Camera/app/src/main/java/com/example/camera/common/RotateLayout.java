package com.example.camera.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class RotateLayout extends ViewGroup implements  Rotatable {
    public RotateLayout(Context context) {
        super(context);
    }
    public RotateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // The transparent background here is a workaround of the render issue
        // happened when the view is rotated as the device's orientation
        // changed. The view looks fine in landscape. After rotation, the view
        // is invisible.
        setBackgroundResource(android.R.color.transparent);
    }
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public void setOrientation(int orientation, boolean animation) {

    }
}
