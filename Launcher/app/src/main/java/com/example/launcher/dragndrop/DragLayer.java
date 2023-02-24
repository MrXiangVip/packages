package com.example.launcher.dragndrop;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.launcher.Launcher;
import com.example.launcher.graphics.WorkspaceAndHotseatScrim;
import com.example.launcher.keyboard.ViewGroupFocusHelper;
import com.example.launcher.util.TouchController;
import com.example.launcher.views.BaseDragLayer;
import com.example.launcher.widget.Workspace;

public class DragLayer extends BaseDragLayer<Launcher> {
    private final ViewGroupFocusHelper mFocusIndicatorHelper;

    private DragController mDragController;
    private final WorkspaceAndHotseatScrim mWorkspaceScrim;
    private View mMoveTarget;
    protected TouchController[] mControllers;
    private static final int ALPHA_CHANNEL_COUNT = 3;

    public DragLayer(@NonNull Context context) {
        this(context, null);
    }

    public DragLayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, ALPHA_CHANNEL_COUNT);
        mFocusIndicatorHelper = new ViewGroupFocusHelper(this);
        mWorkspaceScrim = new WorkspaceAndHotseatScrim(this);
    }



    public ViewGroupFocusHelper getFocusIndicatorHelper() {
        return mFocusIndicatorHelper;
    }

    public void setup(DragController dragController, Workspace workspace) {
        mDragController = dragController;
        mWorkspaceScrim.setWorkspace(workspace);
        mMoveTarget = workspace;
        recreateControllers();
    }
    public void recreateControllers() {
        mControllers = mActivity.createTouchControllers();
    }

}
