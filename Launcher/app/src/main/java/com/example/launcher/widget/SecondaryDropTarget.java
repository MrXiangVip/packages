package com.example.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class SecondaryDropTarget extends ButtonDropTarget{
    public SecondaryDropTarget(Context context) {
        this(context, null);
    }

    public SecondaryDropTarget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SecondaryDropTarget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SecondaryDropTarget(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
