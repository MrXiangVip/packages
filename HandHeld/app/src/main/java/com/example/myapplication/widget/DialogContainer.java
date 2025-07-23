package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

public class DialogContainer extends FrameLayout {
    public DialogContainer(@NonNull Context context) {
        this(context,null);
    }

    public DialogContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DialogContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public DialogContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.dialog_container, this, true);
    }


    public <T extends View> void show(Class<T> cls, Object... arg){
        int count = getChildCount();
        for(int ix=0; ix<count;ix++){
            View view = getChildAt(ix);
            if( view.getClass() == cls){
                view.setVisibility( VISIBLE);
            }else{
                view.setVisibility( INVISIBLE);
            }
        }
    }
}
