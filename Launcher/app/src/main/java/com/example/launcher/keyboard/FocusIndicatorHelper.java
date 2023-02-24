package com.example.launcher.keyboard;

import android.animation.ValueAnimator;
import android.view.View;

public class FocusIndicatorHelper implements View.OnFocusChangeListener, ValueAnimator.AnimatorUpdateListener {

    private final View mContainer;

    public FocusIndicatorHelper(View container) {
        mContainer = container;
    }
    @Override
    public void onAnimationUpdate(ValueAnimator animation) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }
}
