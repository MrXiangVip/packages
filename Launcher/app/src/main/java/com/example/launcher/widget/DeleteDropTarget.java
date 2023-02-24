package com.example.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class DeleteDropTarget extends ButtonDropTarget{
    public DeleteDropTarget(Context context) {
        this(context, null);
    }

    public DeleteDropTarget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DeleteDropTarget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public DeleteDropTarget(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
