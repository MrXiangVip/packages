package com.example.settings.settingslib;


import android.content.Context;
import android.preference.PreferenceFragment;

import androidx.fragment.app.Fragment;

public abstract class ObservablePreferenceFragment extends Fragment {

    public void onAttach(Context context) {
        super.onAttach(context);
//        mLifecycle.onAttach(context);
    }
}
