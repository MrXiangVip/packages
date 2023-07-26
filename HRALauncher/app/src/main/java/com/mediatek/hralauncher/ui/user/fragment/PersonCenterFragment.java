package com.mediatek.hralauncher.ui.user.fragment;

import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.mediatek.hralauncher.R;

public class PersonCenterFragment extends BaseFragment {

    ConstraintLayout layoutLogin;
    @Override
    protected int getLayoutID() {
        return R.layout.fragment_person_center;
    }

    @Override
    protected void init(View rootView) {
        layoutLogin = rootView.findViewById(R.id.layoutLogin);
        showUserInfo();

    }

    private void showUserInfo() {
        layoutLogin.setVisibility(View.GONE);

    }
    @Override
    protected void initListener() {

    }
}
