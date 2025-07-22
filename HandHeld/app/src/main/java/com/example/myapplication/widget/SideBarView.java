package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class SideBarView extends LinearLayout {
    private View ui;
    private TabLayout tabLayout;
    private ViewPager    viewPager;
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

        ui = LayoutInflater.from(context).inflate(R.layout.layout_side_bar,this,true);
        tabLayout = ui.findViewById(R.id.tabs);
        viewPager =ui.findViewById(R.id.pager);
        init();
    }

    public void init(){
        tabLayout.setupWithViewPager( viewPager);
//
        tabLayout.getTabAt(0).setIcon(R.drawable.tab_performance_default);
        tabLayout.getTabAt(1).setIcon(R.drawable.tab_controller_default);
        tabLayout.getTabAt(2).setIcon(R.drawable.tab_system_default);
    }

}
