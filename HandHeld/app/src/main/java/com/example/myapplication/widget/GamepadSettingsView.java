package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.sidebar.utils.GamepadConfigType;

public class GamepadSettingsView extends LinearLayout implements View.OnClickListener {
    private View nativeMode;
    private View screenMappingMode;
    private FrameLayout  container;
    private Button      workMode;
    private Context mContext;
    GamepadConfigType currentAppGamepadMode;
    private String TAG="GamepadSettingsView";
    public GamepadSettingsView(Context context) {
        this(context,null);
    }

    public GamepadSettingsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GamepadSettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public GamepadSettingsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
//        init( context);
        mContext = context;
        currentAppGamepadMode = GamepadConfigType.NATIVE;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        container= findViewById(R.id.container);
        workMode = findViewById(R.id.workMode);
        nativeMode = LayoutInflater.from(mContext).inflate(R.layout.layout_native_mode_settings, container, false );
        screenMappingMode = LayoutInflater.from(mContext).inflate(R.layout.layout_screen_mapping_mode_settings, container, false);
        workMode.setOnClickListener( this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if( currentAppGamepadMode.isNative() ) {
            container.addView(nativeMode);
        }else{
            container.addView( workMode);
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        container.removeView( nativeMode);
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, " ");

        currentAppGamepadMode = currentAppGamepadMode.isNative()?
            GamepadConfigType.TOUCHSCREEN : GamepadConfigType.NATIVE;
        refreshContainerUi();
    }

    private void refreshContainerUi() {
        container.removeAllViews();
        container.addView( currentAppGamepadMode.isNative()? nativeMode:screenMappingMode);
        invalidate();
    }
}
