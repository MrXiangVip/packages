package com.example.camera;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.example.camera.common.IApp;
import com.example.camera.common.IAppUi;
import com.example.camera.common.IModeListener;
import com.example.camera.common.ModeManager;
import com.example.camera.host.CameraAppUI;
import com.example.camera.preference.RecyclerAdapter;
import com.example.camera.setting.AISSettingView;
import com.example.camera.setting.SceneModeSettingView;
import com.example.camera.setting.WhiteBalanceSettingView;
import com.example.camera.setting.PictureSizeSettingView;
import com.example.camera.setting.XdfPhotoMultiSettingView;
import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends Activity implements IApp {
    private String TAG ="Camera";

    private List<String> entries = new ArrayList<>();
    private List<String> whilteBalanceEntries = new ArrayList<>();
    private List<Integer> icons = new ArrayList<>();

    private SettingFragment mSettingFragment;

    private ImageButton imageButton;
    private boolean mChecked=false;
// 手势
    private GestureDetector mDetector;
    private GestureListener mGestureListener;

    private CameraAppUI mCameraAppUI;
    private IModeListener mIModeListener;

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public IAppUi getAppUi() {
        return mCameraAppUI;
    }

    private class GestureListener implements  GestureDetector.OnGestureListener{


        @Override
        public boolean onDown(MotionEvent e) {
            Log.d(TAG, "onDown:按下");
            FragmentTransaction transaction = getFragmentManager()
                    .beginTransaction();
            getFragmentManager().popBackStack();
            transaction.commit();
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.d(TAG, "onShowPress:手指按下一段时间,不过还没到长按");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.d(TAG, "onSingleTapUp:手指离开屏幕的一瞬间");
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(TAG, "onScroll:在触摸屏上滑动");
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.d(TAG, "onLongPress:长按并且没有松开");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling:迅速滑动，并松开");
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        return  mDetector.onTouchEvent( event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_LAYOUT_FLAGS
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        setContentView(R.layout.activity_main);

        mCameraAppUI = new CameraAppUI( this);
        mCameraAppUI.onCreate();


        mIModeListener = new ModeManager();
        mIModeListener.create(this);


        initEntriesAndIcons();


        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter=new RecyclerAdapter(entries, entries, icons);
        recyclerView.setAdapter(adapter);



        mSettingFragment = new SettingFragment();
        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        transaction.addToBackStack("setting_fragment");
        transaction.replace(R.id.setting_container, mSettingFragment, "Setting")
                .commitAllowingStateLoss();


        WhiteBalanceSettingView whiteBalanceSettingView = new WhiteBalanceSettingView(  this, "white balance");
        whiteBalanceSettingView.setEntryValues( entries);
        whiteBalanceSettingView.setValue( 10+"");
        addSettingView( whiteBalanceSettingView );


        SceneModeSettingView sceneModeSettingView = new SceneModeSettingView( this, "scene mode" );
        sceneModeSettingView.setEntryValues( entries);
        sceneModeSettingView.setValue( 10+"");
        addSettingView( sceneModeSettingView );


        AISSettingView aisSettingView = new AISSettingView( this, "ais " );
        addSettingView( aisSettingView);

        PictureSizeSettingView pictureSizeSettingView = new PictureSizeSettingView();
        pictureSizeSettingView.setEntryValues( entries );
        addSettingView(pictureSizeSettingView);


        XdfPhotoMultiSettingView xdfMultiSettingView = new XdfPhotoMultiSettingView( this, "multi setting");
        xdfMultiSettingView.setWhiteBalanceEntryValues( entries );
        addSettingView( xdfMultiSettingView );

        mGestureListener = new GestureListener();
        mDetector = new GestureDetector(this, mGestureListener);

    }

    protected void onResume( ) {
        mCameraAppUI.onResume();
        super.onResume();

    }

    public void addSettingView(ICameraSettingView view) {
        mSettingFragment.addSettingView(view);
    }

    private void initEntriesAndIcons() {

        for(int i=0; i <24; i++ ){
            entries.add(""+i);
            whilteBalanceEntries.add("white balance "+i);
            int id = R.drawable.ic_launcher_foreground;
            icons.add( id );
        }
    }
}