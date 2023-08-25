package com.example.launcher.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.widget.RelativeLayout;

public class SpringRelativeLayout extends RelativeLayout {

    protected final SparseBooleanArray mSpringViews = new SparseBooleanArray();


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
    public void addSpringView(int id) {
        mSpringViews.put(id, true);
    }
}
