package com.example.myapplication.widget;

import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class FloatingView {
    private static SideBarView sidebarView;
    private static GamepadConfigEditor  gamepadConfigEditor;
    private static FloatingView mInstance;
    private static Context mContext;

    private Map<Class<? extends View>, View> views = new HashMap<>();
    private FloatingView(Context context){
        sidebarView = new SideBarView(context);
        gamepadConfigEditor = new GamepadConfigEditor(context);
        views.put(SideBarView.class, sidebarView);
        views.put(GamepadConfigEditor.class, gamepadConfigEditor);
    }
    public static FloatingView getInstance(Context context){
        mContext = context;

        if(mInstance==null){
            mInstance = new FloatingView(mContext);
        }
        return mInstance;
    }

    public <T extends View> T get(Class<T> cls){
        T view = (T)views.get(cls);
        if( view ==null){
            return null;
        }
        return view;
    }


    public void dismissAll( ){
        for(Map.Entry<Class<? extends View>, View> entry:views.entrySet()){
            ((IFloatingView)entry).dismiss();
        }
    }
}
