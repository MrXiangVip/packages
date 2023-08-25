package com.example.launcher.views;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.ContextThemeWrapper;

import com.example.launcher.DeviceProfile;
import com.example.launcher.dot.DotInfo;
import com.example.launcher.model.ItemInfo;

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
    default DotInfo getDotInfoForItem(ItemInfo info) {
        return null;
    }

    DeviceProfile getDeviceProfile();

}
