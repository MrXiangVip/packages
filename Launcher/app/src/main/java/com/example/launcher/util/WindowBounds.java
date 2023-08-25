package com.example.launcher.util;

import android.graphics.Point;
import android.graphics.Rect;

public class WindowBounds {
    public final Rect bounds;
    public final Rect insets;
    public final Point availableSize;

    public WindowBounds(Rect bounds, Rect insets) {
        this.bounds = bounds;
        this.insets = insets;
        availableSize = new Point(bounds.width() - insets.left - insets.right,
                bounds.height() - insets.top - insets.bottom);
    }
}
