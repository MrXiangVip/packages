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

public class SettingFragment extends PreferenceFragment  {
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
        layout.setLayoutParams(params);

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
