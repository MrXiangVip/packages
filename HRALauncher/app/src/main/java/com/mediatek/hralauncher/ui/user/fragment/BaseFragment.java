package com.mediatek.hralauncher.ui.user.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {
    private  Context context;
    private  View   mView;
    public void onAttach(Context context ){
        super.onAttach(context);
        this.context = context;
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        mView = LayoutInflater.from( context ).inflate(getLayoutID( ), container, false );
        return  mView;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        init( );
        initListener( );
    }
    protected abstract int getLayoutID();
    protected abstract void init();
    protected abstract void initListener();
}
