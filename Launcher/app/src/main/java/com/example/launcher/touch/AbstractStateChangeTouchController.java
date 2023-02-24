package com.example.launcher.touch;

import android.view.MotionEvent;

import com.example.launcher.Launcher;
import com.example.launcher.util.TouchController;

import java.io.PrintWriter;

public class AbstractStateChangeTouchController implements TouchController {
    protected final Launcher mLauncher;

    @Override
    public boolean onControllerTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onControllerInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
    public AbstractStateChangeTouchController(Launcher l) {
        mLauncher = l;
//        mDetector = new SingleAxisSwipeDetector(l, this, dir);
//        mSwipeDirection = dir;
    }

}
