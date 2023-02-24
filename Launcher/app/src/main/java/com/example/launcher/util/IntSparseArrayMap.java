package com.example.launcher.util;

import android.util.SparseArray;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class IntSparseArrayMap<E> extends SparseArray<E> implements Iterable<E>{
    @NonNull
    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public void forEach(@NonNull Consumer<? super E> action) {
        Iterable.super.forEach(action);
    }

    @NonNull
    @Override
    public Spliterator<E> spliterator() {
        return Iterable.super.spliterator();
    }

    public boolean containsKey(int key) {
        return indexOfKey(key) >= 0;
    }

}
