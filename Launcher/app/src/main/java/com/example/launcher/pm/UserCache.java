package com.example.launcher.pm;

import android.content.Context;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.ArrayMap;
import android.util.LongSparseArray;

import com.example.launcher.util.MainThreadInitializedObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserCache {
    public static final MainThreadInitializedObject<UserCache> INSTANCE =
            new MainThreadInitializedObject<>(UserCache::new);

    private final Context mContext;
    private final UserManager mUserManager;
    private LongSparseArray<UserHandle> mUsers;
    private ArrayMap<UserHandle, Long> mUserToSerialMap;

    private UserCache(Context context) {
        mContext = context;
        mUserManager = context.getSystemService(UserManager.class);
    }

    public List<UserHandle> getUserProfiles() {
        synchronized (this) {
            if (mUsers != null) {
                return new ArrayList<>(mUserToSerialMap.keySet());
            }
        }

        List<UserHandle> users = mUserManager.getUserProfiles();
        return users == null ? Collections.emptyList() : users;
    }
}
