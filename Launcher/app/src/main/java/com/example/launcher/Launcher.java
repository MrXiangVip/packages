package com.example.launcher;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;

import com.example.launcher.allapps.AllAppsContainerView;
import com.example.launcher.allapps.AllAppsTransitionController;
import com.example.launcher.dragndrop.DragController;
import com.example.launcher.dragndrop.DragLayer;
import com.example.launcher.keyboard.ViewGroupFocusHelper;
import com.example.launcher.model.AppInfo;
import com.example.launcher.model.BgDataModel.Callbacks;
import com.example.launcher.model.ModelWriter;
import com.example.launcher.popup.PopupDataProvider;
import com.example.launcher.statemanager.StatefulActivity;
import com.example.launcher.touch.AllAppsSwipeController;
import com.example.launcher.util.ComponentKey;
import com.example.launcher.util.PackageUserKey;
import com.example.launcher.util.TouchController;
import com.example.launcher.util.WindowBounds;
import com.example.launcher.views.ActivityContext;
import com.example.launcher.views.ScrimView;
import com.example.launcher.widget.DropTargetBar;
import com.example.launcher.widget.Hotseat;
import com.example.launcher.widget.LauncherRootView;
import com.example.launcher.widget.WidgetListRowEntry;
import com.example.launcher.widget.Workspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Predicate;

public class Launcher extends StatefulActivity<LauncherState> implements ActivityContext, Callbacks, InvariantDeviceProfile.OnIDPChangeListener {

    private LauncherRootView mRootView;

    DragLayer mDragLayer;
    private ViewGroupFocusHelper mFocusHandler;
    Workspace mWorkspace;
    private View mOverviewPanel;
    Hotseat mHotseat;
    private DragController mDragController;
    private DropTargetBar mDropTargetBar;
    AllAppsContainerView mAppsView;
    ScrimView mScrimView;
    AllAppsTransitionController mAllAppsController;
    private LauncherModel mModel;
    private PopupDataProvider mPopupDataProvider;
    private Configuration mOldConfig;
    private ModelWriter mModelWriter;

    private String TAG="Launcher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        LauncherAppState app = LauncherAppState.getInstance(this);
        mOldConfig = new Configuration(getResources().getConfiguration());

        mModel = app.getModel();
        InvariantDeviceProfile idp = app.getInvariantDeviceProfile();
        initDeviceProfile(idp);
        idp.addOnChangeListener(this);

        mDragController = new DragController(this);
        mAllAppsController = new AllAppsTransitionController(this);

        inflateRootView(R.layout.launcher);
        setupViews();
        mPopupDataProvider = new PopupDataProvider(this::updateNotificationDots);

        mModel.addCallbacksAndLoad(this);
        setContentView(getRootView());

    }
    private void initDeviceProfile(InvariantDeviceProfile idp) {
        // Load configuration-specific DeviceProfile
        mDeviceProfile = idp.getDeviceProfile(this);
        if (isInMultiWindowMode()) {
            mDeviceProfile = mDeviceProfile.getMultiWindowProfile(
                    this, getMultiWindowDisplaySize());
        }

        onDeviceProfileInitiated();
        mModelWriter = mModel.getWriter(getDeviceProfile().isVerticalBarLayout(), true);
    }
    protected WindowBounds getMultiWindowDisplaySize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point mwSize = new Point();
        display.getSize(mwSize);
        return new WindowBounds(new Rect(0, 0, mwSize.x, mwSize.y), new Rect());
    }
    protected void onDeviceProfileInitiated() {
        if (mDeviceProfile.isVerticalBarLayout()) {
            mDeviceProfile.updateIsSeascape(this);
        }
    }

    protected void inflateRootView(int layoutId) {
        mRootView = (LauncherRootView) LayoutInflater.from(this).inflate(layoutId, null);
        mRootView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }

    protected void setupViews() {
        Log.d(TAG, "setupViews");
        mDragLayer = mRootView.findViewById(R.id.drag_layer);
        mFocusHandler = mDragLayer.getFocusIndicatorHelper();
        mWorkspace = mDragLayer.findViewById(R.id.workspace);
        mWorkspace.initParentViews(mDragLayer);
        mOverviewPanel = mRootView.findViewById(R.id.overview_panel);
        mHotseat = mRootView.findViewById(R.id.hotseat);
        mHotseat.setWorkspace(mWorkspace);

        // Setup the drag layer
        mDragLayer.setup(mDragController, mWorkspace);
        mWorkspace.setup(mDragController);
        // Until the workspace is bound, ensure that we keep the wallpaper offset locked to the
        // default state, otherwise we will update to the wrong offsets in RTL
        mWorkspace.lockWallpaperToDefaultPage();
        mWorkspace.bindAndInitFirstWorkspaceScreen(null /* recycled qsb */);
        mDragController.addDragListener(mWorkspace);

        // Get the search/delete/uninstall bar
        mDropTargetBar = mDragLayer.findViewById(R.id.drop_target_bar);

        // Setup Apps
        mAppsView = mRootView.findViewById(R.id.apps_view);

        // Setup Scrim
        mScrimView = mRootView.findViewById(R.id.scrim_view);

        // Setup the drag controller (drop targets have to be added in reverse order in priority)
        mDropTargetBar.setup(mDragController);

        mAllAppsController.setupViews(mAppsView, mScrimView);
    }

    private void updateNotificationDots(Predicate<PackageUserKey> updatedDots) {
        mWorkspace.updateNotificationDots(updatedDots);
        mAppsView.getAppsStore().updateNotificationDots(updatedDots);
    }

    public final LauncherRootView getRootView() {
        return mRootView;
    }

    public DragController getDragController() {
        return mDragController;
    }

    public TouchController[] createTouchControllers() {
        return new TouchController[] {getDragController(), new AllAppsSwipeController(this)};
    }

    public static Launcher getLauncher(Context context) {
        return fromContext(context);
    }

    public static <T extends BaseActivity> T fromContext(Context context) {
        if (context instanceof BaseActivity) {
            return (T) context;
        } else if (context instanceof ContextThemeWrapper) {
            return fromContext(((ContextWrapper) context).getBaseContext());
        } else {
            throw new IllegalArgumentException("Cannot find BaseActivity in parent tree");
        }
    }

    @Override
    public void clearPendingBinds() {

    }

    @Override
    public void bindAllApplications(AppInfo[] apps, int flags) {
        Log.d(TAG, "bindAllApplications "+apps.toString());
        mAppsView.getAppsStore().setApps(apps, flags);

    }

    @Override
    public void bindDeepShortcutMap(HashMap<ComponentKey, Integer> deepShortcutMapCopy) {
        mPopupDataProvider.setDeepShortcutMap(deepShortcutMapCopy);

    }

    @Override
    public void bindAllWidgets(ArrayList<WidgetListRowEntry> allWidgets) {
        mPopupDataProvider.setAllWidgets(allWidgets);

    }

    @Override
    public void onIdpChanged(int changeFlags, InvariantDeviceProfile profile) {

    }
}