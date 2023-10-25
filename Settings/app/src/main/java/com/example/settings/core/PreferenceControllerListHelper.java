package com.example.settings.core;

import static com.example.settings.core.PreferenceXmlParserUtils.METADATA_CONTROLLER;
import static com.example.settings.core.PreferenceXmlParserUtils.METADATA_FOR_WORK;
import static com.example.settings.core.PreferenceXmlParserUtils.METADATA_KEY;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.example.settings.core.PreferenceXmlParserUtils.MetadataFlag;
import com.example.settings.settingslib.AbstractPreferenceController;

public class PreferenceControllerListHelper {

    private static String TAG="PreferenceControllerListHelper ";

    public static List<BasePreferenceController> getPreferenceControllersFromXml(Context context,
                                                                                 int xmlResId) {
        final List<BasePreferenceController> controllers = new ArrayList<>();
        List<Bundle> preferenceMetadata;
        try{
            preferenceMetadata = PreferenceXmlParserUtils.extractMetadata(context, xmlResId,
                    MetadataFlag.FLAG_NEED_KEY | MetadataFlag.FLAG_NEED_PREF_CONTROLLER
                            | MetadataFlag.FLAG_INCLUDE_PREF_SCREEN  | MetadataFlag.FLAG_FOR_WORK);
        }catch (Exception e){
            Log.d(TAG, "Failed to parse preference xml for getting controllers", e);
            return controllers;
        }
        for (Bundle metadata : preferenceMetadata) {
            final String controllerName = metadata.getString(METADATA_CONTROLLER);
            if (TextUtils.isEmpty(controllerName)) {
                continue;
            }
            BasePreferenceController controller;
            try {
                controller = BasePreferenceController.createInstance(context, controllerName);
            }catch (IllegalStateException e){
                final String key = metadata.getString(METADATA_KEY);
                final boolean isWorkProfile = metadata.getBoolean(METADATA_FOR_WORK, false);
                if (TextUtils.isEmpty(key)) {
                    Log.w(TAG, "Controller requires key but it's not defined in xml: "
                            + controllerName);
                    continue;
                }
                try {
                    controller = BasePreferenceController.createInstance(context, controllerName,
                            key, isWorkProfile);
                } catch (IllegalStateException e2) {
                    Log.w(TAG, "Cannot instantiate controller from reflection: " + controllerName);
                    continue;
                }
            }
            controllers.add(controller);
        }
        return  controllers;
    }

    public static List<BasePreferenceController> filterControllers(
            List<BasePreferenceController> input,
            List<AbstractPreferenceController> filter) {
        if (input == null || filter == null) {
            return input;
        }
        final Set<String> keys = new TreeSet<>();
        final List<BasePreferenceController> filteredList = new ArrayList<>();
        for (AbstractPreferenceController controller : filter) {
            final String key = controller.getPreferenceKey();
            if (key != null) {
                keys.add(key);
            }
        }
        for (BasePreferenceController controller : input) {
            if (keys.contains(controller.getPreferenceKey())) {
                Log.w(TAG, controller.getPreferenceKey() + " already has a controller");
                continue;
            }
            filteredList.add(controller);
        }
        return filteredList;
    }
}
