package com.android.lock.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.lock.MainActivity;
import com.android.lock.R;
//import android.os.SystemProperties;

public class ManageLockFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    RelativeLayout  lockSwitchLayout;
    RelativeLayout  gotoResetPassword;
    ManageApplicationAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lock_manager, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.applications);
        lockSwitchLayout = view.findViewById( R.id.lockSwitchLayout );
        gotoResetPassword = view.findViewById(R.id.gotoResetPassword);
        lockSwitchLayout.setOnClickListener( this );
        gotoResetPassword.setOnClickListener( this );

        ((MainActivity)getActivity()).setActionBarTitle("应用锁");
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((MainActivity)getActivity()).clearFragments();

        adapter = new ManageApplicationAdapter( getActivity() );
        recyclerView.setAdapter( adapter );

    }

    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.lockSwitchLayout ){

            ((MainActivity)getActivity()).pushBackFragment( new CloseLockFragment());

        }else if( v.getId() ==R.id.gotoResetPassword ){
            ((MainActivity)getActivity()).pushBackFragment( new ResetPasswordFragment());
        }
    }
}
