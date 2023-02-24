package com.example.launcher.allapps;

import com.example.launcher.Launcher;
import com.example.launcher.views.ScrimView;

public class AllAppsTransitionController {
    private AllAppsContainerView mAppsView;
    private ScrimView mScrimView;
    private final Launcher mLauncher;

    public void setupViews(AllAppsContainerView appsView, ScrimView scrimView) {
        mAppsView = appsView;
        mScrimView = scrimView;
//        PluginManagerWrapper.INSTANCE.get(mLauncher)
//                .addPluginListener(this, AllAppsSearchPlugin.class, false);
    }

    public AllAppsTransitionController(Launcher l) {
        mLauncher = l;
//        mShiftRange = mLauncher.getDeviceProfile().heightPx;
//        mProgress = 1f;

//        mIsVerticalLayout = mLauncher.getDeviceProfile().isVerticalBarLayout();
//        mLauncher.addOnDeviceProfileChangeListener(this);
    }
}
