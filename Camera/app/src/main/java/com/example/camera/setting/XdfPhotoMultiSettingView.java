package com.example.camera.setting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.camera.ICameraSettingView;
import com.example.camerabg.R;
import com.example.camera.preference.IScenceModeRecyclerItemClickListener;
import com.example.camera.preference.IWhiteBalanceRecyclerItemClickListener;
import com.example.camera.preference.XdfMultiImagesPreference;

import java.util.ArrayList;
import java.util.List;

public class XdfPhotoMultiSettingView implements ICameraSettingView , XdfMultiImagesPreference.OnImageClickListener, IScenceModeRecyclerItemClickListener, IWhiteBalanceRecyclerItemClickListener {
    private Activity mActivity;
    private String mKey;
    private String TAG="MultiSettingView";
    private XdfMultiImagesPreference mPreference;
    private XdfTabHostFragment xdfTabHostFragment;
// 白平衡
    private static List<String> mWhiteBalanceOriginalEntries = new ArrayList<>();
    private static List<String> mWhiteBalanceOriginalEntryValues = new ArrayList<>();
    private static List<Integer> mWhiteBalanceOriginalIcons = new ArrayList<>();

    private static List<String> mWhiteBalanceEntries = new ArrayList<>();
    private static List<String> mWhiteBalanceEntryValues = new ArrayList<>();
    private static List<Integer> mWhiteBalanceIcons = new ArrayList<>();

    private static String mWhiteBalanceSelectedValue = null;
// 情景模式
    private static List<Integer> mScenceModeOriginalIcons = new ArrayList<>();
    private static List<String> mScenceModeOriginalEntries = new ArrayList<>();
    private static List<String> mScenceModeOriginalEntryValues = new ArrayList<>();

    private static List<Integer> mScenceModeIcons = new ArrayList<>();
    private static List<String> mScenceModeEntries = new ArrayList<>();
    private static List<String> mScenceModeEntryValues = new ArrayList<>();
    private boolean mEnabled;

    private static String mScenceModeSelectedValue = null;
//  AIS
    private static boolean mChecked=false;
//
    private String mSummary = null;

    public XdfPhotoMultiSettingView(Activity activity, String key) {
        mActivity = activity;
        mKey = key;


        initScenceModeOriginalEntryValues( mActivity );
        initWhiteBalanceOriginalEntryValues( mActivity );

    }
    private void initScenceModeOriginalEntryValues(Activity activity ) {
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
        mScenceModeOriginalIcons.clear();
        mScenceModeOriginalEntries.clear();
        mScenceModeOriginalEntryValues.clear();
        for (int icon : originalIconsInArray) {
            mScenceModeOriginalIcons.add( icon );
        }
        for (String value : originalEntriesInArray) {
            mScenceModeOriginalEntries.add( value);
        }
        for (String value : originalEntryValuesInArray) {
            mScenceModeOriginalEntryValues.add( value );
        }
    }

    private void initWhiteBalanceOriginalEntryValues(Activity activity ) {
        String[] originalEntriesInArray = mActivity.getResources()
                .getStringArray(R.array.white_balance_entries);
        String[] originalEntryValuesInArray = mActivity.getResources()
                .getStringArray(R.array.white_balance_entryvalues);

        TypedArray array = mActivity.getResources().obtainTypedArray(R.array.white_balance_icons);
        int n = array.length();
        int[] originalIconsInArray = new int[n];
        for (int i = 0; i < n; ++i) {
            originalIconsInArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        mWhiteBalanceOriginalIcons.clear();
        mWhiteBalanceOriginalEntries.clear();
        mWhiteBalanceOriginalEntryValues.clear();
        for (int icon : originalIconsInArray) {
            mWhiteBalanceOriginalIcons.add( icon );
        }
        for (String value : originalEntriesInArray) {
            mWhiteBalanceOriginalEntries.add( value);
        }
        for (String value : originalEntryValuesInArray) {
            mWhiteBalanceOriginalEntryValues.add( value );
        }
    }
    @Override
    public void loadView(PreferenceFragment fragment) {
        fragment.addPreferencesFromResource(R.xml.xdf_multi_image_preference);
        mPreference = (XdfMultiImagesPreference) fragment.getPreferenceManager().findPreference("key_multi_image");
        mPreference.setRootPreference(fragment.getPreferenceScreen());
//        mPreference.setId(R.id.white_balance_setting);
//        mPreference.setContentDescription(fragment.getActivity().getResources()
//                .getString(R.string.pref_camera_whitebalance_content_description));
        mPreference.setSummary("summary");
        mPreference.setOnImageClickListener( this );

        mPreference.setEnabled(true);
//        if (xdfTabHostFragment == null) {
//            xdfTabHostFragment = new XdfTabHostFragment();
//            xdfTabHostFragment.setOnSconceModeRecylerItemClickListener( this );
//        }
//        xdfTabHostFragment.setScenceModeSelectedValue(mWhiteBalanceSelectedValue);
//        xdfTabHostFragment.setScenceModeEntriesAndEntryValues(mEntries, mEntryValues, mWhiteBalanceIcons);
    }

    @Override
    public boolean isEnabled() {
        return mEnabled;
    }

    public void setWhiteBalanceEntryValues( ) {
        mWhiteBalanceIcons.clear();
        mWhiteBalanceEntries.clear();
        mWhiteBalanceEntryValues.clear();

        for (int i = 0; i < mWhiteBalanceOriginalEntryValues.size(); i++) {
            mWhiteBalanceEntryValues.add(mWhiteBalanceOriginalEntryValues.get(i));
            mWhiteBalanceEntries.add(mWhiteBalanceOriginalEntries.get(i));
            mWhiteBalanceIcons.add(mWhiteBalanceOriginalIcons.get(i));
        }
    }

    public void setScenceModeEntryValues( ){
        mScenceModeIcons.clear();
        mScenceModeEntries.clear();
        mScenceModeEntryValues.clear();

        for (int i = 0; i < mScenceModeOriginalEntryValues.size(); i++) {
            mScenceModeEntryValues.add(mScenceModeOriginalEntryValues.get(i));
            mScenceModeEntries.add(mScenceModeOriginalEntries.get(i));
            mScenceModeIcons.add(mScenceModeOriginalIcons.get(i));
        }
    }
    public void setWhiteBalanceValue(String value) {
        Log.d(TAG, "setValue "+value);
        mWhiteBalanceSelectedValue = value;
        if ("hdr".equals(mWhiteBalanceSelectedValue)) {
            mWhiteBalanceSelectedValue = "auto";
        }
        int index = mWhiteBalanceEntryValues.indexOf(mWhiteBalanceSelectedValue);
        if (index >= 0 && index < mWhiteBalanceEntries.size()) {
            mSummary = mWhiteBalanceEntries.get(index);
        }
    }

    public void setScenceModeValue(String value) {
        Log.d(TAG, "setValue "+value);
        mScenceModeSelectedValue = value;
        if ("hdr".equals(mScenceModeSelectedValue)) {
            mScenceModeSelectedValue = "auto";
        }
        int index = mWhiteBalanceEntryValues.indexOf(mScenceModeSelectedValue);
        if (index >= 0 && index < mWhiteBalanceEntries.size()) {
            mSummary = mWhiteBalanceEntries.get(index);
        }
    }

    @Override
    public void onImageClick(View view) {
        if( view.getId() == R.id.scence_mode ){
            if (xdfTabHostFragment == null) {
                xdfTabHostFragment = new XdfTabHostFragment();
                xdfTabHostFragment.setOnSconceModeRecylerItemClickListener( this );
            }
            xdfTabHostFragment.setCurrentIndex( 0 );
            xdfTabHostFragment.setScenceModeSelectedValue(mScenceModeSelectedValue);
            xdfTabHostFragment.setScenceModeEntriesAndEntryValues(mScenceModeEntries, mScenceModeEntryValues, mScenceModeIcons);
            FragmentTransaction transaction = mActivity.getFragmentManager()
                    .beginTransaction();

            Fragment fragment = mActivity.getFragmentManager().findFragmentById( R.id.setting_container);
            transaction.hide( fragment );
            transaction.setCustomAnimations( R.anim.slide_in_bottom, 0);
            transaction.replace(R.id.setting_tail, xdfTabHostFragment, "scene_mode_fragment");
            transaction.addToBackStack(null);
            transaction.commit();
        }else if( view.getId() == R.id.white_balance ){
            if (xdfTabHostFragment == null) {
                xdfTabHostFragment = new XdfTabHostFragment();
                xdfTabHostFragment.setOnWhiteBalanceRecylerItemClickListener(this );
            }
            xdfTabHostFragment.setCurrentIndex( 1 );
            xdfTabHostFragment.setWhiteBalanceSelectedValue(mWhiteBalanceSelectedValue);
            xdfTabHostFragment.setWhiteBalanceEntriesAndEntryValues(mWhiteBalanceEntries, mWhiteBalanceEntryValues, mWhiteBalanceIcons);
            FragmentTransaction transaction = mActivity.getFragmentManager()
                    .beginTransaction();

            Fragment fragment = mActivity.getFragmentManager().findFragmentById( R.id.setting_container);
            transaction.hide( fragment );
            transaction.setCustomAnimations( R.anim.slide_in_bottom, 0);
            transaction.replace(R.id.setting_tail, xdfTabHostFragment, "scene_mode_fragment");
            transaction.addToBackStack(null);
            transaction.commit();
        }else if( view.getId() == R.id.ais ){
            mChecked = !mChecked;
            mPreference.setAisChecked( mChecked );
        }

    }

    @Override
    public void onScenceModeRecylerItemClick(String selectedValue) {
        Toast.makeText( mActivity, " " + selectedValue, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onWhiteBalanceRecylerItemClick(String entry,String entryValue) {
        Toast.makeText( mActivity, " " + entryValue, Toast.LENGTH_SHORT).show();

    }
}
