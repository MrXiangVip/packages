package com.example.camera;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.camera.common.IApp;
import com.example.camera.common.IAppUi;
import com.example.camera.common.IModeListener;
import com.example.camera.common.PreviewFrameLayout;
import com.example.camera.common.RotateImageView;
import com.example.camera.exposure.ExposureView;
import com.example.camera.focus.FocusView;
import com.example.camera.host.PreviewManager;
import com.example.camera.mode.ModeManager;
import com.example.camera.host.CameraAppUI;
import com.example.camera.preference.RecyclerAdapter;
import com.example.camera.setting.AISSettingView;
import com.example.camera.setting.SceneModeSettingView;
import com.example.camera.setting.WhiteBalanceSettingView;
import com.example.camera.setting.PictureSizeSettingView;
import com.example.camera.setting.XdfPhotoMultiSettingView;
import com.example.camera.shutter.ShutterRootLayout;
import com.example.camerabg.R;

import java.util.ArrayList;
import java.util.List;

public class CameraActivity extends Activity implements IApp {
    private String TAG ="Camera";

    private RotateImageView flash;
    private RotateImageView mode;
    private RotateImageView hdr;
    private boolean mChecked=false;
// 手势
    private GestureDetector mDetector;
    private GestureListener mGestureListener;

    private CameraAppUI mCameraAppUI;
    private IModeListener mIModeListener;
    protected Animation mFadeIn, mFadeOut;
    private ViewGroup   mOptionRoot;
    private View        mOptionLayout;
    private View        mFlashChoiceView;
    private ImageView   mFlashOffIcon;
    private ImageView   mFlashAutoIcon;
    private ImageView   mFlashOnIcon;
    private LinearLayout mQuickSwitcherLayout;
    private View mTopBar;

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

        mGestureListener = new GestureListener();
        mDetector = new GestureDetector(this, mGestureListener);

    }


    protected void onResume( ) {
        mCameraAppUI.onResume();
        super.onResume();
    }

}