package com.example.launcher.graphics;

import android.view.View;

import com.example.launcher.Launcher;
import com.example.launcher.uioverrides.WallpaperColorInfo;

public class Scrim {
    protected final View mRoot;
    protected final Launcher mLauncher;
//    protected final WallpaperColorInfo mWallpaperColorInfo;

    public Scrim(View view) {
        mRoot = view;
        mLauncher = Launcher.getLauncher(view.getContext());
//        mWallpaperColorInfo = WallpaperColorInfo.INSTANCE.get(mLauncher);
//        view.addOnAttachStateChangeListener(this);
    }
}
