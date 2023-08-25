package com.example.launcher;

import android.view.View;

import com.example.launcher.touch.ItemClickHandler;

public class BaseDraggingActivity extends  BaseActivity{


    public View.OnClickListener getItemOnClickListener() {
        return ItemClickHandler.INSTANCE;
    }

}
