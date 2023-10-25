package com.example.settings.core;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;

import com.example.settings.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreferenceXmlParserUtils {

    public @interface MetadataFlag {

        int FLAG_INCLUDE_PREF_SCREEN = 1;
        int FLAG_NEED_KEY = 1 << 1;
        int FLAG_NEED_PREF_TYPE = 1 << 2;
        int FLAG_NEED_PREF_CONTROLLER = 1 << 3;
        int FLAG_NEED_PREF_TITLE = 1 << 4;
        int FLAG_NEED_PREF_SUMMARY = 1 << 5;
        int FLAG_NEED_PREF_ICON = 1 << 6;
        int FLAG_NEED_KEYWORDS = 1 << 8;
        int FLAG_NEED_SEARCHABLE = 1 << 9;
        int FLAG_NEED_PREF_APPEND = 1 << 10;
        int FLAG_UNAVAILABLE_SLICE_SUBTITLE = 1 << 11;
        int FLAG_FOR_WORK = 1 << 12;
    }
    public static final String METADATA_PREF_TYPE = "type";
    public static final String METADATA_KEY = "key";
    public static final String METADATA_CONTROLLER = "controller";
    public static final String METADATA_TITLE = "title";
    public static final String METADATA_SUMMARY = "summary";
    public static final String METADATA_ICON = "icon";
    public static final String METADATA_KEYWORDS = "keywords";
    public static final String METADATA_SEARCHABLE = "searchable";
    public static final String METADATA_APPEND = "staticPreferenceLocation";
    public static final String METADATA_UNAVAILABLE_SLICE_SUBTITLE = "unavailable_slice_subtitle";
    public static final String METADATA_FOR_WORK = "for_work";

    public static List<Bundle> extractMetadata(Context context,  int xmlResId, int flags)
            throws IOException, XmlPullParserException {
        final List<Bundle> metadata = new ArrayList<>();
        final XmlResourceParser parser = context.getResources().getXml(xmlResId);
        int type;
        while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                && type != XmlPullParser.START_TAG) {
            // Parse next until start tag is found
        }
        final int outerDepth = parser.getDepth();
        do{
            if (type != XmlPullParser.START_TAG) {
                continue;
            }
            final String nodeName = parser.getName();
            final Bundle preferenceMetadata = new Bundle();
            final AttributeSet attrs = Xml.asAttributeSet(parser);

            final TypedArray preferenceAttributes = context.obtainStyledAttributes(attrs,
                    R.styleable.Preference);


            if (hasFlag(flags, MetadataFlag.FLAG_NEED_PREF_TYPE)) {
                preferenceMetadata.putString(METADATA_PREF_TYPE, nodeName);
            }
            if (hasFlag(flags, MetadataFlag.FLAG_NEED_KEY)) {
                preferenceMetadata.putString(METADATA_KEY, getKey(preferenceAttributes));
            }
            if (hasFlag(flags, MetadataFlag.FLAG_NEED_PREF_CONTROLLER)) {
                preferenceMetadata.putString(METADATA_CONTROLLER,
                        getController(preferenceAttributes));
            }
            if (hasFlag(flags, MetadataFlag.FLAG_NEED_PREF_TITLE)) {
                preferenceMetadata.putString(METADATA_TITLE, getTitle(preferenceAttributes));
            }
            if (hasFlag(flags, MetadataFlag.FLAG_NEED_PREF_SUMMARY)) {
                preferenceMetadata.putString(METADATA_SUMMARY, getSummary(preferenceAttributes));
            }

            if (hasFlag(flags, MetadataFlag.FLAG_NEED_KEYWORDS)) {
                preferenceMetadata.putString(METADATA_KEYWORDS, getKeywords(preferenceAttributes));
            }


            metadata.add(preferenceMetadata);
        }while ((type = parser.next()) != XmlPullParser.END_DOCUMENT
                && (type != XmlPullParser.END_TAG || parser.getDepth() > outerDepth));
        parser.close();
        return  metadata;
    }
    private static boolean hasFlag(int flags, @MetadataFlag int flag) {
        return (flags & flag) != 0;
    }
    private static String getKey(TypedArray styledAttributes) {
        return styledAttributes.getString(R.styleable.Preference_key);
    }

    private static String getTitle(TypedArray styledAttributes) {
        return styledAttributes.getString(R.styleable.Preference_title);
    }

    private static String getSummary(TypedArray styledAttributes) {
        return styledAttributes.getString(R.styleable.Preference_summary);
    }

    private static String getController(TypedArray styledAttributes) {
        return styledAttributes.getString(R.styleable.Preference_controller);
    }


    private static String getKeywords(TypedArray styledAttributes) {
        return styledAttributes.getString(R.styleable.Preference_keywords);
    }


}
