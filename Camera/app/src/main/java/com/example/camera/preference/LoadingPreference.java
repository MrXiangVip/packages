package com.example.camera.preference;


import android.content.Context;
import android.graphics.Color;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class LoadingPreference extends Preference {
    private List<String> mEntryValues = new ArrayList<>();
    private String mSelectedValue;

    private LinearLayout layout;
    private View.OnClickListener onClickListener = new MyOnClickListener();
    public LoadingPreference(Context context){
        this(context, null);
    }


    public LoadingPreference(Context context, AttributeSet attrs) {
       this(context, attrs, 0);
    }

    public LoadingPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);

    }
    public LoadingPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setValue(String value) {
        mSelectedValue = value;
    }

    public void setEntryValues(List<String> entryValues) {
        mEntryValues.clear();
        mEntryValues.addAll(entryValues);
    }

    @Override
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
            button.setOnClickListener( onClickListener);
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor( Color.TRANSPARENT);
            if (mEntryValues.get(i).equals(mSelectedValue)) {
                button.setBackgroundResource( R.drawable.ic_radio_background);
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
                    button.setBackgroundResource( R.drawable.ic_radio_background);
                }
                layout.addView( button );

            }

        }
    }

}
