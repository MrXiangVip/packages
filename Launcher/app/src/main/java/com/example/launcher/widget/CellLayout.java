package com.example.launcher.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;

import com.example.launcher.DeviceProfile;
import com.example.launcher.R;
import com.example.launcher.views.ActivityContext;

public class CellLayout extends ViewGroup {

    public static final int WORKSPACE = 0;

    private int mCountX;
    private int mCountY;

    protected final ActivityContext mActivity;
    private final float mChildScale = 1f;
    private final int mContainerType;

    private final ShortcutAndWidgetContainer mShortcutsAndWidgets;

    private String TAG=" CellLayout ";
    public CellLayout(Context context) {
        this(context, null);
    }

    public CellLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CellLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CellLayout, defStyleAttr, 0);

        mContainerType = a.getInteger(R.styleable.CellLayout_containerType, WORKSPACE);

        mActivity = ActivityContext.lookupContext(context);

        DeviceProfile grid = mActivity.getDeviceProfile();

        mCountX = 5;//grid.inv.numColumns;
        mShortcutsAndWidgets = new ShortcutAndWidgetContainer(context, mContainerType);

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public int getCountX() {
        return mCountX;
    }
    public boolean addViewToCellLayout(View child, int index, int childId, LayoutParams params,
                                       boolean markCells) {
        final LayoutParams lp = params;
        // Hotseat icons - remove text
        if (child instanceof BubbleTextView) {
            BubbleTextView bubbleChild = (BubbleTextView) child;
//            bubbleChild.setTextVisibility(mContainerType != HOTSEAT);
        }

        child.setScaleX(mChildScale);
        child.setScaleY(mChildScale);

        // Generate an id for each view, this assumes we have at most 256x256 cells
        // per workspace screen
        if (lp.cellX >= 0 && lp.cellX <= mCountX - 1 && lp.cellY >= 0 && lp.cellY <= mCountY - 1) {
            // If the horizontal or vertical span is set to -1, it is taken to
            // mean that it spans the extent of the CellLayout
            if (lp.cellHSpan < 0) lp.cellHSpan = mCountX;
            if (lp.cellVSpan < 0) lp.cellVSpan = mCountY;

            child.setId(childId);
            if (true) {
                Log.d(TAG, "Adding view to ShortcutsAndWidgetsContainer: " + child);
            }
            mShortcutsAndWidgets.addView(child, index, lp);

            if (markCells) markCellsAsOccupiedForView(child);

            return true;
        }
        return false;
    }
    public void markCellsAsOccupiedForView(View view) {
        if (view == null || view.getParent() != mShortcutsAndWidgets) return;
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
//        mOccupied.markCells(lp.cellX, lp.cellY, lp.cellHSpan, lp.cellVSpan, true);
    }
    public static class LayoutParams extends ViewGroup.MarginLayoutParams {

        /**
         * Vertical location of the item in the grid.
         */
        public int cellY;
        public int cellX;

        /**
         * Number of cells spanned vertically by the item.
         */
        public int cellVSpan;
        public int cellHSpan;

        public boolean canReorder = true;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(MarginLayoutParams source) {
            super(source);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(int cellX, int cellY, int cellHSpan, int cellVSpan) {
            super(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            this.cellX = cellX;
            this.cellY = cellY;
            this.cellHSpan = cellHSpan;
            this.cellVSpan = cellVSpan;
        }
    }

}
