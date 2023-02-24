package com.example.camera.setting;

import android.preference.PreferenceFragment;
import android.util.Log;

import com.example.camera.ICameraSettingView;
import com.example.camerabg.R;
import com.example.camera.preference.XdfPictureSizePreference;

import java.util.ArrayList;
import java.util.List;

public class PictureSizeSettingView implements ICameraSettingView ,XdfPictureSizePreference.OnItemClickListener {
    private  final String TAG = "PictureSizeSettingView";
    private XdfPictureSizePreference mGroupPref;
    private String mSelectedValue;
    private List<String> mEntryValues = new ArrayList<>();
    @Override
    public void loadView(PreferenceFragment fragment) {
        fragment.addPreferencesFromResource(R.xml.xdf_picturesize_preference);
        mGroupPref = (XdfPictureSizePreference) fragment.findPreference("key_picture_size");
        mGroupPref.setRootPreference(fragment.getPreferenceScreen());
        mGroupPref.setOnItemClickListener(this);
        mGroupPref.setValue(mSelectedValue );
        mGroupPref.setEntryValues( mEntryValues );
        mGroupPref.setEnabled( true);

    }

    public void setValue(String value) {
        mSelectedValue = value;
    }

    /**
     * Set the picture sizes supported.
     *
     * @param entryValues The picture sizes supported.
     */
    public void setEntryValues(List<String> entryValues) {
        mEntryValues = entryValues;
    }

    @Override
    public void onItemClick(String value) {
        Log.d(TAG, "onItemClick");
    }
}
