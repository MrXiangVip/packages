package com.example.camera;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.camera.preference.IRecyclerItemClickListener;
import com.example.camera.preference.XdfMultiImagesPreference;
import com.example.camera.preference.LoadingPreference;
import com.example.camera.preference.RecyclerPreference;
import com.example.camera.preference.SummaryPreference;
import com.example.camera.setting.XdfSceneModeFragment;
import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends PreferenceFragment implements IRecyclerItemClickListener {
    String TAG="SettingFragment.camera";

    private List<ICameraSettingView> mSettingViewList = new ArrayList<>();

    public static Preference mUptime;

    private CheckBoxPreference m_prefCheckBoxTwo;
    private LoadingPreference loadingPreference;

    private List<String> mValues = new ArrayList<>();


    private String mSelectedValue = null;

    private List<String> mEntries = new ArrayList<>();
    private List<String> mEntryValues = new ArrayList<>();
    private List<Integer> mIcons = new ArrayList<>();

    private XdfMultiImagesPreference xdfMultiImagesPreference;
    private SummaryPreference sceneSummaryPreference;
    private SummaryPreference whiteBalanceSummaryPreference;
    private RecyclerPreference recyclerPreference;
    private XdfSceneModeFragment mSceneModeSelector;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settingsfragment);

        synchronized (this) {
            for (ICameraSettingView view : mSettingViewList) {
                view.loadView(this);
            }
        }

    }

    private int dpToPixel(int dp) {
        float scale = getActivity().getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout layout = (FrameLayout) getActivity().findViewById(android.R.id.list_container);
        layout.setBackgroundResource(R.drawable.ic_container_background);

        LinearLayout.LayoutParams  params = new LinearLayout.LayoutParams(dpToPixel(640), dpToPixel(366));
//        params.gravity = CENTER
        layout.setLayoutParams(params);

    }
    @Override
    public void onRecylerItemClick(String selectedValue) {
            mSelectedValue = selectedValue;
            sceneSummaryPreference.setSummary( selectedValue);
    }

//    @Override
//    public void onLayoutClick(View view) {
//        Toast.makeText(getContext(), view.getId()+"", Toast.LENGTH_LONG).show();
//        mSceneModeSelector = new XdfSceneModeFragment();
//        mSceneModeSelector.setSelectedValue(mSelectedValue);
//        mSceneModeSelector.setEntriesAndEntryValues(mEntries, mEntryValues, mIcons);
//
//        mSceneModeSelector.setOnRecylerItemClickListener( this );
//
//
//        FragmentTransaction transaction = getFragmentManager()
//                .beginTransaction();
//        Fragment fragment = getFragmentManager().findFragmentById( R.id.setting_container);
//        transaction.hide( fragment );
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.setting_tail,
//                mSceneModeSelector, "Setting").commit();
//    }
    private void initEntries() {

        for(int i=0; i <20; i++ ){
            mEntries.add(""+i);
            int id = R.drawable.ic_launcher_background;
            mIcons.add( id );
        }
    }

    public synchronized void addSettingView(ICameraSettingView view) {
        if (view == null) {
            Log.w(TAG, "[addSettingView], view:" + view, new Throwable());
            return;
        }
        if (!mSettingViewList.contains(view)) {
            mSettingViewList.add(view);
        }
    }


}
