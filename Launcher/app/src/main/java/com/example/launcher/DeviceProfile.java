package com.example.launcher;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.Surface;

import com.example.launcher.icons.DotRenderer;
import com.example.launcher.util.DefaultDisplay;
import com.example.launcher.util.WindowBounds;

public class DeviceProfile {

    public final boolean isLandscape;
    public final int cellLayoutPaddingLeftRightPx;
    public final int cellLayoutBottomPaddingPx;
    private static final int PORTRAIT_TABLET_LEFT_RIGHT_PADDING_MULTIPLIER = 4;
    public final InvariantDeviceProfile inv;

    public DotRenderer mDotRendererAllApps;
    public DotRenderer mDotRendererWorkSpace;
    public int iconDrawablePaddingPx;
// Workspace icons
    public int iconSizePx;
    public int iconTextSizePx;
    public final PointF appWidgetScale = new PointF(1.0f, 1.0f);

    public int cellHeightPx;
    public int cellWidthPx;
    private boolean mIsSeascape;

    public final int availableWidthPx;
    public final int availableHeightPx;
    private final DefaultDisplay.Info mInfo;
    public final int widthPx;
    public final int heightPx;
    public final int windowX;
    public final int windowY;
    public final boolean isMultiWindowMode;
    public final boolean transposeLayoutWithOrientation;

    DeviceProfile(Context context, InvariantDeviceProfile inv, DefaultDisplay.Info info,
                  Point minSize, Point maxSize, int width, int height, boolean isLandscape,
                  boolean isMultiWindowMode, boolean transposeLayoutWithOrientation,
                  Point windowPosition)  {
        final Resources res = context.getResources();
        this.inv = inv;
        this.isLandscape = isLandscape;
        windowX = windowPosition.x;
        windowY = windowPosition.y;
        this.isMultiWindowMode = isMultiWindowMode;
        this.transposeLayoutWithOrientation = transposeLayoutWithOrientation;

        // Determine sizes.
        widthPx = width;
        heightPx = height;
        if (isLandscape) {
            availableWidthPx = maxSize.x;
            availableHeightPx = minSize.y;
        } else {
            availableWidthPx = minSize.x;
            availableHeightPx = maxSize.y;
        }
        mInfo = info;


        int cellLayoutPaddingLeftRightMultiplier = isLandscape
                ? PORTRAIT_TABLET_LEFT_RIGHT_PADDING_MULTIPLIER : 1;
        int cellLayoutPadding = res.getDimensionPixelSize(R.dimen.dynamic_grid_cell_layout_padding);
        if (isLandscape) {
            cellLayoutPaddingLeftRightPx = 0;
            cellLayoutBottomPaddingPx = cellLayoutPadding;
        } else {
            cellLayoutPaddingLeftRightPx = cellLayoutPaddingLeftRightMultiplier * cellLayoutPadding;
            cellLayoutBottomPaddingPx = 0;
        }
    }
    public Point getCellSize() {
        return getCellSize(inv.numColumns, inv.numRows);
    }
    private Point getCellSize(int numColumns, int numRows) {
        Point result = new Point();
        return result;

    }
    public DeviceProfile getMultiWindowProfile(Context context, WindowBounds windowBounds) {
        // We take the minimum sizes of this profile and it's multi-window variant to ensure that
        // the system decor is always excluded.
        Point mwSize = new Point(Math.min(availableWidthPx, windowBounds.availableSize.x),
                Math.min(availableHeightPx, windowBounds.availableSize.y));

        DeviceProfile profile = toBuilder(context)
                .setSizeRange(mwSize, mwSize)
                .setSize(windowBounds.bounds.width(), windowBounds.bounds.height())
                .setWindowPosition(windowBounds.bounds.left, windowBounds.bounds.top)
                .setMultiWindowMode(true)
                .build();

        // If there isn't enough vertical cell padding with the labels displayed, hide the labels.
        float workspaceCellPaddingY = profile.getCellSize().y - profile.iconSizePx
                - iconDrawablePaddingPx - profile.iconTextSizePx;
        if (workspaceCellPaddingY < profile.iconDrawablePaddingPx * 2) {
            profile.adjustToHideWorkspaceLabels();
        }

        // We use these scales to measure and layout the widgets using their full invariant profile
        // sizes and then draw them scaled and centered to fit in their multi-window mode cellspans.
        float appWidgetScaleX = (float) profile.getCellSize().x / getCellSize().x;
        float appWidgetScaleY = (float) profile.getCellSize().y / getCellSize().y;
        profile.appWidgetScale.set(appWidgetScaleX, appWidgetScaleY);
        profile.updateWorkspacePadding();

        return profile;
    }
    private void adjustToHideWorkspaceLabels() {
        iconTextSizePx = 0;
        iconDrawablePaddingPx = 0;
        cellHeightPx = iconSizePx;
        autoResizeAllAppsCells();
    }
    private void updateWorkspacePadding() {

    }
    public void autoResizeAllAppsCells() {

    }
    public boolean isVerticalBarLayout() {
        return isLandscape && transposeLayoutWithOrientation;
    }
    public boolean updateIsSeascape(Context context) {
        if (isVerticalBarLayout()) {
            boolean isSeascape = DefaultDisplay.INSTANCE.get(context).getInfo().rotation
                    == Surface.ROTATION_270;
            if (mIsSeascape != isSeascape) {
                mIsSeascape = isSeascape;
                return true;
            }
        }
        return false;
    }


    public Builder toBuilder(Context context) {
        Point size = new Point(availableWidthPx, availableHeightPx);
        return new Builder(context, inv, mInfo)
                .setSizeRange(size, size)
                .setSize(widthPx, heightPx)
                .setWindowPosition(windowX, windowY)
                .setMultiWindowMode(isMultiWindowMode);
    }

    public static class Builder {
        private Context mContext;
        private InvariantDeviceProfile mInv;
        private DefaultDisplay.Info mInfo;
        private boolean mTransposeLayoutWithOrientation;
        private Point mMinSize, mMaxSize;
        private int mWidth, mHeight;
        private boolean mIsLandscape;
        private final Point mWindowPosition = new Point();
        private boolean mIsMultiWindowMode = false;

        public Builder(Context context, InvariantDeviceProfile inv, DefaultDisplay.Info info) {
            mContext = context;
            mInv = inv;
            mInfo = info;
            mTransposeLayoutWithOrientation = context.getResources()
                    .getBoolean(R.bool.hotseat_transpose_layout_with_orientation);
        }
        public Builder setSizeRange(Point minSize, Point maxSize) {
            mMinSize = minSize;
            mMaxSize = maxSize;
            return this;
        }
        public Builder setSize(int width, int height) {
            mWidth = width;
            mHeight = height;
            mIsLandscape = mWidth > mHeight;
            return this;
        }
        public Builder setWindowPosition(int x, int y) {
            mWindowPosition.set(x, y);
            return this;
        }
        public Builder setMultiWindowMode(boolean isMultiWindowMode) {
            mIsMultiWindowMode = isMultiWindowMode;
            return this;
        }
        public DeviceProfile build() {
            return new DeviceProfile(mContext, mInv, mInfo, mMinSize, mMaxSize,
                    mWidth, mHeight, mIsLandscape, mIsMultiWindowMode,
                    mTransposeLayoutWithOrientation, mWindowPosition);
        }

    }
}
