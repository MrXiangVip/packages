package com.mediatek.hralauncher.util;

import android.os.Handler;
import android.os.HandlerThread;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class WorkThreadExecutor extends AbstractExecutorService {
    private HandlerThread sWorkThread;
    private final Handler sWorkHandler;

    public WorkThreadExecutor(String name) {
        sWorkThread = new HandlerThread(name);
        sWorkThread.start();
        sWorkHandler = new Handler(sWorkThread.getLooper());
    }
    @Override
    public void shutdown() {

    }

    @Override
    public List<Runnable> shutdownNow() {
        return null;
    }

    @Override
    public boolean isShutdown() {
        return false;
    }

    @Override
    public boolean isTerminated() {
        return false;
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void execute(Runnable command) {
        this.sWorkHandler.post( command );
    }
}
