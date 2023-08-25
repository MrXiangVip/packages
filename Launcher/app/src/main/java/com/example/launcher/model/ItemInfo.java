package com.example.launcher.model;

import android.content.ComponentName;
import android.content.Intent;
import android.os.UserHandle;

import java.util.Optional;

public class ItemInfo {
    private ComponentName mComponentName;
    public CharSequence contentDescription;
    public UserHandle user;

    public Intent getIntent() {
        return null;
    }

    public ComponentName getTargetComponent() {
        return Optional.ofNullable(getIntent()).map(Intent::getComponent).orElse(mComponentName);
    }

    public boolean isDisabled() {
        return false;
    }

}
