package com.example.launcher.util;

import java.util.Arrays;

public class IntSet {
    final IntArray mArray = new IntArray();

    public IntArray getArray() {
        return mArray;
    }

    public static IntSet wrap(IntArray array) {
        IntSet set = new IntSet();
        set.mArray.addAll(array);
        Arrays.sort(set.mArray.mValues, 0, set.mArray.mSize);
        return set;
    }
}
