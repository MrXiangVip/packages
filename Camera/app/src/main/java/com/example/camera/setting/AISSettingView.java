package com.example.camera.setting;

import android.app.Activity;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.camera.ICameraSettingView;
import com.example.camerabg.R;
import com.example.camera.preference.XdfSwitchPreference;


public class AISSettingView implements ICameraSettingView,  XdfSwitchPreference.OnLayoutClickListener{
    private static final String KEY_AIS = "key_ais";

    private SwitchPreference  originPref;
    private SwitchPreference  mPref;

    private XdfSwitchPreference mPreference;

    private Activity mActivity;
    private String mKey;
    private String TAG="AISSettingView";

    public AISSettingView(Activity activity, String key) {
        mActivity = activity;
        mKey = key;
    }

    @Override
    public void loadView(PreferenceFragment fragment) {
        fragment.addPreferencesFromResource(R.xml.ais_preference);
        mPref = (SwitchPreference) fragment.findPreference(KEY_AIS);
//        mPref.setEnabled(true);
//        mPref.setOnItemClickListener()
        mPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });
        originPref = (SwitchPreference) fragment.findPreference("origin_ais");
        originPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                return false;
            }
        });

        mPreference = new XdfSwitchPreference( mActivity );
//        mPreference.setRootPreference(fragment.getPreferenceScreen());
//        mPreference.setTitle(" fang dou  ");
//        mPreference.setOnLayoutClickListener( this);
//        mPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(Preference preference) {
//                return false;
//            }
//        });
//        mPreference.setEnabled( true);
//        mPreference =(XdfSwitchPreference) fragment.findPreference("self_ais");
        mPreference.setRootPreference(fragment.getPreferenceScreen());
        mPreference.setEnabled( true);
        mPreference.setTitle("AIS");
        mPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Toast.makeText(mActivity.getBaseContext(), "AIS", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void onLayoutClick(View view) {
            Log.d(TAG, "view " +view);
    //        if( view instanceof )
    }


}
