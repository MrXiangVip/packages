package com.android.lock.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.android.lock.MainActivity;
import com.android.lock.R;
import com.android.lock.widget.PasswordEditText;
import com.android.lock.widget.VerifyEditText;

public class CloseLockFragment extends Fragment {

    private String TAG ="CloseLockFragment";
//    VerifyEditText  verifyEditText;
    PasswordEditText verifyEditText;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_close_lock, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        verifyEditText = view.findViewById( R.id.vet );
        verifyEditText.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
//              校验密码               修改属性
                String password = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("password", null);
                Log.d(TAG, " password "+password +" content "+content);
                if( password.equals(content)){
//                    SystemProperties.set( "persist.vendor.application.lock.enable", Boolean.toString(false));
                    getActivity().finish();
                }else{
                    Toast.makeText(getActivity(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((MainActivity) getActivity()).setActionBarTitle("关闭应用锁功能");
    }


}
