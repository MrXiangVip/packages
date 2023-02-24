package com.example.launcher.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class SpringRelativeLayout extends RelativeLayout {
    public SpringRelativeLayout(Context context) {
        this(context, null);
    }

    public SpringRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpringRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SpringRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
