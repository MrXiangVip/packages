package com.example.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;

public class SideBarViewPager extends ViewPager {
    public SideBarViewPager(@NonNull Context context) {
        this(context,null);
    }

    public SideBarViewPager(Context context, AttributeSet attributeSet ){
        super(context, attributeSet);
        init();
    }

    public void init(){
        setAdapter( new PagePadAdapter());
    }

    private class PagePadAdapter extends PagerAdapter{
        private final int[] layouts={
                R.layout.layout_performance_settings,
                R.layout.layout_gamepad_settings_container,
                R.layout.layout_system_settings,
        };
        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view ==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ScrollView scrollView = new ScrollView(container.getContext());
            View view = LayoutInflater.from(container.getContext())
                    .inflate(layouts[position], container, false);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            scrollView.addView(view, params);
            container.addView(scrollView);
            return scrollView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }
}


