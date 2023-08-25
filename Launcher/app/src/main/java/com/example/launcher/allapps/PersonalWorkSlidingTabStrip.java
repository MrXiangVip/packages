package com.example.launcher.allapps;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.launcher.pageindicators.PageIndicator;

public class PersonalWorkSlidingTabStrip extends LinearLayout implements PageIndicator {

    private AllAppsContainerView mContainerView;


    public PersonalWorkSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PersonalWorkSlidingTabStrip(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonalWorkSlidingTabStrip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PersonalWorkSlidingTabStrip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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

    public void setContainerView(AllAppsContainerView containerView) {
        mContainerView = containerView;
    }

}
