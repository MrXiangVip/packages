package com.example.settings.core;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.XmlRes;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static com.example.settings.core.PreferenceXmlParserUtils.METADATA_CONTROLLER;
import static com.example.settings.core.PreferenceXmlParserUtils.METADATA_FOR_WORK;
import static com.example.settings.core.PreferenceXmlParserUtils.METADATA_KEY;

import com.example.settings.core.PreferenceXmlParserUtils.MetadataFlag;

public class PreferenceControllerListHelper {

    private static final String TAG = "PrefCtrlListHelper";

    public static List<BasePreferenceController> getPreferenceControllersFromXml(Context context,
                                                                                 @XmlRes int xmlResId) {
        final List<BasePreferenceController> controllers = new ArrayList<>();
        List<Bundle> preferenceMetadata;
        try {
            preferenceMetadata = PreferenceXmlParserUtils.extractMetadata(context, xmlResId,
                    MetadataFlag.FLAG_NEED_KEY | PreferenceXmlParserUtils.MetadataFlag.FLAG_NEED_PREF_CONTROLLER
                            | MetadataFlag.FLAG_INCLUDE_PREF_SCREEN  | MetadataFlag.FLAG_FOR_WORK);
        } catch (IOException | XmlPullParserException e) {
            Log.e(TAG, "Failed to parse preference xml for getting controllers", e);
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
            } catch (IllegalStateException e) {
                //Log.d(TAG, "Could not find Context-only controller for pref: " + controllerName);
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
        return controllers;
    }
}
