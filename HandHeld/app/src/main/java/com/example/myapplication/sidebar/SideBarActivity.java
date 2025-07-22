package com.example.myapplication.sidebar;

import android.content.Context;
import android.os.Bundle;
//import me.jessyan.autosize.AutoSizeCompat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.R;
import com.google.android.material.tabs.TabLayout;

public class SideBarActivity extends AppCompatActivity {

    private TabLayout mTabLayout;

    private ViewPager mViewPager;

    private PageAdapter mFragmentAdapter;

    private TabLayout.Tab one;

    private TabLayout.Tab two;

    private TabLayout.Tab three;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        AutoSizeCompat.autoConvertDensityBaseOnWidth(getResources(), width);
        setContentView(R.layout.layout_side_bar);
        initView(getBaseContext());
    }

    private void initView(Context context) {
        //利用适配器 mFragmentAdapter 将 ViewPager 和 Fragment 绑定在一起
        mViewPager = findViewById(R.id.pager);
        mFragmentAdapter = new PageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentAdapter);

        //将 TabLayout 和 ViewPager 绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);

        //设置Tab的图标，假如不需要则把下面的代码删去
        one.setIcon(R.mipmap.ic_launcher);
        two.setIcon(R.mipmap.ic_launcher);
        three.setIcon(R.mipmap.ic_launcher);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
