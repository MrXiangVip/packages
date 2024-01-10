package com.android.lock;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toolbar;

import com.android.lock.fragment.EnableLockFragment;
import com.android.lock.fragment.InputPasswordFragment;
//import android.app.ActivityTaskManager;

public class MainActivity extends Activity {


    private Intent preIntent;
    private Bundle preOption;
    private String action;
    private String TAG="MainActivity.XSHX.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_main);
        initActionBar();
    }

    public void initActionBar( ){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.app_name);
        }
    }

    public  boolean parseIntent( ){
        Intent intent = getIntent();
        action = intent.getAction();
        preOption = intent.getParcelableExtra("options");
        preIntent = intent.getParcelableExtra("intent");
//        startActivityForResult( intent, resultCode, option);
        return  false;
    }

    public void setActionBarTitle(String title){
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(title);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Bundle getPreOption( ){
        return   preOption;
    }

    public  Intent getPreIntent( ){
        return  preIntent;
    }
    public String getPreAppName()  {
        String appLabel=null;
        try{
            if( preIntent !=null ){
                String packageName = preIntent.getComponent().getPackageName();
                if( packageName.equals( getPackageName())){
                    return  null;
                }
                appLabel = (String) getPackageManager().getApplicationInfo(packageName, 0).loadLabel(getPackageManager());
                Log.d(TAG, "label "+appLabel);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return appLabel;
    }

    public String getAction( ){
        return  action;
    }

    public void showFragment(Fragment fragment) {
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.commit();
    }

    public void pushBackFragment(Fragment fragment ){
        final FragmentManager fragmentManager = getFragmentManager();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_content, fragment);
        transaction.addToBackStack( "TAG");
        transaction.commit();
    }

    public void popBackFragment(){
        final FragmentManager fragmentManager = getFragmentManager();
        int backStackCount = fragmentManager.getBackStackEntryCount();
        if (backStackCount > 0) {
            fragmentManager.popBackStack("TAG",  FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        parseIntent();
//      如果没有开启去往点击开启界面
//        if( SystemProperties.getBoolean("persist.vendor.application.lock.enable", false)){
        if( true ){
            showFragment( new EnableLockFragment());
        }else{
            showFragment(new InputPasswordFragment());
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
        finish();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, " onDestroy ");
        super.onDestroy();

    }

    public void goBack(View view){
        onBackPressed();
    }

    public void removeAllRecentTasks() {
        try {
//            ActivityTaskManager.getService().removeAllVisibleRecentTasks();
        } catch (Exception e) {
            Log.w(TAG, "Failed to remove all tasks", e);
        }
    }
}