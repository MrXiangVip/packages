package com.example.launcher.icons;

import android.graphics.Rect;
import android.view.ViewDebug;

public class DotRenderer {


    public static class DrawParams {
        /** The color (possibly based on the icon) to use for the dot. */
        @ViewDebug.ExportedProperty(category = "notification dot", formatToHexString = true)
        public int color;
        /** The bounds of the icon that the dot is drawn on top of. */
        @ViewDebug.ExportedProperty(category = "notification dot")
        public Rect iconBounds = new Rect();
        /** The progress of the animation, from 0 to 1. */
        @ViewDebug.ExportedProperty(category = "notification dot")
        public float scale;
        /** Whether the dot should align to the top left of the icon rather than the top right. */
        @ViewDebug.ExportedProperty(category = "notification dot")
        public boolean leftAlign;
    }
}
