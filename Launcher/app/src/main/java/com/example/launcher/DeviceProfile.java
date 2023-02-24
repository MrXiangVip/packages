package com.example.launcher;

import android.content.Context;
import android.content.res.Resources;

public class DeviceProfile {

    public final boolean isLandscape;
    public final int cellLayoutPaddingLeftRightPx;
    public final int cellLayoutBottomPaddingPx;
    private static final int PORTRAIT_TABLET_LEFT_RIGHT_PADDING_MULTIPLIER = 4;
    public final InvariantDeviceProfile inv;

    public DeviceProfile(Context context, InvariantDeviceProfile inv, boolean isLandscape) {
        final Resources res = context.getResources();
        this.inv = inv;
        this.isLandscape = isLandscape;

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
}
