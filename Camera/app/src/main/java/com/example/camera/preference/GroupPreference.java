package com.example.camera.preference;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.camerabg.R;

public class GroupPreference extends Preference {
    private  LayoutInflater inflater;

    public GroupPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
    }


    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        Log.i("zyq_custom_preference", "System_onCreateView");
        View mRootView = inflater.inflate(R.layout.group_preference_layout, null);
        return mRootView;
    }
    protected void onBindView(View view) {
        super.onBindView(view);
        Log.i("zyq_custom_preference","System_onBindView");
    }
}
