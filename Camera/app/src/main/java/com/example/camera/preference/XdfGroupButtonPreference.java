package com.example.camera.preference;

import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class XdfGroupButtonPreference extends Preference {
    private   String  TAG =
           (XdfGroupButtonPreference.class.getSimpleName()+".xshx");
    private List<String> mEntryValues = new ArrayList<>();
    private String mSelectedValue;
    private LinearLayout layout;
    private View.OnClickListener onClickListener = new MyOnClickListener();
    private View mRootView;
    private LayoutInflater inflater;
    private Context mContext;

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        /**
         * Callback when item clicked.
         *
         * @param value The picture size clicked.
         */
        void onItemClick(String value);
    }

    public XdfGroupButtonPreference(Context context){
        this(context, null);
    }

    public XdfGroupButtonPreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XdfGroupButtonPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }
    public XdfGroupButtonPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;

    }

    public void setValue(String value) {
        mSelectedValue = value;
    }

    public void setEntryValues(List<String> entryValues) {
        mEntryValues.clear();
        mEntryValues.addAll(entryValues);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }
    protected View onCreateView(ViewGroup parent) {
        super.onCreateView(parent);
        Log.d(TAG, "onCreateView parent "+parent);
        if( mRootView == null){
            inflater = LayoutInflater.from(mContext);
            mRootView = inflater.inflate(R.layout.xdf_group_button_preference, null);
        }
        return mRootView;
    }

    protected void onBindView(View view) {
        super.onBindView(view);


        upButtonView(view);
    }

    private class MyOnClickListener  implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            String value =((Button)v).getText().toString();
            int index = mEntryValues.indexOf( value );
            mSelectedValue = mEntryValues.get( index );

            upButtonView(v);
            mListener.onItemClick(value);

        }
    }

    private void upButtonView(View view){
        layout = (LinearLayout) view.findViewById(R.id.group_radio_summary );
        layout.removeAllViews();
        for (int i = 0 ; i < mEntryValues.size(); i++) {
            Button button = new Button( getContext() );
            button.setText( mEntryValues.get(i));
            LinearLayout.LayoutParams logTextLayoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            logTextLayoutParams.weight = 1;
            logTextLayoutParams.setMargins(4,4,4,4);
            button.setLayoutParams(logTextLayoutParams);
            button.setOnClickListener( onClickListener );
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor( Color.TRANSPARENT );
            if (mEntryValues.get(i).equals(mSelectedValue)) {
                if (i != 0){
                    button.setTextColor(Color.BLACK);
                    button.setBackgroundResource(R.drawable.setting_button_bg);
                }else {
                    button.setBackgroundResource(R.drawable.setting_button_white_bg);
                }
            }
            layout.addView( button );
        }
    }
}
