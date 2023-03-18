package com.example.camera.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class RotateLayout extends ViewGroup implements  Rotatable {
    protected View mChild;
    private int mOrientation;

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
    protected void onMeasure(int widthSpec, int heightSpec) {
        int w = 0;
        int h = 0;
        if (mChild == null) {
            setMeasuredDimension(w, h);
            return;
        }
        switch (mOrientation) {
            case 0:
            case 180:
                measureChild(mChild, widthSpec, heightSpec);
                w = mChild.getMeasuredWidth();
                h = mChild.getMeasuredHeight();
                break;
            case 90:
            case 270:
                measureChild(mChild, heightSpec, widthSpec);
                w = mChild.getMeasuredHeight();
                h = mChild.getMeasuredWidth();
                break;
            default:
                break;
        }
        setMeasuredDimension(w, h);

        switch (mOrientation) {
            case 0:
                mChild.setTranslationX(0);
                mChild.setTranslationY(0);
                break;
            case 90:
                mChild.setTranslationX(0);
                mChild.setTranslationY(h);
                break;
            case 180:
                mChild.setTranslationX(w);
                mChild.setTranslationY(h);
                break;
            case 270:
                mChild.setTranslationX(w);
                mChild.setTranslationY(0);
                break;
            default:
                break;
        }
        mChild.setRotation(-mOrientation);
    }
    @Override
    protected void onLayout(boolean change, int left, int top, int right, int bottom) {
        if (mChild == null) {
            return;
        }
        int width = right - left;
        int height = bottom - top;
        switch (mOrientation) {
            case 0:
            case 180:
                mChild.layout(0, 0, width, height);
                break;
            case 90:
            case 270:
                mChild.layout(0, 0, height, width);
                break;
            default:
                break;
        }
    }

    @Override
    public void setOrientation(int orientation, boolean animation) {
        mOrientation = orientation;
        requestLayout();
    }
}
