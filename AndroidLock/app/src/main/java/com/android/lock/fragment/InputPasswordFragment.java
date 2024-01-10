package com.android.lock.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

public class InputPasswordFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

    private static final String TAG = "InputPasswordFragment";
//    VerifyEditText verifyEditText;
    PasswordEditText vet;
    Button          sureBtn;
    TextView        headText;
    TextView        contentText;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_password, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        headText = view.findViewById(R.id.headerText);
        contentText = view.findViewById( R.id.contentText);
        vet = view.findViewById(R.id.vet);
        vet.clearText();
        vet.setOnEditorActionListener( this );
        sureBtn =view.findViewById(R.id.sure);
        sureBtn.setOnClickListener( this );
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        String appName = ((MainActivity) getActivity()).getPreAppName();
        if (appName != null) {
            headText.setText(String.format(getResources().getString(R.string.input_password), appName));
        }
    }



    @Override
    public void onClick(View v) {
        if( v.getId()==R.id.sure) {
            actionDone();
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if( actionId == EditorInfo.IME_ACTION_NEXT ||actionId == EditorInfo.IME_ACTION_DONE){
            actionDone();
        }
        return false;
    }

    public void actionDone( ){
        String content = vet.getText().toString();
//                1. 校验密码   2.判断是去往 管理界面还是 转交framework 启动应用
        String password = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("password", null);
        Log.d(TAG, " password " + password + " content " + content);
        if (content.equals(password)) {
            if (((MainActivity) getActivity()).getIntent() != null
                    && ((MainActivity) getActivity()).getIntent().getData() != null
                    && "android.intent.action.MANAGE_APPLICATION_LOCK".equals(
                        ((MainActivity) getActivity()).getIntent().getData().toString())) {
                vet.hideSoftKeyBoard();
                ((MainActivity) getActivity()).showFragment(new ManageLockFragment());
            } else {
                startActivity(((MainActivity) getActivity()).getPreIntent(), ((MainActivity) getActivity()).getPreOption());
                ((MainActivity) getActivity()).finish();
            }
        } else {
            contentText.setText("密码错误,请重新输入!!!");
            contentText.setTextColor(Color.RED);
            vet.clearText();
        }
    }

}
