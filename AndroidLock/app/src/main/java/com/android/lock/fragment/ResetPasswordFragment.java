package com.android.lock.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.lock.MainActivity;
import com.android.lock.R;
import com.android.lock.widget.PasswordEditText;
import com.android.lock.widget.VerifyEditText;

public class ResetPasswordFragment extends Fragment implements View.OnClickListener{

//    VerifyEditText  vet;
    PasswordEditText vet;
    Button           sureBtn;
    TextView         head;
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
        head =view.findViewById( R.id.headerText );
        vet = view.findViewById( R.id.vet );
        vet.clearText();
        vet.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
                String password = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("password", null);
                if( password.equals(content)){
                    ((MainActivity)getActivity()).pushBackFragment( new ResetPasswordFragmentNext() );
                }else{
                    head.setText("密码错误,请重新输入");
                    head.setTextColor(Color.RED);
                }
            }
        });
        sureBtn =view.findViewById(R.id.sure);
        sureBtn.setOnClickListener(this );
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle("修改密码");
    }

    @Override
    public void onPause() {
        super.onPause();
        vet.clearText();
    }

    @Override
    public void onClick(View v) {

    }

}
