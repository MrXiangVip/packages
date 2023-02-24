package com.example.launcher;

import android.app.Activity;

public class BaseActivity extends Activity {

    protected DeviceProfile mDeviceProfile;

    public DeviceProfile getDeviceProfile() {
        return mDeviceProfile;
    }
}
