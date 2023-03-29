package com.example.camera.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class CoverGroupButtonLayout extends LinearLayout {
    private LinearLayout groupLayout;
    private LinearLayout buttonLayout;
    private Button      selectedButton;
    private TextView    title;
    private List<String> mEntryValues = new ArrayList<>();
    private String      mSelectedValue;

    private Context mContext;
    private OnClickListener onClickListener = new MyOnClickListener();
    private String TAG="CoverGroupButtonLayout";
    float  lastX;
    float  lastY;
    private class MyOnClickListener  implements  OnClickListener{
        @Override
        public void onClick(View v) {
            Log.d(TAG, "position x "+v.getX()+" y "+v.getY());
            String value =((Button)v).getText().toString();

            int index = mEntryValues.indexOf( value );
            mSelectedValue = mEntryValues.get( index );

            float currentX = v.getX();
            if( mSelectedValue.equals("off")){
                selectedButton.setBackgroundResource( R.drawable.setting_button_white_bg);

            }else{
                selectedButton.setBackgroundResource( R.drawable.setting_button_bg);
            }
            selectedButton.setText( mSelectedValue );
            selectedButton.setTextColor(Color.BLACK);
            ObjectAnimator animator = ObjectAnimator.ofFloat(selectedButton, "translationX", lastX, currentX);
            animator.setDuration(300);
            animator.start();
            lastX = currentX;

        }
    }
    public CoverGroupButtonLayout(Context context) {
        this(context, null);
    }

    public CoverGroupButtonLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverGroupButtonLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CoverGroupButtonLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;

    }

    protected void onFinishInflate() {
       super.onFinishInflate();
       title = findViewById(R.id.group_title);
       groupLayout = findViewById( R.id.group_pref );
       selectedButton = findViewById( R.id.cover );
   }
    public void setSelectedValue(String value) {
        mSelectedValue = value;
    }

    public void setEntryAndSelectedValue(String selectedValue, List<String> entryValues ){
        mSelectedValue = selectedValue;
        mEntryValues.clear();
        for (String value : entryValues) {
            mEntryValues.add(value);
        }
    }

    public void setTitle(String titleValue ){
        title.setText( titleValue );
    }
   public void initButtonView(){
        buttonLayout = (LinearLayout) groupLayout.findViewById(R.id.group_radio_summary );
        for (int i = 0 ; i < mEntryValues.size(); i++) {
            Button button = new Button( mContext );
            button.setText( mEntryValues.get(i));
            LayoutParams layoutParams =
                    new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.setMargins(4,4,4,4);
            button.setLayoutParams(layoutParams);
            button.setOnClickListener( onClickListener );
            button.setTextColor(Color.WHITE);
            button.setBackgroundColor( Color.TRANSPARENT );
            if (mEntryValues.get(i).equals(mSelectedValue)) {
                if (i != 0){
//                    button.setBackgroundResource(R.drawable.setting_button_bg);
                    lastX = button.getX();
                    lastY = button.getY();
                    selectedButton.setBackgroundResource( R.drawable.setting_button_bg );
                    selectedButton.setX( lastX);
                    selectedButton.setY( lastY);
                }else {
//                    button.setBackgroundResource(R.drawable.setting_button_white_bg);
                    lastX = button.getX();
                    lastY = button.getY();
                    selectedButton.setBackgroundResource( R.drawable.setting_button_white_bg);
                    selectedButton.setX( lastX);
                    selectedButton.setY( lastY);
                }
                selectedButton.setTextColor(Color.BLACK);
                selectedButton.setText( mSelectedValue );
            }
            buttonLayout.addView( button );
        }
    }

}
