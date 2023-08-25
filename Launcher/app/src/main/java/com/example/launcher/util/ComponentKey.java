package com.example.launcher.util;

import android.content.ComponentName;
import android.os.UserHandle;

public class ComponentKey {
    public final ComponentName componentName;
    public final UserHandle user;

    public ComponentKey(ComponentName componentName, UserHandle user) {
        this.componentName = componentName;
        this.user = user;

    }
}
