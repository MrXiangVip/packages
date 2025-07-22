package com.example.myapplication.sidebar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.sidebar.fragment.FragmentGamepadSetting;
import com.example.myapplication.sidebar.fragment.FragmentPerformanceSetting;
import com.example.myapplication.sidebar.fragment.FragmentSystemSetting;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList= new ArrayList<>();

    private FragmentSystemSetting   fragmentSystemSettings;
    private FragmentGamepadSetting fragmentGamepadSetting;
    private FragmentPerformanceSetting  fragmentPerformanceSetting;
    public PageAdapter(@NonNull FragmentManager fm) {
        super(fm);
        initFragments();
    }

    public void initFragments(){
        fragmentSystemSettings = new FragmentSystemSetting();
        fragmentGamepadSetting = new FragmentGamepadSetting();
        fragmentPerformanceSetting = new FragmentPerformanceSetting();
        fragmentList.add( fragmentSystemSettings);
        fragmentList.add( fragmentGamepadSetting);
        fragmentList.add( fragmentPerformanceSetting);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return  fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
