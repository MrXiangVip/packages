package com.example.myapplication.widget;

import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

public class GamepadConfigEditor extends FrameLayout implements IFloatingView{
    Context mContext;
    WindowManager windowManager;
    public GamepadConfigEditor(@NonNull Context context) {
        this(context,null);
    }

    public GamepadConfigEditor(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GamepadConfigEditor(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public GamepadConfigEditor(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext =context;
        init( context);
    }

    public void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.layout_gamepad_config_editor,this,true);
    }

    @Override
    public void show() {
        if( getParent()==null ){
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.5);
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.CENTER;
            params.format = PixelFormat.TRANSLUCENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            windowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
            windowManager.addView(this, params);

        }
    }

    @Override
    public void dismiss() {
        if (getParent() != null) {
            windowManager.removeView(this);
        }
    }
}
