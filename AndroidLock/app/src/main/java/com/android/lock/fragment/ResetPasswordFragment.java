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

public class ResetPasswordFragment extends Fragment {

//    VerifyEditText  vet;
    PasswordEditText vet;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vet = view.findViewById( R.id.vet );
        vet.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
//                重新设置密码， 1 和原密码比较   通过则跳往设置密码页面
                String password = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("password", null);
                if( password.equals(content)){
                    ((MainActivity)getActivity()).showFragment( new ResetPasswordFragmentNext() );
                }else{
                    Toast.makeText(getActivity(), "和原密码不一致", Toast.LENGTH_LONG).show();
                }
            }
        });
        ((MainActivity) getActivity()).setActionBarTitle("修改密码");

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
