package com.example.launcher.util;

import android.view.MotionEvent;

import java.io.PrintWriter;

public interface TouchController {
    /**
     * Called when the draglayer receives touch event.
     */
    boolean onControllerTouchEvent(MotionEvent ev);

    /**
     * Called when the draglayer receives a intercept touch event.
     */
    boolean onControllerInterceptTouchEvent(MotionEvent ev);

    default void dump(String prefix, PrintWriter writer) { }
}
