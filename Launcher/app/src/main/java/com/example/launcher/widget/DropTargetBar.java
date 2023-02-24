package com.example.launcher.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.launcher.dragndrop.DragController;
import com.example.launcher.dragndrop.DragOptions;
import com.example.launcher.dragndrop.DropTarget;

public class DropTargetBar extends FrameLayout implements DragController.DragListener, Insettable{
    private ButtonDropTarget[] mDropTargets;


    public DropTargetBar(@NonNull Context context) {
        super(context);
    }

    public DropTargetBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DropTargetBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DropTargetBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setup(DragController dragController) {
        dragController.addDragListener(this);
        for (int i = 0; i < mDropTargets.length; i++) {
            dragController.addDragListener(mDropTargets[i]);
            dragController.addDropTarget(mDropTargets[i]);
        }
    }
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDropTargets = new ButtonDropTarget[getChildCount()];
        for (int i = 0; i < mDropTargets.length; i++) {
            mDropTargets[i] = (ButtonDropTarget) getChildAt(i);
            mDropTargets[i].setDropTargetBar(this);
        }
    }
    @Override
    public void onDragStart(DropTarget.DragObject dragObject, DragOptions options) {

    }

    @Override
    public void onDragEnd() {

    }

    @Override
    public void setInsets(Rect insets) {

    }
}
