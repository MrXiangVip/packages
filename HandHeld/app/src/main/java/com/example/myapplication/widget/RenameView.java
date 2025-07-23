package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RenameView extends FrameLayout {
    public RenameView(@NonNull Context context) {
        super(context);
    }

    public RenameView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RenameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RenameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
