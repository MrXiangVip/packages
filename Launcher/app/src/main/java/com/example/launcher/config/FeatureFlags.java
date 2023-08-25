package com.example.launcher.config;

import com.example.launcher.BuildConfig;

public final class FeatureFlags {

    public static final boolean QSB_ON_FIRST_SCREEN = true;
    public static final boolean IS_STUDIO_BUILD = BuildConfig.DEBUG;

    public static final BooleanFlag FOLDER_NAME_SUGGEST = new DeviceFlag(
            "FOLDER_NAME_SUGGEST", true,
            "Suggests folder names instead of blank text.");


    public static class BooleanFlag {
        public final String key;
        public boolean defaultValue;
        public BooleanFlag(String key, boolean defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public boolean get() {
            return defaultValue;
        }
    }

    public static class DebugFlag extends BooleanFlag {
        public final String description;

        public DebugFlag(String key, boolean defaultValue, String description) {
            super(key, defaultValue);
            this.description = description;

        }
    }

}