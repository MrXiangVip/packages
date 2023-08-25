package com.example.launcher.util;

import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;

public class Executors {

    public static final LooperExecutor MAIN_EXECUTOR =
            new LooperExecutor(Looper.getMainLooper());

    public static final LooperExecutor MODEL_EXECUTOR =
            new LooperExecutor(createAndStartNewLooper("launcher-loader"));
    public static Looper createAndStartNewLooper(String name) {
        return createAndStartNewLooper(name, Process.THREAD_PRIORITY_DEFAULT);
    }
    public static Looper createAndStartNewLooper(String name, int priority) {
        HandlerThread thread = new HandlerThread(name, priority);
        thread.start();
        return thread.getLooper();
    }
}
