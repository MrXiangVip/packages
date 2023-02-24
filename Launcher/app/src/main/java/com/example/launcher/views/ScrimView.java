package com.example.launcher.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ScrimView extends View {
    public ScrimView(Context context) {
        this(context, null);
    }

    public ScrimView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScrimView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
