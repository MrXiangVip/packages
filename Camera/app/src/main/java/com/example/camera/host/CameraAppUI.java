package com.example.camera.host;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.camera.ICameraSettingView;
import com.example.camera.SettingFragment;
import com.example.camera.common.IAppUiListener;
import com.example.camera.common.IAppUiListener.ISurfaceStatusListener;
import com.example.camera.common.PreviewFrameLayout;
import com.example.camera.common.RotateLayout;
import com.example.camera.effect.EffectViewManager;
import com.example.camera.gesture.GestureManager;
import com.example.camera.modepicker.ModePickerManager;
import com.example.camera.preference.GroupPreference;
import com.example.camera.setting.AISSettingView;
import com.example.camera.setting.PictureSizeSettingView;
import com.example.camera.setting.SceneModeSettingView;
import com.example.camera.setting.WhiteBalanceSettingView;
import com.example.camera.setting.XdfAntiFlickerSettingView;
import com.example.camera.setting.XdfPhotoMultiSettingView;
import com.example.camera.shutter.ShutterButtonManager;
import com.example.camera.ui.CameraSwitcherManager;
import com.example.camera.ui.QuickSwitcherManager;
import com.example.camera.ui.ThumbnailViewManager;
import com.example.camerabg.R;
import com.example.camera.common.IApp;
import com.example.camera.common.IAppUi;

import java.util.ArrayList;
import java.util.List;

public class CameraAppUI implements IAppUi {
    private final IApp mApp;

    private final OnOrientationChangeListenerImpl mOrientationChangeListener;

    private final List<IViewManager> mViewManagers;
    private ShutterButtonManager mShutterManager;
    private ModePickerManager mModePickerManager;
    private QuickSwitcherManager mQuickSwitcherManager;
    private CameraSwitcherManager mCameraSwitcherManager;
    private ThumbnailViewManager mThumbnailViewManager;

    private EffectViewManager mEffectViewManager;
    private PreviewManager mPreviewManager;

    private SettingFragment mSettingFragment;

    private List<String> entries = new ArrayList<>();
    private List<String> whilteBalanceEntries = new ArrayList<>();
    private List<Integer> icons = new ArrayList<>();

    private Handler mConfigUIHandler = new ConfigUIHandler();
    private static final int APPLY_ALL_UI_VISIBILITY = 0;
    private static final int APPLY_ALL_UI_ENABLED = 1;
    private static final int SET_UI_VISIBILITY = 2;
    private static final int SET_UI_ENABLED = 3;
    private String TAG="CameraAppUI";
    private GestureManager mGestureManager;

    private class SettingStateListener implements SettingFragment.StateListener {
        @Override
        public void onCreate() {
            View view = mApp.getActivity().findViewById(R.id.setting_ui_root);
            view.setVisibility(View.VISIBLE);

            applyAllUIVisibility(View.GONE);
//            setUIEnabled(SHUTTER_BUTTON, false);
        }
        @Override
        public void onResume() {

        }
        @Override
        public void onPause() {

        }
        @Override
        public void onDestroy() {
            View view = mApp.getActivity().findViewById(R.id.setting_ui_root);
            view.setVisibility(View.GONE);

            applyAllUIVisibility(View.VISIBLE);
//            setUIEnabled(SHUTTER_BUTTON, true);
        }
    }

    private class ConfigUIHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(TAG, "handleMessage what =  " + msg.what);
            switch (msg.what) {
                case APPLY_ALL_UI_VISIBILITY:
                    //call each manager's setVisibility()
                    int visibility = msg.arg1;
                    for (int count = 0; count < mViewManagers.size(); count++) {
                        mViewManagers.get(count).setVisibility(visibility);
                    }

                    break;
//                case APPLY_ALL_UI_ENABLED:
//                    //call each manager's setEnabled()
//                    boolean enabled = msg.arg1 == 1;
//                    for (int count = 0; count < mViewManagers.size(); count++) {
//                        mViewManagers.get(count).setEnabled(enabled);
//                    }
//                    break;
//                case SET_UI_VISIBILITY:
//                    configUIVisibility(msg.arg1, msg.arg2);
//                    break;
//                case SET_UI_ENABLED:
//                    configUIEnabled(msg.arg1, msg.arg2 == 1);
                default:
                    break;
            }
        }
    }

    private class OnTouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (mGestureManager != null) {

                mGestureManager.getOnTouchListener().onTouch(view, event);
            }
            return true;
        }
    }

    private  class  OnOrientationChangeListenerImpl implements  IApp.OnOrientationChangeListener{

        @Override
        public void onOrientationChanged(int orientation) {

        }
    }

    public CameraAppUI(IApp app) {
        mApp = app;
        mOrientationChangeListener = new OnOrientationChangeListenerImpl();
//        xshx add 20230204
//        mXdfAppUiGestureListener = new XdfAppUiGestureListener(mApp.getActivity());
        mViewManagers = new ArrayList<>();
    }

    public void onCreate() {
        ViewGroup rootView = (ViewGroup) mApp.getActivity().findViewById(R.id.app_ui_root);
        ViewGroup parentView = (ViewGroup) mApp.getActivity().getLayoutInflater().inflate( R.layout.camera_ui_root, rootView, true);
        View appUI = parentView.findViewById( R.id.camera_ui_root);

//      拍照按钮
        mShutterManager = new ShutterButtonManager(mApp, parentView);
        mShutterManager.setVisibility(View.VISIBLE);
        mShutterManager.registerDone();
        mViewManagers.add(mShutterManager);

//      设置按钮
        mQuickSwitcherManager = new QuickSwitcherManager(mApp, parentView);
        mQuickSwitcherManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mQuickSwitcherManager);
//      切换按钮
        mCameraSwitcherManager = new CameraSwitcherManager(mApp, parentView);
        mCameraSwitcherManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mCameraSwitcherManager);

        mModePickerManager = new ModePickerManager(mApp, parentView);
        mModePickerManager.setSettingClickedListener(new OnSettingClickedListenerImpl());
        mModePickerManager.setModeChangeListener(new OnModeChangedListenerImpl());
        mModePickerManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mModePickerManager);
//
        mEffectViewManager = new EffectViewManager(mApp, parentView);
        mEffectViewManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mEffectViewManager);
//      缩略图
        mThumbnailViewManager = new ThumbnailViewManager(mApp, parentView);
        mThumbnailViewManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mThumbnailViewManager);
//      预览
        mPreviewManager = new PreviewManager( mApp, parentView );
        mPreviewManager.setVisibility( View.VISIBLE);
        mPreviewManager.setOnTouchListener(new OnTouchListenerImpl());


        mSettingFragment = new SettingFragment();
        mSettingFragment.setStateListener(new SettingStateListener());

        initEntriesAndIcons();
        WhiteBalanceSettingView whiteBalanceSettingView = new WhiteBalanceSettingView(  mApp.getActivity(), "white balance");
        whiteBalanceSettingView.setEntryValues( entries);
        whiteBalanceSettingView.setValue( 10+"");
        addSettingView( whiteBalanceSettingView );

        SceneModeSettingView sceneModeSettingView = new SceneModeSettingView( mApp.getActivity(), "scene mode" );
        sceneModeSettingView.setEntryValues( entries);
        sceneModeSettingView.setValue( 10+"");
        addSettingView( sceneModeSettingView );

        AISSettingView aisSettingView = new AISSettingView( mApp.getActivity(), "ais " );
        addSettingView( aisSettingView);

        PictureSizeSettingView pictureSizeSettingView = new PictureSizeSettingView();
        pictureSizeSettingView.setEntryValues( entries );
        addSettingView(pictureSizeSettingView);

        XdfPhotoMultiSettingView xdfMultiSettingView = new XdfPhotoMultiSettingView( mApp.getActivity(), "multi setting");
        xdfMultiSettingView.setWhiteBalanceEntryValues(  );
        xdfMultiSettingView.setScenceModeEntryValues( );
        addSettingView( xdfMultiSettingView );


        XdfAntiFlickerSettingView xdfAntiFlickerSettingView = new XdfAntiFlickerSettingView(mApp.getActivity(), "shake");
        addSettingView( xdfAntiFlickerSettingView );

        for (int count = 0; count < mViewManagers.size(); count ++) {
            mViewManagers.get(count).onCreate();
        }
    }



    public void showSetting() {
        FragmentTransaction transaction = mApp.getActivity().getFragmentManager()
                .beginTransaction();
        transaction.setCustomAnimations( R.anim.slide_in_top, 0);
        transaction.addToBackStack("setting_fragment");
        transaction.replace(R.id.setting_container, mSettingFragment, "Setting")
                .commitAllowingStateLoss();
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
    public void onResume() {
        RotateLayout root = (RotateLayout) mApp.getActivity().findViewById(R.id.app_ui);
        Configuration newConfig = mApp.getActivity().getResources().getConfiguration();
        if( root != null ){
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                root.setOrientation(0, false);
            } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                root.setOrientation(90, false);
            }
        }
        for (int count = 0; count < mViewManagers.size(); count ++) {
            mViewManagers.get(count).onResume();
        }
    }

    @Override
    public void setModeChangeListener(IAppUiListener.OnModeChangeListener listener) {

    }

    @Override
    public void registerMode(List<ModeItem> items) {

        mApp.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterManager.registerDone();
            }
        });
    }

    @Override
    public void updateCurrentMode(String mode) {

    }
    public PreviewFrameLayout getPreviewFrameLayout() {
        return mPreviewManager.getPreviewFrameLayout();
    }

    @Override
    public void registerGestureListener(GestureDetector.OnGestureListener listener, int priority) {

    }


    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
    public void applyAllUIVisibility(final int visibility) {
        if (!isMainThread()) {
            Log.d(TAG, "applyAllUIVisibility + visibility " + visibility);
            Message msg = Message.obtain();
            msg.arg1 = visibility;
            msg.what = APPLY_ALL_UI_VISIBILITY;
            mConfigUIHandler.sendMessage(msg);
            Log.d(TAG, "applyAllUIVisibility -");
        } else {
            applyAllUIVisibilityImmediately(visibility);
        }
    }

    private void applyAllUIVisibilityImmediately(int visibility) {
        Log.d(TAG, "applyAllUIVisibilityImmediately + visibility " + visibility);
        mConfigUIHandler.removeMessages(APPLY_ALL_UI_VISIBILITY);
        for (int count = 0; count < mViewManagers.size(); count++) {
            mViewManagers.get(count).setVisibility(visibility);
        }
    }

    public void setPreviewSize(final int width, final int height,
                               final ISurfaceStatusListener listener) {
        mApp.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPreviewManager.updatePreviewSize(width, height, listener);
            }
        });
    }

    private class OnSettingClickedListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (mSettingFragment.hasVisibleChild()) {
                showSetting();
            }
        }
    }
    private class OnModeChangedListenerImpl implements ModePickerManager.OnModeChangedListener {
        @Override
        public void onModeChanged(String modeName) {

        }
    }
}
