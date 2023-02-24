package com.example.launcher.util;

public class IntArray implements Cloneable{
    private static final int MIN_CAPACITY_INCREMENT = 12;

    private static final int[] EMPTY_INT = new int[0];
    /* package private */ int[] mValues;
    /* package private */ int mSize;

    private  IntArray(int[] array, int size) {
        mValues = array;
        mSize = size;
    }

    public IntArray() {
        this(10);
    }

    public IntArray(int initialCapacity) {
        if (initialCapacity == 0) {
            mValues = EMPTY_INT;
        } else {
            mValues = new int[initialCapacity];
        }
        mSize = 0;
    }

    public void add(int value) {
        add(mSize, value);
    }
    public void add(int index, int value) {
        ensureCapacity(1);
        int rightSegment = mSize - index;
        mSize++;
        checkBounds(mSize, index);

        if (rightSegment != 0) {
            // Move by 1 all values from the right of 'index'
            System.arraycopy(mValues, index, mValues, index + 1, rightSegment);
        }

        mValues[index] = value;
    }
    private void ensureCapacity(int count) {
        final int currentSize = mSize;
        final int minCapacity = currentSize + count;
        if (minCapacity >= mValues.length) {
            final int targetCap = currentSize + (currentSize < (MIN_CAPACITY_INCREMENT / 2) ?
                    MIN_CAPACITY_INCREMENT : currentSize >> 1);
            final int newCapacity = targetCap > minCapacity ? targetCap : minCapacity;
            final int[] newValues = new int[newCapacity];
            System.arraycopy(mValues, 0, newValues, 0, currentSize);
            mValues = newValues;
        }
    }

    private static void checkBounds(int len, int index) {
        if (index < 0 || len <= index) {
            throw new ArrayIndexOutOfBoundsException("length=" + len + "; index=" + index);
        }
    }
}
