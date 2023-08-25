package com.example.launcher.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;

public interface ResourceBasedOverride {

    class Overrides {
        private static final String TAG = "Overrides";
        public static <T extends ResourceBasedOverride> T getObject(
                Class<T> clazz, Context context, int resId) {
            String className = context.getString(resId);
            if (!TextUtils.isEmpty(className)) {
                try {
                    Class<?> cls = Class.forName(className);
                    return (T) cls.getDeclaredConstructor(Context.class).newInstance(context);
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | ClassCastException | NoSuchMethodException | InvocationTargetException e) {
                    Log.e(TAG, "Bad overriden class", e);
                }
            }
            try {
                return clazz.newInstance();
            } catch (InstantiationException|IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
