package com.android.lock.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.lock.MainActivity;
import com.android.lock.R;
import com.android.lock.widget.PasswordEditText;

public class InputPasswordFragment extends Fragment {

    private static final String TAG = "InputPasswordFragment";
//    VerifyEditText verifyEditText;
    PasswordEditText verifyEditText;
    TextView    headText;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_password, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headText = view.findViewById(R.id.headerText);
        String appName=((MainActivity)getActivity()).getPreAppName();
        if( appName!=null ){
            headText.setText( String.format(getResources().getString(R.string.input_password),appName ));
        }else{
            headText.setText("请输入密码后进入");
        }
        verifyEditText = view.findViewById(R.id.vet);

        verifyEditText.addInputCompleteListener(new PasswordEditText.InputCompleteListener() {
            @Override
            public void complete(String content) {
//                verifyEditText.hideSoftKeyBoard();
                if( ((MainActivity)getActivity()).getAction().equals("android.intent.action.MANAGE_APPLICATION_LOCK")){
                    ((MainActivity)getActivity()).showFragment(new ManageLockFragment());
                }else{
//                    验证密码通过  转交 framework 启动应用
                    String password = getActivity().getSharedPreferences("password", Context.MODE_PRIVATE).getString("password",null);
                    Log.d(TAG, " password "+password+" content "+content);
                    if( content.equals(password )){
                        startActivity( ((MainActivity)getActivity()).getPreIntent(),((MainActivity)getActivity()).getPreOption());
                        ((MainActivity)getActivity()).finish();
                    }else{
                        Toast.makeText(getActivity(), "密码错误 ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
