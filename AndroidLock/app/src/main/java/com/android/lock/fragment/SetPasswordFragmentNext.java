package com.android.lock.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.lock.MainActivity;
import com.android.lock.R;
import com.android.lock.widget.PasswordEditText;
import com.android.lock.widget.VerifyEditText;

public class SetPasswordFragmentNext extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    //    VerifyEditText  vet;
    PasswordEditText vet;
    Button sureBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_set_password_next, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vet = view.findViewById(R.id.vet);
        vet.clearText();
        vet.setOnEditorActionListener(this);
        vet.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
                String tmpPassword = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("firstPassword", null);
                if (tmpPassword.equals(content)) {
                    Toast.makeText( getActivity(), "两次密码一致,请点击确认按钮", Toast.LENGTH_LONG).show();
                }else{
                    vet.clearText();
                }
            }
        });
        sureBtn = view.findViewById(R.id.sure);
        sureBtn.setOnClickListener( this );
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("应用锁密码");
    }


    @Override
    public void onClick(View view) {
        if( view.getId()==R.id.sure){
            actionDone();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT ||actionId == EditorInfo.IME_ACTION_DONE) {
            actionDone();
        }
        return false;
    }

    public void actionDone() {
        String content = vet.getText().toString();
        if( content.length() == vet.getTextLength() ){
//               确认密码
            String tmpPassword = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("firstPassword", null);
            if (tmpPassword.equals(content)) {
//               校验通过，保存密码   , enable 应用锁  , 跳往管理页面
                getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).edit().putString("password", content).apply();
//                SystemProperties.set( "persist.vendor.application.lock.enable", Boolean.toString(true));
                vet.hideSoftKeyBoard();
                ((MainActivity) getActivity()).popBackFragment();
                ((MainActivity) getActivity()).showFragment(new ManageLockFragment());
            } else {
                Toast.makeText(getActivity(), "密码不一致", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
