package com.example.settings.core;

import android.content.Context;
import android.os.UserHandle;
import android.text.TextUtils;

import com.example.settings.settingslib.AbstractPreferenceController;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class BasePreferenceController extends AbstractPreferenceController {
    private boolean mIsForWork;
    private UserHandle mWorkProfileUser;
    protected final String mPreferenceKey;

    public BasePreferenceController(Context context, String preferenceKey) {
        super(context);
        mPreferenceKey = preferenceKey;
        if (TextUtils.isEmpty(mPreferenceKey)) {
            throw new IllegalArgumentException("Preference key must be set");
        }
    }
    public static BasePreferenceController createInstance(Context context, String controllerName) {
        try {
            final Class<?> clazz = Class.forName(controllerName);
            final Constructor<?> preferenceConstructor = clazz.getConstructor(Context.class);
            final Object[] params = new Object[]{context};
            return (BasePreferenceController) preferenceConstructor.newInstance(params);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException |
                IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(
                    "Invalid preference controller: " + controllerName, e);
        }
    }

    public static BasePreferenceController createInstance(Context context, String controllerName,
                                                          String key, boolean isWorkProfile) {
        try {
            final Class<?> clazz = Class.forName(controllerName);
            final Constructor<?> preferenceConstructor =
                    clazz.getConstructor(Context.class, String.class);
            final Object[] params = new Object[]{context, key};
            final BasePreferenceController controller =
                    (BasePreferenceController) preferenceConstructor.newInstance(params);
//            controller.setForWork(isWorkProfile);
            return controller;
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException
                | IllegalArgumentException | InvocationTargetException | IllegalAccessException e) {
            throw new IllegalStateException(
                    "Invalid preference controller: " + controllerName, e);
        }
    }

    @Override
    public String getPreferenceKey() {
        return mPreferenceKey;
    }

/*    void setForWork(boolean forWork) {
        mIsForWork = forWork;
        if (mIsForWork) {
            mWorkProfileUser = Utils.getManagedProfile(UserManager.get(mContext));
        }
    }*/

}
