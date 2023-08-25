package com.example.launcher.util;

import android.content.Context;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManager.DisplayListener;
import android.util.DisplayMetrics;
import android.view.Display;

import static android.view.Display.DEFAULT_DISPLAY;

public class DefaultDisplay implements DisplayListener {
    public static final MainThreadInitializedObject<DefaultDisplay> INSTANCE =
            new MainThreadInitializedObject<>(DefaultDisplay::new);
    private Info mInfo;
    private final Context mDisplayContext;

    private DefaultDisplay(Context context) {
        DisplayManager dm = context.getSystemService(DisplayManager.class);
        mDisplayContext = context.getApplicationContext().createDisplayContext(
                dm.getDisplay(DEFAULT_DISPLAY));
        mInfo = new Info(mDisplayContext);

    }
    public Info getInfo() {
        return mInfo;
    }

    @Override
    public void onDisplayAdded(int displayId) {

    }

    @Override
    public void onDisplayRemoved(int displayId) {

    }

    @Override
    public void onDisplayChanged(int displayId) {

    }

    public static class Info {
        public final int rotation;

        public final Point realSize;
        public final Point smallestSize;
        public final Point largestSize;

        public final DisplayMetrics metrics;

        public Info(Context context) {
            this(context, context.getSystemService(DisplayManager.class)
                    .getDisplay(DEFAULT_DISPLAY));
        }

        public Info(Context context, Display display) {
            rotation = display.getRotation();
            realSize = new Point();
            smallestSize = new Point();
            largestSize = new Point();
            display.getRealSize(realSize);
            display.getCurrentSizeRange(smallestSize, largestSize);
            metrics = context.getResources().getDisplayMetrics();

        }
    }
}
