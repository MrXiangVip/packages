package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsoluteLayout;

public class GamepadConfigViewEditMode extends AbsoluteLayout {
    public GamepadConfigViewEditMode(Context context) {
        this(context, null);
    }

    public GamepadConfigViewEditMode(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GamepadConfigViewEditMode(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public GamepadConfigViewEditMode(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
