package com.example.launcher.keyboard;

import android.view.View;

public class ViewGroupFocusHelper extends FocusIndicatorHelper{

    private final View mContainer;

    public ViewGroupFocusHelper(View container) {
        super(container);
        mContainer = container;
    }
}
