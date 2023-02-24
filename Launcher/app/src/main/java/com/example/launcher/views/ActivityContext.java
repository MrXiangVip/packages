package com.example.launcher.views;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.ContextThemeWrapper;

import com.example.launcher.DeviceProfile;

public interface ActivityContext {

    static ActivityContext lookupContext(Context context) {
        if (context instanceof ActivityContext) {
            return (ActivityContext) context;
        } else if (context instanceof ContextThemeWrapper) {
            return lookupContext(((ContextWrapper) context).getBaseContext());
        } else {
            throw new IllegalArgumentException("Cannot find ActivityContext in parent tree");
        }
    }
    DeviceProfile getDeviceProfile();

}
