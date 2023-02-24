package com.example.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.launcher.dragndrop.DragController;
import com.example.launcher.dragndrop.DragOptions;
import com.example.launcher.dragndrop.DropTarget;

public class ButtonDropTarget extends TextView implements DropTarget, DragController.DragListener, View.OnClickListener {

    protected DropTargetBar mDropTargetBar;


    public ButtonDropTarget(Context context) {
        this(context, null);
    }

    public ButtonDropTarget(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ButtonDropTarget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ButtonDropTarget(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onDragStart(DragObject dragObject, DragOptions options) {

    }

    @Override
    public void onDragEnd() {

    }

    public void setDropTargetBar(DropTargetBar dropTargetBar) {
        mDropTargetBar = dropTargetBar;
    }
}
