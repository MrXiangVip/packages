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

public class FloatingView {
    private static SideBarView sidebarView;
    private WindowManager windowManager;
//    private ViewPager viewPager;
    private static FloatingView mInstance;
    private static Context mContext;

//    private SideBarViewPager pageAdapter;

    private SideBarViewPager viewPager;
    private FloatingView(Context context){
        sidebarView = new SideBarView(context);


    }
    public static FloatingView getInstance(Context context){
        mContext = context;

        if(mInstance==null){
            mInstance = new FloatingView(mContext);
        }
        return mInstance;
    }
    public void show() {
        if( sidebarView.getParent()==null ){
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.5);
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.RIGHT;
            params.format = PixelFormat.TRANSLUCENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
            windowManager = (WindowManager) mContext.getSystemService(WINDOW_SERVICE);
            windowManager.addView(sidebarView, params);

        }

    }
    public void dismiss() {
        if (sidebarView.getParent() != null) {
            windowManager.removeView(sidebarView);
        }
    }
}
