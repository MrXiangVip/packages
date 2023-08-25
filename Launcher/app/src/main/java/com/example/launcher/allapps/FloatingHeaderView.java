package com.example.launcher.allapps;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class FloatingHeaderView extends LinearLayout {

    private FloatingHeaderRow[] mAllRows = FloatingHeaderRow.NO_ROWS;
    protected int mMaxTranslation;
    private AllAppsRecyclerView mMainRV;
    private AllAppsRecyclerView mWorkRV;
    private AllAppsRecyclerView mCurrentRV;
    private boolean mMainRVActive = true;

    public FloatingHeaderView(Context context) {
        this(context, null);
    }

    public FloatingHeaderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingHeaderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public FloatingHeaderView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setup(AllAppsContainerView.AdapterHolder[] mAH, boolean tabsHidden) {
        for (FloatingHeaderRow row : mAllRows) {
            row.setup(this, mAllRows, tabsHidden);
        }
//        updateExpectedHeight();
//
//        mTabsHidden = tabsHidden;
//        mTabLayout.setVisibility(tabsHidden ? View.GONE : View.VISIBLE);
//        mMainRV = setupRV(mMainRV, mAH[AllAppsContainerView.AdapterHolder.MAIN].recyclerView);
//        mWorkRV = setupRV(mWorkRV, mAH[AllAppsContainerView.AdapterHolder.WORK].recyclerView);
//        mParent = (ViewGroup) mMainRV.getParent();
//        setMainActive(mMainRVActive || mWorkRV == null);
//        reset(false);
    }

    public int getMaxTranslation() {
        return mMaxTranslation;
    }

    public void setMainActive(boolean active) {
        mCurrentRV = active ? mMainRV : mWorkRV;
        mMainRVActive = active;
    }
}