package com.mediatek.hralauncher.util;

public class LauncherExecutors {
    public static final WorkThreadExecutor sAppLoaderExecutor = new WorkThreadExecutor("launcher-applist");
    public static final MainThreadExecutor sMainExecutor = new MainThreadExecutor();
}
