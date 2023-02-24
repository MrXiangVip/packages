package com.example.settings.homepage;

import android.content.Context;
import android.os.Bundle;

import com.example.settings.R;
import com.example.settings.dashboard.DashboardFragment;

public class TopLevelSettings extends DashboardFragment {

    public TopLevelSettings() {
//        final Bundle args = new Bundle();
//        // Disable the search icon because this page uses a full search view in actionbar.
//        args.putBoolean(NEED_SEARCH_ICON_IN_ACTION_BAR, false);
//        setArguments(args);
    }

    protected int getPreferenceScreenResId() {
        return R.xml.top_level_settings;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
//        use(SupportPreferenceController.class).setActivity(getActivity());
    }
}
