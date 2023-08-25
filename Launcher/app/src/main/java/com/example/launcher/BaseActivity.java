package com.example.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.ContextThemeWrapper;

public class BaseActivity extends Activity {

    protected DeviceProfile mDeviceProfile;

    public DeviceProfile getDeviceProfile() {
        return mDeviceProfile;
    }

    public static <T extends BaseActivity> T fromContext(Context context) {
        if (context instanceof BaseActivity) {
            return (T) context;
        } else if (context instanceof ContextThemeWrapper) {
            return fromContext(((ContextWrapper) context).getBaseContext());
        } else {
            throw new IllegalArgumentException("Cannot find BaseActivity in parent tree");
        }
    }
}
