package com.example.launcher.util;

import android.os.Trace;

import androidx.annotation.MainThread;

import java.util.function.Supplier;

public class TraceHelper {
    // Track binder class for this trace
    public static final int FLAG_ALLOW_BINDER_TRACKING = 1 << 0;

    // Temporarily ignore blocking binder calls for this trace.
    public static final int FLAG_IGNORE_BINDERS = 1 << 1;

    public static final int FLAG_CHECK_FOR_RACE_CONDITIONS = 1 << 2;

    public static final int FLAG_UI_EVENT =
            FLAG_ALLOW_BINDER_TRACKING | FLAG_CHECK_FOR_RACE_CONDITIONS;

    /**
     * Static instance of Trace helper, overridden in tests.
     */
    public static TraceHelper INSTANCE = new TraceHelper();

    /**
     * @return a token to pass into {@link #endSection(Object)}.
     */
    public Object beginSection(String sectionName) {
        return beginSection(sectionName, 0);
    }

    public Object beginSection(String sectionName, int flags) {
        Trace.beginSection(sectionName);
        return null;
    }

    /**
     * @param token the token returned from {@link #beginSection(String, int)}
     */
    public void endSection(Object token) {
        Trace.endSection();
    }

    /**
     * Similar to {@link #beginSection} but doesn't add a trace section.
     */
    public Object beginFlagsOverride(int flags) {
        return null;
    }

    public void endFlagsOverride(Object token) { }

    /**
     * Temporarily ignore blocking binder calls for the duration of this {@link Supplier}.
     */
    @MainThread
    public static <T> T whitelistIpcs(String rpcName, Supplier<T> supplier) {
        Object traceToken = INSTANCE.beginSection(rpcName, FLAG_IGNORE_BINDERS);
        try {
            return supplier.get();
        } finally {
            INSTANCE.endSection(traceToken);
        }
    }
}
