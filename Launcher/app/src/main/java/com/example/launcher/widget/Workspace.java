package com.example.launcher.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.launcher.DeviceProfile;
import com.example.launcher.Launcher;
import com.example.launcher.R;
import com.example.launcher.dragndrop.DragController;
import com.example.launcher.dragndrop.DragOptions;
import com.example.launcher.dragndrop.DropTarget;
import com.example.launcher.dragndrop.SpringLoadedDragController;
import com.example.launcher.util.IntArray;
import com.example.launcher.util.IntSparseArrayMap;
import com.example.launcher.util.PackageUserKey;

import java.util.function.Predicate;

public class Workspace extends PagedView implements DragController.DragListener , WorkspaceLayoutManager {

    private SpringLoadedDragController mSpringLoadedDragController;
    DragController mDragController;
    final Launcher mLauncher;
    private String TAG = "Workspace ";
    final IntSparseArrayMap<CellLayout> mWorkspaceScreens = new IntSparseArrayMap<>();
    final IntArray mScreenOrder = new IntArray();

    public Workspace(Context context) {
        this(context, null);
    }

    public Workspace(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Workspace(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public Workspace(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mLauncher = Launcher.getLauncher(context);

    }

    @Override
    public void onDragStart(DropTarget.DragObject dragObject, DragOptions options) {

    }

    @Override
    public void onDragEnd() {

    }

    public void bindAndInitFirstWorkspaceScreen(View qsb) {

        CellLayout firstPage = insertNewWorkspaceScreen(Workspace.FIRST_SCREEN_ID, 0);
        // Always add a QSB on the first screen.
        if (qsb == null) {
            // In transposed layout, we add the QSB in the Grid. As workspace does not touch the
            // edges, we do not need a full width QSB.
            qsb = LayoutInflater.from(getContext())
                    .inflate(R.layout.search_container_workspace, firstPage, false);
        }

        CellLayout.LayoutParams lp = new CellLayout.LayoutParams(0, 0, firstPage.getCountX(), 1);
        lp.canReorder = false;
        if (!firstPage.addViewToCellLayout(qsb, 0, R.id.search_container_workspace, lp, true)) {
            Log.e(TAG, "Failed to add to item at (0, 0) to CellLayout");
        }

    }

    public CellLayout insertNewWorkspaceScreen(int screenId, int insertIndex) {
        if (mWorkspaceScreens.containsKey(screenId)) {
            throw new RuntimeException("Screen id " + screenId + " already exists!");
        }
        // Inflate the cell layout, but do not add it automatically so that we can get the newly
        // created CellLayout.
        CellLayout newScreen = (CellLayout) LayoutInflater.from(getContext()).inflate(
                R.layout.workspace_screen, this, false /* attachToRoot */);
        DeviceProfile grid = mLauncher.getDeviceProfile();
        int paddingLeftRight = 10;//grid.cellLayoutPaddingLeftRightPx;
        int paddingBottom = 10;//grid.cellLayoutBottomPaddingPx;
        newScreen.setPadding(paddingLeftRight, 0, paddingLeftRight, paddingBottom);

        mWorkspaceScreens.put(screenId, newScreen);
        mScreenOrder.add(insertIndex, screenId);
        addView(newScreen, insertIndex);
//        mStateTransitionAnimation.applyChildState(
//                mLauncher.getStateManager().getState(), newScreen, insertIndex);
        return newScreen;
    }

    public void lockWallpaperToDefaultPage() {
//        mWallpaperOffset.setLockToDefaultPage(true);
    }

    public void setup(DragController dragController) {
        mSpringLoadedDragController = new SpringLoadedDragController(mLauncher);
        mDragController = dragController;
//        // hardware layers on children are enabled on startup, but should be disabled until
//        // needed
        updateChildrenLayersEnabled();
    }

    private void updateChildrenLayersEnabled() {
//        boolean enableChildrenLayers = mIsSwitchingState || isPageInTransition();
//        if (enableChildrenLayers != mChildrenLayersEnabled) {
//            mChildrenLayersEnabled = enableChildrenLayers;
//            if (mChildrenLayersEnabled) {
//                enableHwLayersOnVisiblePages();
//            } else {
//                for (int i = 0; i < getPageCount(); i++) {
//                    final CellLayout cl = (CellLayout) getChildAt(i);
//                    cl.enableHardwareLayer(false);
//                }
//            }
//        }
    }

    public void updateNotificationDots(Predicate<PackageUserKey> updatedDots) {

    }
}