package com.example.myapplication.widget;

import static android.content.Context.WINDOW_SERVICE;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class SideBarView extends LinearLayout implements IFloatingView{
    private View ui;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context   mContext;
    private WindowManager windowManager;

    public SideBarView(Context context) {
        this(context,null);
    }

    public SideBarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SideBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public SideBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init(mContext);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void init(Context mContext){
        ui = LayoutInflater.from(mContext).inflate(R.layout.layout_side_bar,this,true);
        tabLayout = ui.findViewById(R.id.tabs);
        viewPager =ui.findViewById(R.id.pager);
        tabLayout.setupWithViewPager( viewPager);
//
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_performance_default);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_controller_default);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab_system_default);
    }

    @Override
    public void show() {
        if( getParent()==null ){
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.width = (int) (mContext.getResources().getDisplayMetrics().widthPixels * 0.5);
            params.height = WindowManager.LayoutParams.MATCH_PARENT;
            params.gravity = Gravity.RIGHT;
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
