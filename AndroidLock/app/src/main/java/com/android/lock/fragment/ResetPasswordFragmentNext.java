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

import androidx.annotation.Nullable;

import com.android.lock.MainActivity;
import com.android.lock.R;
import com.android.lock.widget.PasswordEditText;
import com.android.lock.widget.VerifyEditText;

public class ResetPasswordFragmentNext extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    //    VerifyEditText  vet;
    PasswordEditText vet;
    Button sureBtn;
    TextView head;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_password_next, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        head = view.findViewById(R.id.headerText);
        vet = view.findViewById(R.id.vet);
        vet.clearText();
        vet.setOnEditorActionListener(this);
        vet.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
                head.setText("请点击完成新密码的修改");
            }
        });
        sureBtn = view.findViewById(R.id.sure);
        sureBtn.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("修改密码");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sure) {
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
        if (content.length() == vet.getTextLength()) {
// 保存密码
            vet.hideSoftKeyBoard();
            getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).edit().putString("password", content).apply();
            ((MainActivity) getActivity()).popBackFragment();
            ((MainActivity) getActivity()).showFragment(new ManageLockFragment());
        }
    }
}