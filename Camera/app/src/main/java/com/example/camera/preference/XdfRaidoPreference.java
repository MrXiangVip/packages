package com.example.camera.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;

public class XdfRaidoPreference extends Preference {


    public XdfRaidoPreference(Context context){
        this(context, null);
    }


    public XdfRaidoPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XdfRaidoPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }
    public XdfRaidoPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
