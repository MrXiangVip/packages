package com.example.launcher.widget;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

import com.example.launcher.R;
import com.example.launcher.Utilities;
import com.example.launcher.config.FeatureFlags;
import com.example.launcher.pageindicators.PageIndicator;

public abstract class PagedView<T extends View & PageIndicator> extends ViewGroup {
    //    @Thunk int mPageIndicatorViewId;
//    protected T mPageIndicator;
    int mPageIndicatorViewId;
    protected T mPageIndicator;
    public static final int PAGE_SNAP_ANIMATION_DURATION = 750;
    protected int mUnboundedScroll;
    protected boolean mFirstLayout = true;
    public static final int INVALID_PAGE = -1;
    protected int mNextPage = INVALID_PAGE;
    protected OverScroller mScroller;
    protected int mCurrentPage;
    protected int[] mPageScrolls;
    protected final Rect mInsets = new Rect();
    private static final String TAG = "PagedView";
    private static final boolean DEBUG = false;

    public PagedView(Context context) {
        this(context, null);
    }

    public PagedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagedView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PagedView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.PagedView, defStyleAttr, 0);
        mPageIndicatorViewId = a.getResourceId(R.styleable.PagedView_pageIndicator, -1);
        mCurrentPage = 0;

    }



    public void initParentViews(View parent) {
        if (mPageIndicatorViewId > -1) {
            mPageIndicator = parent.findViewById(mPageIndicatorViewId);
            mPageIndicator.setMarkersCount(getChildCount());
        }
    }

    public T getPageIndicator() {
        return mPageIndicator;
    }

    public boolean snapToPage(int whichPage) {
        return snapToPage(whichPage, PAGE_SNAP_ANIMATION_DURATION);
    }

    public boolean snapToPage(int whichPage, int duration) {
        return snapToPage(whichPage, duration, false, null);
    }

    protected boolean snapToPage(int whichPage, int duration, boolean immediate,
                                 TimeInterpolator interpolator) {
        whichPage = validateNewPage(whichPage);

        int newLoc = getScrollForPage(whichPage);
        final int delta = newLoc - getUnboundedScroll();
        return snapToPage(whichPage, delta, duration, immediate, interpolator, 0, false);
    }

    protected boolean snapToPage(int whichPage, int delta, int duration, boolean immediate,
                                 TimeInterpolator interpolator, float velocity, boolean spring) {
        if (mFirstLayout) {
            setCurrentPage(whichPage);
            return false;
        }

        if (FeatureFlags.IS_STUDIO_BUILD) {
            duration *= Settings.Global.getFloat(getContext().getContentResolver(),
                    Settings.Global.WINDOW_ANIMATION_SCALE, 1);
        }

        whichPage = validateNewPage(whichPage);

        mNextPage = whichPage;

        awakenScrollBars(duration);
        if (immediate) {
            duration = 0;
        } else if (duration == 0) {
            duration = Math.abs(delta);
        }

        if (duration != 0) {
            pageBeginTransition();
        }

        if (!mScroller.isFinished()) {
            abortScrollerAnimation(false);
        }

//        if (interpolator != null) {
//            mScroller.setInterpolator(interpolator);
//        } else {
//            mScroller.setInterpolator(mDefaultInterpolator);
//        }
//
//        if (spring && QUICKSTEP_SPRINGS.get()) {
//            mScroller.startScrollSpring(getUnboundedScroll(), delta, duration, velocity);
//        } else {
//            mScroller.startScroll(getUnboundedScroll(), delta, duration);
//        }
        mScroller.startScroll(getUnboundedScroll(), delta, duration, 0);

        updatePageIndicator();

        // Trigger a compute() to finish switching pages if necessary
        if (immediate) {
            computeScroll();
            pageEndTransition();
        }
        invalidate();
        return Math.abs(delta) > 0;
    }

    private int validateNewPage(int newPage) {
        newPage = ensureWithinScrollBounds(newPage);
        // Ensure that it is clamped by the actual set of children in all cases
        return Utilities.boundToRange(newPage, 0, getPageCount() - 1);
    }
    private int ensureWithinScrollBounds(int page) {
        return page;
    }
    public int getPageCount() {
        return getChildCount();
    }

    public int getScrollForPage(int index) {
        if (mPageScrolls == null || index >= mPageScrolls.length || index < 0) {
            return 0;
        } else {
            return mPageScrolls[index];
        }
    }


    protected int getUnboundedScroll() {
        return mUnboundedScroll;
    }

    public void setCurrentPage(int currentPage) {
        setCurrentPage(currentPage, INVALID_PAGE);
    }

    public void setCurrentPage(int currentPage, int overridePrevPage) {
        if (!mScroller.isFinished()) {
            abortScrollerAnimation(true);
        }
        // don't introduce any checks like mCurrentPage == currentPage here-- if we change the
        // the default
        if (getChildCount() == 0) {
            return;
        }
        int prevPage = overridePrevPage != INVALID_PAGE ? overridePrevPage : mCurrentPage;
        mCurrentPage = validateNewPage(currentPage);
        updateCurrentPageScroll();
        notifyPageSwitchListener(prevPage);
        invalidate();
    }
    private void abortScrollerAnimation(boolean resetNextPage) {
        mScroller.abortAnimation();
        // We need to clean up the next page here to avoid computeScrollHelper from
        // updating current page on the pass.
        if (resetNextPage) {
            mNextPage = INVALID_PAGE;
            pageEndTransition();
        }
    }
    protected void notifyPageSwitchListener(int prevPage) {
        updatePageIndicator();
    }

    private void updatePageIndicator() {
        if (mPageIndicator != null) {
            mPageIndicator.setActiveMarker(getNextPage());
        }
    }
    public int getNextPage() {
        return (mNextPage != INVALID_PAGE) ? mNextPage : mCurrentPage;
    }

    protected void updateCurrentPageScroll() {
        // If the current page is invalid, just reset the scroll position to zero
//        int newPosition = 0;
//        if (0 <= mCurrentPage && mCurrentPage < getPageCount()) {
//            newPosition = getScrollForPage(mCurrentPage);
//        }
//        mOrientationHandler.set(this, VIEW_SCROLL_TO, newPosition);
//        mOrientationHandler.scrollerStartScroll(mScroller, newPosition);
//        forceFinishScroller(true);
    }

    protected void pageBeginTransition() {

    }

    protected void pageEndTransition() {

    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        // We measure the dimensions of the PagedView to be larger than the pages so that when we
        // zoom out (and scale down), the view is still contained in the parent
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.UNSPECIFIED) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        // Return early if we aren't given a proper dimension
        if (widthSize <= 0 || heightSize <= 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        // The children are given the same width and height as the workspace
        // unless they were set to WRAP_CONTENT
        if (DEBUG) Log.d(TAG, "PagedView.onMeasure(): " + widthSize + ", " + heightSize);

        int myWidthSpec = MeasureSpec.makeMeasureSpec(
                widthSize - mInsets.left - mInsets.right, MeasureSpec.EXACTLY);
        int myHeightSpec = MeasureSpec.makeMeasureSpec(
                heightSize - mInsets.top - mInsets.bottom, MeasureSpec.EXACTLY);

        // measureChildren takes accounts for content padding, we only need to care about extra
        // space due to insets.
        measureChildren(myWidthSpec, myHeightSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}