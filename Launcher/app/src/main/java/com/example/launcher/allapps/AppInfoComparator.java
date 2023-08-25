package com.example.launcher.allapps;

import android.content.Context;
import android.os.Process;
import android.os.UserHandle;

import com.example.launcher.model.AppInfo;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class AppInfoComparator implements Comparator<AppInfo> {
    private final UserHandle mMyUser;

    public AppInfoComparator(Context context) {
//        mUserManager = UserCache.INSTANCE.get(context);
        mMyUser = Process.myUserHandle();
//        mLabelComparator = new LabelComparator();
    }

    @Override
    public int compare(AppInfo o1, AppInfo o2) {
        return 0;
    }

    @Override
    public Comparator<AppInfo> reversed() {
        return Comparator.super.reversed();
    }

    @Override
    public Comparator<AppInfo> thenComparing(Comparator<? super AppInfo> other) {
        return Comparator.super.thenComparing(other);
    }

    @Override
    public <U> Comparator<AppInfo> thenComparing(Function<? super AppInfo, ? extends U> keyExtractor, Comparator<? super U> keyComparator) {
        return Comparator.super.thenComparing(keyExtractor, keyComparator);
    }

    @Override
    public <U extends Comparable<? super U>> Comparator<AppInfo> thenComparing(Function<? super AppInfo, ? extends U> keyExtractor) {
        return Comparator.super.thenComparing(keyExtractor);
    }

    @Override
    public Comparator<AppInfo> thenComparingInt(ToIntFunction<? super AppInfo> keyExtractor) {
        return Comparator.super.thenComparingInt(keyExtractor);
    }

    @Override
    public Comparator<AppInfo> thenComparingLong(ToLongFunction<? super AppInfo> keyExtractor) {
        return Comparator.super.thenComparingLong(keyExtractor);
    }

    @Override
    public Comparator<AppInfo> thenComparingDouble(ToDoubleFunction<? super AppInfo> keyExtractor) {
        return Comparator.super.thenComparingDouble(keyExtractor);
    }
}
