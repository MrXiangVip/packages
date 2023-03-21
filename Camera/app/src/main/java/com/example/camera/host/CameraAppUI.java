package com.example.camera.host;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.view.GestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.camera.ICameraSettingView;
import com.example.camera.SettingFragment;
import com.example.camera.common.IAppUiListener;
import com.example.camera.common.PreviewFrameLayout;
import com.example.camera.effect.EffectViewManager;
import com.example.camera.modepicker.ModePickerManager;
import com.example.camera.setting.AISSettingView;
import com.example.camera.setting.PictureSizeSettingView;
import com.example.camera.setting.SceneModeSettingView;
import com.example.camera.setting.WhiteBalanceSettingView;
import com.example.camera.setting.XdfPhotoMultiSettingView;
import com.example.camera.shutter.ShutterButtonManager;
import com.example.camera.ui.CameraSwitcherManager;
import com.example.camera.ui.QuickSwitcherManager;
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

    private EffectViewManager mEffectViewManager;
    private PreviewManager mPreviewManager;

    private SettingFragment mSettingFragment;

    private List<String> entries = new ArrayList<>();
    private List<String> whilteBalanceEntries = new ArrayList<>();
    private List<Integer> icons = new ArrayList<>();

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


        mShutterManager = new ShutterButtonManager(mApp, parentView);
        mShutterManager.setVisibility(View.VISIBLE);
        mShutterManager.registerDone();
        mViewManagers.add(mShutterManager);

        mCameraSwitcherManager = new CameraSwitcherManager(mApp, parentView);
        mCameraSwitcherManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mCameraSwitcherManager);

        mModePickerManager = new ModePickerManager(mApp, parentView);
//        mModePickerManager.setSettingClickedListener(new OnSettingClickedListenerImpl());
//        mModePickerManager.setModeChangeListener(new OnModeChangedListenerImpl());
        mModePickerManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mModePickerManager);

        mQuickSwitcherManager = new QuickSwitcherManager(mApp, parentView);
        mQuickSwitcherManager.setVisibility(View.VISIBLE);
//        mQuickSwitcherManager.setModeChangeListener(new OnQuickModeChangedListenerImpl());
        mViewManagers.add(mQuickSwitcherManager);

        mEffectViewManager = new EffectViewManager(mApp, parentView);
        mEffectViewManager.setVisibility(View.VISIBLE);
        mViewManagers.add(mEffectViewManager);

        mPreviewManager = new PreviewManager( mApp );
        mPreviewManager.init();
        mSettingFragment = new SettingFragment();

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
//        RotateLayout root = (RotateLayout) mApp.getActivity().findViewById(R.id.app_ui);
        Configuration newConfig = mApp.getActivity().getResources().getConfiguration();
//        for (int count = 0; count < mViewManagers.size(); count ++) {
//            mViewManagers.get(count).onResume();
//        }
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


    private  class  OnOrientationChangeListenerImpl implements  IApp.OnOrientationChangeListener{

        @Override
        public void onOrientationChanged(int orientation) {

        }
    }
}
