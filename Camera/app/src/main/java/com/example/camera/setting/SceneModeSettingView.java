package com.example.camera.setting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;

import com.example.camera.ICameraSettingView;
import com.example.camerabg.R;
import com.example.camera.preference.IScenceModeRecyclerItemClickListener;
import com.example.camera.preference.SummaryPreference;

import java.util.ArrayList;
import java.util.List;

public class SceneModeSettingView implements ICameraSettingView, SummaryPreference.OnLayoutClickListener, IScenceModeRecyclerItemClickListener {
    private SummaryPreference mPreference;
    private XdfTabHostFragment xdfTabHostFragment;

    private List<String> mOriginalEntries = new ArrayList<>();
    private List<String> mOriginalEntryValues = new ArrayList<>();
    private List<Integer> mOriginalIcons = new ArrayList<>();

    private List<String> mEntries = new ArrayList<>();
    private List<String> mEntryValues = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();

    private String mSelectedValue = null;
    private String mSummary = null;

    private Activity mActivity;
    private String mKey;
    private OnValueChangeListener mListener;
    private String TAG="MyCamera.SceneModeSettingView";



    /**
     * Listener to listen picture size value changed.
     */
    public interface OnValueChangeListener {
        /**
         * Callback when picture size value changed.
         *
         * @param value The changed picture size, such as "1920x1080".
         */
        void onValueChanged(String value);
    }

    public SceneModeSettingView(Activity activity, String key) {
        mActivity = activity;
        mKey = key;

        String[] originalEntriesInArray = mActivity.getResources()
                .getStringArray(R.array.scene_mode_entries);
        String[] originalEntryValuesInArray = mActivity.getResources()
                .getStringArray(R.array.scene_mode_entryvalues);

        TypedArray array = mActivity.getResources().obtainTypedArray(R.array.scene_mode_icons);
        int n = array.length();
        int[] originalIconsInArray = new int[n];
        for (int i = 0; i < n; ++i) {
            originalIconsInArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();

        for (String value : originalEntriesInArray) {
            mOriginalEntries.add(value);
        }
        for (String value : originalEntryValuesInArray) {
            mOriginalEntryValues.add(value);
        }
        for (int icon : originalIconsInArray) {
            mOriginalIcons.add(icon);
        }
    }
    @Override
    public void loadView(PreferenceFragment fragment) {
        Log.d(TAG, "loadView");
        fragment.addPreferencesFromResource(R.xml.xdf_scene_mode_preference);
        mPreference = (SummaryPreference) fragment.getPreferenceManager().findPreference("key_scene_mode");
        mPreference.setRootPreference(fragment.getPreferenceScreen());
//        mPreference.setId(R.id.white_balance_setting);
//        mPreference.setContentDescription(fragment.getActivity().getResources()
//                .getString(R.string.pref_camera_whitebalance_content_description));
        mPreference.setSummary("summary");
        mPreference.setOnLayoutClickListener( this );

        mPreference.setEnabled(true);

        if (xdfTabHostFragment == null) {
            xdfTabHostFragment = new XdfTabHostFragment();
            xdfTabHostFragment.setOnSconceModeRecylerItemClickListener( this );
        }
        xdfTabHostFragment.setScenceModeSelectedValue(mSelectedValue);
        xdfTabHostFragment.setScenceModeEntriesAndEntryValues(mEntries, mEntryValues, mIcons);
    }

    @Override
    public void onLayoutClick(View view) {
        Log.d(TAG, "onLayoutClick "+view+" "+mActivity.getFragmentManager().getFragments().size());
        if (xdfTabHostFragment == null) {
            xdfTabHostFragment = new XdfTabHostFragment();
        }
        xdfTabHostFragment.setCurrentIndex( 0 );
        xdfTabHostFragment.setOnSconceModeRecylerItemClickListener( this );
        xdfTabHostFragment.setScenceModeSelectedValue(mSelectedValue);
        xdfTabHostFragment.setScenceModeEntriesAndEntryValues(mEntries, mEntryValues, mIcons);

        FragmentTransaction transaction = mActivity.getFragmentManager()
                .beginTransaction();
//        Fragment fragment = mActivity.getFragmentManager().findFragmentById( R.id.setting_container);
//        transaction.hide( fragment );
//        fragment = mActivity.getFragmentManager().findFragmentById( R.id.setting_tail);
//        fragment = mActivity.getFragmentManager().findFragmentById( R.id.setting_container);

        Fragment fragment = mActivity.getFragmentManager().findFragmentById( R.id.setting_container);
        transaction.hide( fragment );
        transaction.replace(R.id.setting_tail, xdfTabHostFragment, "scene_mode_fragment");
        transaction.addToBackStack(null);
        transaction.commit();
        Log.d(TAG, "onLayoutClick replace setting_tail");


        Log.d(TAG, "onLayoutClick end "+view+" "+ mActivity.getFragmentManager().getFragments().size());

    }
    public void setEntryValues(List<String> entryValues) {
        mEntries.clear();
        mEntryValues.clear();
        mIcons.clear();

        for (int i = 0; i < mOriginalEntryValues.size(); i++) {
            mEntryValues.add(mOriginalEntryValues.get(i));
            mEntries.add(mOriginalEntries.get(i));
            mIcons.add(mOriginalIcons.get(i));
        }
    }
    public void setValue(String value) {
        Log.d(TAG, "setValue "+value);
        mSelectedValue = value;
        if ("hdr".equals(mSelectedValue)) {
            mSelectedValue = "auto";
        }
        int index = mEntryValues.indexOf(mSelectedValue);
        if (index >= 0 && index < mEntries.size()) {
            mSummary = mEntries.get(index);
        }
    }


    @Override
    public void onScenceModeRecylerItemClick(String selectedValue) {
        Log.d(TAG, "onRecylerItemClick "+selectedValue+" "+this);
        setValue(selectedValue);
        if (mListener != null) {
            mListener.onValueChanged(selectedValue);
        }
        mPreference.setSummary( selectedValue );
    }
}
