package com.example.launcher.dragndrop;

import android.view.MotionEvent;

import com.example.launcher.Launcher;
import com.example.launcher.util.TouchController;

import java.util.ArrayList;

public class DragController implements DragDriver.EventListener, TouchController {

    private final ArrayList<DragListener> mListeners = new ArrayList<>();
    private final ArrayList<DropTarget> mDropTargets = new ArrayList<>();
    private final Launcher mLauncher;

    @Override
    public void onDriverDragMove(float x, float y) {

    }

    @Override
    public void onDriverDragExitWindow() {

    }

    @Override
    public void onDriverDragEnd(float x, float y) {

    }

    @Override
    public void onDriverDragCancel() {

    }

    @Override
    public boolean onControllerTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onControllerInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    public void addDragListener(DragListener l) {
        mListeners.add(l);
    }
    public void addDropTarget(DropTarget target) {
        mDropTargets.add(target);
    }
    public DragController(Launcher launcher) {
        mLauncher = launcher;
//        mFlingToDeleteHelper = new FlingToDeleteHelper(launcher);
    }


    public interface DragListener {
        /**
         * A drag has begun
         *
         * @param dragObject The object being dragged
         * @param options Options used to start the drag
         */
        void onDragStart(DropTarget.DragObject dragObject, DragOptions options);

        /**
         * The drag has ended
         */
        void onDragEnd();
    }
}
