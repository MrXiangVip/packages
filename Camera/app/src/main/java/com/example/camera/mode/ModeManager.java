package com.example.camera.mode;

import static com.example.camera.mode.ICameraMode.MODE_DEVICE_STATE_PREVIEWING;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.example.camera.common.CameraContext;
import com.example.camera.common.IApp;
import com.example.camera.common.IAppUi;
import com.example.camera.common.IAppUiListener;
import com.example.camera.common.ICameraContext;
import com.example.camera.common.IModeListener;
import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;
import com.example.camera.loader.FeatureProvider;
import com.example.camera.loader.FeatureProvider.FeatureLoadDoneListener;

import java.util.ArrayList;
import java.util.List;

public class ModeManager implements IModeListener , IAppUiListener.OnModeChangeListener {
    private IApp mApp;
    private IAppUi mAppUi;
    private ICameraContext mCameraContext;
    private ModeHandler mModeHandler;
    private static final String DEFAULT_CAPTURE_MODE =
            "com.mediatek.camera.common.mode.photo.PhotoModeEntry";

    private static final int MSG_MODE_INIT = 2;
    private static final int MSG_MODE_RESUME = 3;
    private static final int MSG_MODE_UNINIT = 4;
    private static final int MSG_MODE_PAUSE = 5;
    private static final int MSG_MODE_ON_CAMERA_SELECTED = 6;

    private ICameraMode mNewMode;
    private ICameraMode mOldMode;
    private DeviceUsage mCurrentModeDeviceUsage = null;
    private CameraApi mCurrentCameraApi;
    private String mCurrentEntryKey;
    private final FeatureLoadListener mPluginLoadListener = new FeatureLoadListener();

    private String TAG = "ModeManager";

    @Override
    public void create(IApp app) {
        mApp = app;
        HandlerThread th = new HandlerThread("mode thread",
                android.os.Process.THREAD_PRIORITY_URGENT_DISPLAY);
        th.start();

        mModeHandler = new ModeHandler(th.getLooper());

        mCameraContext = new CameraContext();
        mCameraContext.create(mApp, mApp.getActivity());

        mAppUi = app.getAppUi();
        mAppUi.setModeChangeListener(this);

        String defaultModeKey = getDefaultModeKey();
        Log.i(TAG, "[create], default mode:" + defaultModeKey);

        mNewMode = createMode(defaultModeKey);
        mModeHandler.obtainMessage(MSG_MODE_INIT,
                new MsgParam(mNewMode, true)).sendToTarget();
        //this can't be after mOldMode = mNewMode;
        mCurrentModeDeviceUsage = createDeviceUsage(mNewMode);
        mCurrentCameraApi = mNewMode.getCameraApi();
        mOldMode = mNewMode;
        mCameraContext.getFeatureProvider().registerFeatureLoadDoneListener(mPluginLoadListener);
    }

    private String getDefaultModeKey() {
        String defaultModeKey = DEFAULT_CAPTURE_MODE;
        return defaultModeKey;

    }

    private ICameraMode createMode(String entryKey) {
        String tempEntryKey = entryKey;
        ICameraMode cameraMode = mCameraContext.getFeatureProvider().getInstance(
                new FeatureProvider.Key<>(tempEntryKey, ICameraMode.class),
                null,
                false);
        // if current entry key can not create mode, back to default camera mode.
        if (cameraMode == null) {
            tempEntryKey = DEFAULT_CAPTURE_MODE;
            cameraMode = mCameraContext.getFeatureProvider().getInstance(
                    new FeatureProvider.Key<>(tempEntryKey, ICameraMode.class),
                    null,
                    false); // don't check support, because camera may not opened.
        }
        mCurrentEntryKey = tempEntryKey;
        mCameraContext.getFeatureProvider().updateCurrentModeKey(cameraMode.getModeKey());
        Log.i(TAG, "[createMode] entryKey:" + mCurrentEntryKey);
        return cameraMode;
    }

    private DeviceUsage createDeviceUsage(ICameraMode currentMode) {
        if (mOldMode != null) {
            //update current old mode device usage again.
            mCurrentModeDeviceUsage = mOldMode.getDeviceUsage(mCameraContext.getDataStore(), null);
            mCurrentModeDeviceUsage = mCameraContext.getFeatureProvider().updateDeviceUsage(
                    mOldMode.getModeKey(), mCurrentModeDeviceUsage);
        }
        DeviceUsage newDeviceUsage = currentMode.getDeviceUsage(mCameraContext.getDataStore(),
                mCurrentModeDeviceUsage);
        String modeKey = currentMode.getModeKey();
        return mCameraContext.getFeatureProvider().updateDeviceUsage(modeKey, newDeviceUsage);
    }

    @Override
    public void resume() {

    }

    @Override
    public void onModeSelected(String newModeKey) {

    }

    private class MsgParam {
        /**
         * Construct messge parameter.
         *
         * @param mode current mode
         * @param obj  parameter
         */
        public MsgParam(ICameraMode mode, Object obj) {
            mMode = mode;
            mObj = obj;
        }

        public ICameraMode mMode;
        public Object mObj;
    }

    private class ModeHandler extends Handler {

        public ModeHandler(Looper looper) {
            super(looper);
        }
    }

    private class FeatureLoadListener implements FeatureLoadDoneListener {

        @Override
        public void onBuildInLoadDone(String cameraId, CameraApi cameraApi) {
            List<IAppUi.ModeItem> modeItems = new ArrayList<>();
            if (cameraApi.equals(mCurrentCameraApi)) {
                modeItems =
                        mCameraContext.getFeatureProvider().getAllModeItems(mCurrentCameraApi);
                if (modeItems.size() > 0) {
                    mAppUi.registerMode(modeItems);
                    mAppUi.updateCurrentMode(mCurrentEntryKey);
                }
            }
        }

        @Override
        public void onPluginLoadDone(String cameraId, CameraApi cameraApi) {
            List<IAppUi.ModeItem> modeItems = new ArrayList<>();
            if (cameraApi.equals(mCurrentCameraApi)) {
                modeItems =
                        mCameraContext.getFeatureProvider().getAllModeItems(mCurrentCameraApi);
                if (modeItems.size() > 0) {
                    mAppUi.registerMode(modeItems);
                    mAppUi.updateCurrentMode(mCurrentEntryKey);
                }
            }
        }
    }
}