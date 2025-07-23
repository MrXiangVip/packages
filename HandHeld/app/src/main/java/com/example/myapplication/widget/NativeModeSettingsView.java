package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class NativeModeSettingsView extends LinearLayout {
    public NativeModeSettingsView(Context context) {
        this(context, null);
    }

    public NativeModeSettingsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NativeModeSettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public NativeModeSettingsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
