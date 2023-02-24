package com.example.camera.setting;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;

import com.example.camera.ICameraSettingView;
import com.example.camerabg.R;
import com.example.camera.preference.IWhiteBalanceRecyclerItemClickListener;
import com.example.camera.preference.SummaryPreference;

import java.util.ArrayList;
import java.util.List;

public class WhiteBalanceSettingView implements ICameraSettingView, SummaryPreference.OnLayoutClickListener, IWhiteBalanceRecyclerItemClickListener {
    private  String TAG="MyCamera.WhiteBalanceSettingView";


    private List<String> mEntries = new ArrayList<>();
    private List<String> mEntryValues = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();

    private List<String> mOriginalEntries = new ArrayList<>();
    private List<String> mOriginalEntryValues = new ArrayList<>();
    private List<Integer> mOriginalIcons = new ArrayList<>();

    private String mSelectedValue = null;
    private String mSummary = null;

    private Activity mActivity;
    private String mKey;
    private  SummaryPreference preference;
    private PreferenceScreen  screen;
    private XdfTabHostFragment xdfTabHostFragment;
    public WhiteBalanceSettingView(Activity activity, String key) {
        mActivity = activity;
        mKey = key;

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

        fragment.addPreferencesFromResource(R.xml.xdf_white_balance_preference);
        screen = fragment.getPreferenceScreen();
        preference = (SummaryPreference) screen.findPreference("key_white_balance");
        preference.setSummary("summary");
        preference.setOnLayoutClickListener( this );
        preference.setRootPreference( screen);
        preference.setEnabled(true);


        if (xdfTabHostFragment == null) {
            xdfTabHostFragment = new XdfTabHostFragment();
            xdfTabHostFragment.setOnWhiteBalanceRecylerItemClickListener( this );
        }
        xdfTabHostFragment.setWhiteBalanceSelectedValue(mSelectedValue);
        xdfTabHostFragment.setWhiteBalanceEntriesAndEntryValues(mEntries, mEntryValues, mIcons);
    }



    public void setEntryValues(List<String> entryValues) {
        mEntries.clear();
        mEntryValues.clear();
        mIcons.clear();

        for (int i = 0; i < mOriginalEntryValues.size(); i++) {
            mEntryValues.add(mOriginalEntries.get(i));
            mEntries.add(mOriginalEntries.get(i));
            mIcons.add(mOriginalIcons.get(i));
        }

    }


    public void setValue(String value) {
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
    public void onLayoutClick(View view) {
        if (xdfTabHostFragment == null) {
            xdfTabHostFragment = new XdfTabHostFragment();
            xdfTabHostFragment.setOnWhiteBalanceRecylerItemClickListener( this );;
        }
        xdfTabHostFragment.setCurrentIndex( 1 );
        xdfTabHostFragment.setWhiteBalanceSelectedValue(mSelectedValue);
        xdfTabHostFragment.setWhiteBalanceEntriesAndEntryValues(mEntries, mEntryValues, mIcons);

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
    }


    @Override
    public void onWhiteBalanceRecylerItemClick(String selectedValue) {
        Log.d(TAG, "onRecylerItemClick "+selectedValue+" "+this);
        preference.setSummary( selectedValue );
    }
}
