package com.example.launcher.config;

public class DeviceFlag extends FeatureFlags.DebugFlag {

    public DeviceFlag(String key, boolean defaultValue, String description) {
        super(key, defaultValue, description);
    }
}
