package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class ScreenMappingModeSettingsView extends LinearLayout implements View.OnClickListener {
    Button createSchemeView;
    Context mContext;

    public ScreenMappingModeSettingsView(Context context) {
        this(context, null);
    }

    public ScreenMappingModeSettingsView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScreenMappingModeSettingsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ScreenMappingModeSettingsView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setBackgroundColor(0x4D000000);
        createSchemeView =this.findViewById(R.id.createSchemeView);
        createSchemeView.setOnClickListener( this );
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onClick(View v) {
        if( v.getId()==R.id.createSchemeView){
            FloatingView.getInstance(mContext).get(SideBarView.class).dismiss();
            FloatingView.getInstance(mContext).get(GamepadConfigEditor.class).show();
        }
    }
}
