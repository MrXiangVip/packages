package com.example.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.launcher.pageindicators.PageIndicator;

public class WorkspacePageIndicator extends View implements PageIndicator {
    public WorkspacePageIndicator(Context context) {
        this(context, null);
    }

    public WorkspacePageIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkspacePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public WorkspacePageIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void setScroll(int currentScroll, int totalScroll) {

    }

    @Override
    public void setActiveMarker(int activePage) {

    }

    @Override
    public void setMarkersCount(int numMarkers) {

    }
}
