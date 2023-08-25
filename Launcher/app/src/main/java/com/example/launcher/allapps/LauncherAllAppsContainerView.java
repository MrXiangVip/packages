package com.example.launcher.allapps;

import android.content.Context;
import android.util.AttributeSet;

import com.example.launcher.Launcher;

public class LauncherAllAppsContainerView extends  AllAppsContainerView{
    private final Launcher mLauncher;

    public LauncherAllAppsContainerView(Context context) {
        this(context, null);
    }

    public LauncherAllAppsContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LauncherAllAppsContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public LauncherAllAppsContainerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mLauncher = Launcher.getLauncher(context);

    }
}
