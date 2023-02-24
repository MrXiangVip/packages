package com.example.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;

public class Hotseat extends CellLayout{
    private Workspace mWorkspace;


    public Hotseat(Context context) {
        this(context, null);
    }

    public Hotseat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Hotseat(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Hotseat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setWorkspace(Workspace w) {
        mWorkspace = w;
    }
}
