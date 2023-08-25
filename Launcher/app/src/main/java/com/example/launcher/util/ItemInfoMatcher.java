package com.example.launcher.util;

import android.content.ComponentName;
import android.os.UserHandle;

import com.example.launcher.model.ItemInfo;

public interface ItemInfoMatcher {

    boolean matches(ItemInfo info, ComponentName cn);


    static ItemInfoMatcher not(ItemInfoMatcher matcher) {
        return (info, cn) -> !matcher.matches(info, cn);
    }

    static ItemInfoMatcher ofUser(UserHandle user) {
        return (info, cn) -> info.user.equals(user);
    }
}
