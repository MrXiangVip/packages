package com.example.launcher.search;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class ExtendedEditText extends EditText {
    public ExtendedEditText(Context context) {
        this(context, null);
    }

    public ExtendedEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ExtendedEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
