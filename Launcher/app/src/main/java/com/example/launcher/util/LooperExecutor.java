package com.example.launcher.util;

import android.os.Handler;
import android.os.Looper;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

public class LooperExecutor extends AbstractExecutorService {
    private final Handler mHandler;

    public LooperExecutor(Looper looper) {
        mHandler = new Handler(looper);
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
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void execute(Runnable runnable) {
        if (getHandler().getLooper() == Looper.myLooper()) {
            runnable.run();
        } else {
            getHandler().post(runnable);
        }
    }

    public void post(Runnable runnable) {
        getHandler().post(runnable);
    }
}
