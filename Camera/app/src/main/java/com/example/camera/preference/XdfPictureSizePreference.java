package com.example.camera.preference;

import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.camerabg.R;



import java.util.ArrayList;
import java.util.List;

/**
 * Picture size Prefrence view.
 * xshx add 20230102
 */
public class XdfPictureSizePreference extends Preference {
    private  String TAG ="XdfPictureSizePreference";
    private List<String> mEntryValues = new ArrayList<>();
    private String mSelectedValue;
    private LinearLayout layout;


    private PreferenceScreen mRootPreference;
    private boolean mRemoved = false;

    private View.OnClickListener onClickListener = new MyOnClickListener();

    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        /**
         * Callback when item clicked.
         *
         * @param value The picture size clicked.
         */
        void onItemClick(String value);
    }

    public XdfPictureSizePreference(Context context){
        this(context, null);
    }

    public XdfPictureSizePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XdfPictureSizePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }
    public XdfPictureSizePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }
    public void setEnabled(boolean enabled) {
        if (enabled) {
            mRootPreference.addPreference(this);
            mRemoved = false;
        } else if (!mRemoved) {
            mRootPreference.removePreference(this);
            mRemoved = true;
        }
    }
    public void setRootPreference(PreferenceScreen rootPreference) {
        mRootPreference = rootPreference;
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
    
    protected void onBindView(View view) {
        super.onBindView(view);

        layout = (LinearLayout) view.findViewById(R.id.group_radio_summary );
        layout.removeAllViews();
        for (int i = 0 ; i < mEntryValues.size(); i++) {
            Button button = new Button( getContext() );
            button.setText( mEntryValues.get(i));

            LinearLayout.LayoutParams logTextLayoutParams =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            logTextLayoutParams.weight = 1;
            logTextLayoutParams.leftMargin=4;
            logTextLayoutParams.rightMargin=4;
            button.setLayoutParams(logTextLayoutParams);
            button.setOnClickListener(onClickListener);
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor( Color.TRANSPARENT);
            if (mEntryValues.get(i).equals(mSelectedValue)) {
                button.setBackgroundResource( R.drawable.ic_button_background);
            }
            layout.addView( button );

        }
    }

    private class MyOnClickListener  implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
            String value =((Button)v).getText().toString();
            int index = mEntryValues.indexOf( value );
            mSelectedValue = mEntryValues.get( index );

            layout.removeAllViews();
            for (int i = 0 ; i < mEntryValues.size(); i++) {
                Button button = new Button( getContext() );
                button.setText( mEntryValues.get(i));
                LinearLayout.LayoutParams logTextLayoutParams =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                logTextLayoutParams.weight = 1;
                logTextLayoutParams.leftMargin=4;
                logTextLayoutParams.rightMargin=4;
                button.setLayoutParams(logTextLayoutParams);
                button.setOnClickListener( onClickListener );
                button.setTextColor(Color.WHITE);
                button.setBackgroundColor( Color.TRANSPARENT);
                if (mEntryValues.get(i).equals(mSelectedValue)) {
                    button.setBackgroundResource( R.drawable.ic_button_background);
                }
                layout.addView( button );
            }

            mListener.onItemClick(value);

        }
    }
}
