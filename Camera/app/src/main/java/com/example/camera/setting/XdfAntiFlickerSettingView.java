package com.example.camera.setting;

import android.app.Activity;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.example.camera.ICameraSettingView;
import com.example.camera.preference.GroupPreference;
import com.example.camera.preference.XdfGroupButtonPreference;
import com.example.camera.preference.XdfPictureSizePreference;
import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class XdfAntiFlickerSettingView implements ICameraSettingView , XdfGroupButtonPreference.OnItemClickListener{
    private Activity mActivity;
    private String mKey;
    private XdfGroupButtonPreference mGroupPref;
    private String mSelectedValue="off";
    private boolean mEnabled;

    private List<String> mEntryValues = new ArrayList<>();
    private List<String> mOriginalEntryValues = new ArrayList<>();
    private String TAG="XdfAntiFlickerSettingView";

    public XdfAntiFlickerSettingView(Activity activity, String key) {
        mActivity = activity;
        mKey = key;

        initAntiFlickerEntryValues();
    }

    public void initAntiFlickerEntryValues( ){
        mEntryValues.clear();
        String[] originalEntryValuesInArray = mActivity.getResources()
                .getStringArray(R.array.anti_flicker_entryvalues);
        for (String value : originalEntryValuesInArray) {
            mOriginalEntryValues.add(value);
        }
        for (int i = 0; i < mOriginalEntryValues.size(); i++) {
            mEntryValues.add(mOriginalEntryValues.get(i));
        }
    }

    @Override
    public void loadView(PreferenceFragment fragment) {
        fragment.addPreferencesFromResource(R.xml.xdf_antiflinker_preference);
        mGroupPref = (XdfGroupButtonPreference) fragment.findPreference("anti_flinker");
        mGroupPref.setRootPreference(fragment.getPreferenceScreen());
        mGroupPref.setOnItemClickListener(this);
        mGroupPref.setSelectedValue(mSelectedValue );
        mGroupPref.setEntryValues( mEntryValues );
        mGroupPref.setEnabled( true);
    }

    @Override
    public boolean isEnabled() {
        return mEnabled;
    }

    @Override
    public void onItemClick(String value) {
        Log.d(TAG, ""+value);
    }
}
