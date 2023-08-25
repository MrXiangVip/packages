package com.example.launcher.util;

import static com.example.launcher.util.Executors.MAIN_EXECUTOR;

import android.content.Context;
import android.os.Looper;

import java.util.concurrent.ExecutionException;

public class MainThreadInitializedObject<T> {

    private final ObjectProvider<T> mProvider;
    private T mValue;

    public MainThreadInitializedObject(ObjectProvider<T> provider) {
        mProvider = provider;
    }



    public T get(Context context) {
//        if (context instanceof PreviewContext) {
//            return ((PreviewContext) context).getObject(this, mProvider);
//        }

        if (mValue == null) {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                mValue = TraceHelper.whitelistIpcs("main.thread.object",
                        () -> mProvider.get(context.getApplicationContext()));
            } else {
                try {
                    return MAIN_EXECUTOR.submit(() -> get(context)).get();
                } catch (InterruptedException| ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return mValue;
    }

    public interface ObjectProvider<T> {

        T get(Context context);
    }
}
