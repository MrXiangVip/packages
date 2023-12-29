package com.android.lock.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.lock.MainActivity;
import com.android.lock.R;
import com.android.lock.widget.PasswordEditText;
import com.android.lock.widget.VerifyEditText;

public class SetPasswordFragmentNext extends Fragment {

//    VerifyEditText  vet;
    PasswordEditText vet;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_set_password_next, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vet = view.findViewById( R.id.vet );
        vet.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
//                确认密码
                String tmpPassword = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("firstPassword",null);
                if( tmpPassword.equals(content) ){
//               校验通过，保存密码   , enable 应用锁  , 跳往管理页面
                    getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).edit().putString("password", content).apply();
//                SystemProperties.set( "persist.vendor.application.lock.enable", Boolean.toString(true));
                    vet.hideSoftKeyBoard();
                    ((MainActivity)getActivity()).showFragment( new ManageLockFragment() );
                }else{
                    Toast.makeText(getActivity(), "密码不一致", Toast.LENGTH_SHORT).show();
                }

            }
        });
        ((MainActivity) getActivity()).setActionBarTitle("应用锁密码");

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
