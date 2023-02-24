package com.example.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class ShortcutAndWidgetContainer extends ViewGroup {

    private final int mContainerType;

    public ShortcutAndWidgetContainer(Context context, int containerType) {
        super(context);
        mContainerType = containerType;

    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
