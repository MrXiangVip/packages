package com.android.lock.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.android.lock.MainActivity;
import com.android.lock.R;
import com.android.lock.widget.PasswordEditText;
import com.android.lock.widget.VerifyEditText;

public class SetPasswordFragment extends Fragment {

//    VerifyEditText  vet;
    PasswordEditText vet;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_password, container, false);
        return  view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vet = view.findViewById( R.id.vet );
        vet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vet.requestFocus();
                vet.showSoftKeyBoard();
            }
        });
        vet.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
                getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).edit().putString("firstPassword", content).apply();
                ((MainActivity)getActivity()).showFragment( new SetPasswordFragmentNext() );
            }
        });
        ((MainActivity) getActivity()).setActionBarTitle("应用锁密码");

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
