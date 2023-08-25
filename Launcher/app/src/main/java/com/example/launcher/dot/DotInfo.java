package com.example.launcher.dot;

public class DotInfo {
    private int mTotalCount;
    public static final int MAX_COUNT = 999;

    public int getNotificationCount() {
        return Math.min(mTotalCount, MAX_COUNT);
    }
}
