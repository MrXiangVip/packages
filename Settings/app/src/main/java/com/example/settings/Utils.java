package com.example.settings;

import android.os.UserHandle;
import android.os.UserManager;

import java.util.List;

public class Utils {


    public static UserHandle getManagedProfile(UserManager userManager) {
        final List<UserHandle> userProfiles = userManager.getUserProfiles();
        for (UserHandle profile : userProfiles) {
//            if (profile.getIdentifier() == userManager.getUserHandle()) {
//                continue;
//            }
//            final UserInfo userInfo = userManager.getUserInfo(profile.getIdentifier());
//            if (userInfo.isManagedProfile()) {
//                return profile;
//            }
        }
        return null;
    }
}
