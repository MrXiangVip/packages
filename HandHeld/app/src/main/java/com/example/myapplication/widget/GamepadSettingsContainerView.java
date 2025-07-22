package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class GamepadSettingsContainerView extends FrameLayout {

    private View currentView;
    private GamepadSettingsView  gamepadSettingsView;
    public GamepadSettingsContainerView(@NonNull Context context) {
        this(context,null);
    }

    public GamepadSettingsContainerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GamepadSettingsContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public GamepadSettingsContainerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        gamepadSettingsView = (GamepadSettingsView) LayoutInflater.from(context).inflate( R.layout.layout_gamepad_settings,this, false);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        currentView = gamepadSettingsView;
        addView( currentView);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllViews();
    }
}
