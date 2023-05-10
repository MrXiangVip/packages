package com.mediatek.factorytest;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import com.mediatek.factorytest.fragment.GridItemFragment;
import com.mediatek.factorytest.fragment.ListItemFragment;
import com.mediatek.factorytest.model.FactoryDatas;
import com.mediatek.factorytest.utils.Utils;

public class MainActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    private ListItemFragment listItemFragment;
    private GridItemFragment gridItemFragment;


    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private int    fragmentIndex;
    private final String TAG = Utils.TAG + "MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        listItemFragment = new ListItemFragment();
        gridItemFragment = new GridItemFragment();

        drawerLayout = findViewById(R.id.drawer_layout);

        fragmentIndex  = FactoryDatas.parseFragmentIndex( getBaseContext() );
        if( fragmentIndex ==0 ){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame,  gridItemFragment).commit();
        }else{
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame,  listItemFragment).commit();
//            drawerLayout.setDrawerLockMode( DrawerLayout.LOCK_MODE_LOCKED_CLOSED );
        }

    }

    protected void onResume(){
        super.onResume();
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: ");
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        switch (item.getItemId()) {
            case R.id.grid_fragment:
                transaction.replace(R.id.content_frame,  gridItemFragment).commit();
                break;
            case R.id.list_fragment:
                transaction.replace(R.id.content_frame,  listItemFragment).commit();
                break;
            case R.id.hardware_info:
                Intent miIntent = new Intent();
                miIntent.setAction("com.mediatek.intent.action.HardwareInformationActivity");
                //miIntent.putExtra("textall", true);
                startActivityForResult(miIntent, Utils.REQUEST_CODE );

                break;
            case R.id.exit_test:
                finish();
                break;
        }
        drawerLayout.closeDrawers();
        return true;
    }

    //  按 backup 键不返回
    public void onBackPressed() {
        return;
    }
}