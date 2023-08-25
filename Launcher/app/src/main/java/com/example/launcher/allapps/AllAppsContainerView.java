package com.example.launcher.allapps;

import static com.example.launcher.model.BgDataModel.Callbacks.FLAG_HAS_SHORTCUT_PERMISSION;
import static com.example.launcher.model.BgDataModel.Callbacks.FLAG_QUIET_MODE_CHANGE_PERMISSION;
import static com.example.launcher.model.BgDataModel.Callbacks.FLAG_QUIET_MODE_ENABLED;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Process;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.launcher.BaseDraggingActivity;
import com.example.launcher.R;
import com.example.launcher.Utilities;
import com.example.launcher.model.AppInfo;
import com.example.launcher.util.ItemInfoMatcher;
import com.example.launcher.views.SpringRelativeLayout;

public class AllAppsContainerView extends SpringRelativeLayout {

    private final AllAppsStore mAllAppsStore = new AllAppsStore();
    private final ItemInfoMatcher mPersonalMatcher = ItemInfoMatcher.ofUser(Process.myUserHandle());
    private final ItemInfoMatcher mWorkMatcher = ItemInfoMatcher.not(mPersonalMatcher);
    protected boolean mUsingTabs;
    protected final AdapterHolder[] mAH;
    private AllAppsPagedView mViewPager;
    private FloatingHeaderView mHeader;
    private WorkModeSwitch mWorkModeSwitch;
    protected final BaseDraggingActivity mLauncher;
    Rect mInsets = new Rect();


    private String TAG="AllAppsContainerView";

    public AllAppsContainerView(Context context) {
        this(context, null);
    }

    public AllAppsContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AllAppsContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AllAppsContainerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mLauncher = BaseDraggingActivity.fromContext(context);

        mAH = new AdapterHolder[2];
        mAH[AdapterHolder.MAIN] = new AdapterHolder(false /* isWork */);
        mAH[AdapterHolder.WORK] = new AdapterHolder(true /* isWork */);

        mAllAppsStore.addUpdateListener(this::onAppsUpdated);
        addSpringView(R.id.all_apps_header);
        addSpringView(R.id.apps_list_view);
        addSpringView(R.id.all_apps_tabs_view_pager);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        mHeader = findViewById(R.id.all_apps_header);
        rebindAdapters(mUsingTabs, true /* force */);

    }

    private void onAppsUpdated() {
        Log.d(TAG, "onAppsUpdated");
        boolean hasWorkApps = false;
        for (AppInfo app : mAllAppsStore.getApps()) {
            if (mWorkMatcher.matches(app, null)) {
                hasWorkApps = true;
                break;
            }
        }
        rebindAdapters(hasWorkApps);
        if (hasWorkApps) {
            resetWorkProfile();
        }
    }

    private void rebindAdapters(boolean showTabs) {
        rebindAdapters(showTabs, false /* force */);
    }

    protected void rebindAdapters(boolean showTabs, boolean force) {
        Log.d(TAG, "rebindAdapters");
        if (showTabs == mUsingTabs && !force) {
            return;
        }
        replaceRVContainer(showTabs);
        mUsingTabs = showTabs;

        mAllAppsStore.unregisterIconContainer(mAH[AdapterHolder.MAIN].recyclerView);
        mAllAppsStore.unregisterIconContainer(mAH[AdapterHolder.WORK].recyclerView);

        if (mUsingTabs) {
            setupWorkToggle();
            mAH[AdapterHolder.MAIN].setup(mViewPager.getChildAt(0), mPersonalMatcher);
            mAH[AdapterHolder.WORK].setup(mViewPager.getChildAt(1), mWorkMatcher);
            mViewPager.getPageIndicator().setActiveMarker(AdapterHolder.MAIN);
            findViewById(R.id.tab_personal)
                    .setOnClickListener((View view) -> mViewPager.snapToPage(AdapterHolder.MAIN));
            findViewById(R.id.tab_work)
                    .setOnClickListener((View view) -> mViewPager.snapToPage(AdapterHolder.WORK));
            onTabChanged(mViewPager.getNextPage());
        } else {
            mAH[AdapterHolder.MAIN].setup(findViewById(R.id.apps_list_view), null);
            mAH[AdapterHolder.WORK].recyclerView = null;
            if (mWorkModeSwitch != null) {
                ((ViewGroup) mWorkModeSwitch.getParent()).removeView(mWorkModeSwitch);
                mWorkModeSwitch = null;
            }
        }
        setupHeader();

        mAllAppsStore.registerIconContainer(mAH[AdapterHolder.MAIN].recyclerView);
        mAllAppsStore.registerIconContainer(mAH[AdapterHolder.WORK].recyclerView);
    }

    public void setupHeader() {
        mHeader.setVisibility(View.VISIBLE);
        mHeader.setup(mAH, mAH[AllAppsContainerView.AdapterHolder.WORK].recyclerView == null);

        int padding = mHeader.getMaxTranslation();
        for (int i = 0; i < mAH.length; i++) {
            mAH[i].padding.top = padding;
            mAH[i].applyPadding();
        }
    }

    private void resetWorkProfile() {
        mWorkModeSwitch.update(!mAllAppsStore.hasModelFlag(FLAG_QUIET_MODE_ENABLED));
        mAH[AdapterHolder.WORK].setupOverlay();
        mAH[AdapterHolder.WORK].applyPadding();
    }

    private void replaceRVContainer(boolean showTabs) {
        for (int i = 0; i < mAH.length; i++) {
            if (mAH[i].recyclerView != null) {
                mAH[i].recyclerView.setLayoutManager(null);
            }
        }
        View oldView = getRecyclerViewContainer();
        int index = indexOfChild(oldView);
        removeView(oldView);
        int layout = showTabs ? R.layout.all_apps_tabs : R.layout.all_apps_rv_layout;
        View newView = getLayoutInflater().inflate(layout, this, false);
        addView(newView, index);
        if (showTabs) {
            mViewPager = (AllAppsPagedView) newView;
            mViewPager.initParentViews(this);
            mViewPager.getPageIndicator().setContainerView(this);
        } else {
            mViewPager = null;
        }
    }

    public void onTabChanged(int pos) {
        mHeader.setMainActive(pos == 0);
        if (mAH[pos].recyclerView != null) {
            mAH[pos].recyclerView.bindFastScrollbar();
        }
        reset(true /* animate */);
        if (mWorkModeSwitch != null) {
            mWorkModeSwitch.setWorkTabVisible(pos == AdapterHolder.WORK
                    && mAllAppsStore.hasModelFlag(
                    FLAG_HAS_SHORTCUT_PERMISSION | FLAG_QUIET_MODE_CHANGE_PERMISSION));
        }
    }

    public LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }

    private void setupWorkToggle() {

    }

    public View getRecyclerViewContainer() {
        return mViewPager != null ? mViewPager : findViewById(R.id.apps_list_view);
    }
    public void reset(boolean animate) {

    }
    public AllAppsStore getAppsStore() {
        return mAllAppsStore;
    }

    public class AdapterHolder {
        public static final int MAIN = 0;
        public static final int WORK = 1;

        AllAppsRecyclerView     recyclerView;
        private final boolean   mIsWork;
        private ItemInfoMatcher mInfoMatcher;
        final Rect padding = new Rect();
        public final AllAppsGridAdapter adapter;
        final AlphabeticalAppsList appsList;
        final LinearLayoutManager layoutManager;

        AdapterHolder(boolean isWork) {
            mIsWork = isWork;
            appsList = new AlphabeticalAppsList(mLauncher, mAllAppsStore, isWork);
            adapter = new AllAppsGridAdapter(mLauncher, getLayoutInflater(), appsList);
            appsList.setAdapter(adapter);
            layoutManager = adapter.getLayoutManager();
        }

        void setup(@NonNull View rv, @Nullable ItemInfoMatcher matcher) {
            mInfoMatcher = matcher;
            recyclerView = (AllAppsRecyclerView) rv;
            recyclerView.setApps(appsList);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            applyPadding();
            setupOverlay();

        }

        void applyPadding() {
            if (recyclerView != null) {
                Resources res = getResources();
                int switchH = res.getDimensionPixelSize(R.dimen.work_profile_footer_padding) * 2
                        + mInsets.bottom + Utilities.calculateTextHeight(
                        res.getDimension(R.dimen.work_profile_footer_text_size));

                int bottomOffset = mWorkModeSwitch != null && mIsWork ? switchH : 0;
                recyclerView.setPadding(padding.left, padding.top, padding.right,
                        padding.bottom + bottomOffset);
            }
        }
        void setupOverlay() {
        }
    }
}