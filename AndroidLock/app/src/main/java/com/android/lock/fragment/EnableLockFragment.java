package com.android.lock.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.android.lock.MainActivity;
import com.android.lock.R;

public class EnableLockFragment extends Fragment implements View.OnClickListener {

    Button  enableLock;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_enable_lock, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enableLock = view.findViewById( R.id.enableLock );
        enableLock.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        if( v.getId() == R.id.enableLock ){
            ((MainActivity)getActivity()).showFragment( new SetPasswordFragment() );
        }
    }
}
