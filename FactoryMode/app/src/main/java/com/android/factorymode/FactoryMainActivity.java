package com.android.factorymode;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.android.factorymode.util.ConfigDatas;

public class FactoryMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        if( true ){
            replaceFragment( new FactoryTestFragment());
        }
    }

    private void initData( ){
        ConfigDatas configDatas = ConfigDatas.getInstance(getBaseContext());
    }

    public void replaceFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();
    }


    public void pushBackFragment( String fragmentName, Bundle args ){
        Fragment fragment = Fragment.instantiate(this, fragmentName, args);

        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack( "TAG");
        transaction.commit();
    }
}