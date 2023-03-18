package com.example.camera.loader;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.ConditionVariable;
import android.util.Log;

import com.example.camera.common.IApp;
import com.example.camera.common.IAppUi.ModeItem;
import com.example.camera.device.CameraDeviceManagerFactory.CameraApi;
import com.example.camera.mode.CameraApiHelper;
import com.example.camera.mode.DeviceUsage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class FeatureProvider {
    private ConcurrentHashMap<String, IFeatureEntry> mAllEntries = new ConcurrentHashMap<>();

    private final ConditionVariable mPluginLoadCondition = new ConditionVariable();
    private final ConditionVariable mBuildInLoadCondition = new ConditionVariable();
    private final Activity mActivity;
    private final Object mNotifyApi1Sync = new Object();
    private CopyOnWriteArrayList<FeatureLoadDoneListener> mFeatureLoadDoneListeners = new CopyOnWriteArrayList<>();
    private ConcurrentHashMap<String, IFeatureEntry> mBuildInEntries = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, IFeatureEntry> mPluginEntries = new ConcurrentHashMap<>();

    private CopyOnWriteArrayList<String> mNotifiedApi1BuildInCameraIds = new CopyOnWriteArrayList();

    private String TAG="FeatureProvider";
    public FeatureProvider(IApp app) {
        mActivity = app.getActivity();
        mPluginLoadCondition.close();
        mBuildInLoadCondition.close();
        loadFeatureInBackground();
    }
    private void loadFeatureInBackground() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                synchronized (mNotifyApi1Sync) {
                    mBuildInEntries = FeatureLoader.loadBuildInFeatures(mActivity);
                    mAllEntries.putAll(mBuildInEntries);
                    mBuildInLoadCondition.open();
                    notifyAllApi2BuildInFeatureLoadDone();

                    mPluginEntries = FeatureLoader.loadPluginFeatures(mActivity);
                    mAllEntries.putAll(mPluginEntries);
                    mPluginLoadCondition.open();
                    notifyAllApi2PluginFeatureLoadDone();
                    return null;
                }
            }
        } .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    private void notifyAllApi2BuildInFeatureLoadDone() {

    }
    private void notifyAllApi2PluginFeatureLoadDone() {

    }

    public void registerFeatureLoadDoneListener(
             FeatureLoadDoneListener pluginLoadDoneListener) {
        if (pluginLoadDoneListener == null) {
            return;
        }
        if (!mFeatureLoadDoneListeners.contains(pluginLoadDoneListener)) {
            mFeatureLoadDoneListeners.add(pluginLoadDoneListener);
        }
        postNotifiedBuildInFeatureLoadDone(pluginLoadDoneListener);
        postNotifiedPluginFeatureLoadDone(pluginLoadDoneListener);
    }

    private void postNotifiedBuildInFeatureLoadDone(FeatureLoadDoneListener loadDoneListener) {
        synchronized(mNotifyApi1Sync) {
            // Post API1
            for (String cameraId : mNotifiedApi1BuildInCameraIds) {
                loadDoneListener.onBuildInLoadDone(cameraId, CameraApi.API1);
            }
            // Post API2
            DeviceSpec deviceSpec = CameraApiHelper.getDeviceSpec(mActivity);
            if (deviceSpec == null || mBuildInEntries.size() <= 0) {
                return;
            }
            Collection<String> cameraIds = deviceSpec.getDeviceDescriptionMap().keySet();
            for (String cameraId : cameraIds) {
                loadDoneListener.onBuildInLoadDone(cameraId, CameraApi.API2);
            }
        }
    }
    private void postNotifiedPluginFeatureLoadDone(FeatureLoadDoneListener loadDoneListener) {

    }

    public <T> T getInstance( Key<T> key, CameraApi currentCameraApi,
                             boolean checkSupport) {
        if (!mAllEntries.containsKey(key.getName())) {
            mBuildInLoadCondition.block();
        }
        if (!mAllEntries.containsKey(key.getName())) {
            mPluginLoadCondition.block();
        }
        IFeatureEntry entry = mAllEntries.get(key.getName());
        Log.d(TAG, "[getInstance],key = " + key.getName() + ",entry = " + entry);
        if (entry == null) {
            return null;
        }
        if (checkSupport) {
            return entry.isSupport(currentCameraApi, mActivity) ? (T) entry.createInstance() : null;
        }
        return (T) entry.createInstance();
    }
    public void updateCurrentModeKey(String currentModeKey) {

    }
    public DeviceUsage updateDeviceUsage(String modeKey, DeviceUsage originalDeviceUsage) {
        DeviceUsage tempDeviceUsage = originalDeviceUsage;
        return tempDeviceUsage;

    }

    public List<ModeItem> getAllModeItems( CameraApi currentCameraApi) {
        ArrayList list = new ArrayList();
        // get the values from hashMap use iterator method.
        Iterator iterator = mAllEntries.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry item = (Map.Entry) iterator.next();
            IFeatureEntry entry = (IFeatureEntry) item.getValue();
            if (entry.isSupport(currentCameraApi, mActivity)) {
                ModeItem modeItem = entry.getModeItem();
                if (modeItem != null) {
                    list.add(modeItem);
                }
            }
        }
        return list;
    }

    public interface FeatureLoadDoneListener {
        void onBuildInLoadDone(String cameraId, CameraApi cameraApi);
        void onPluginLoadDone(String cameraId, CameraApi cameraApi);

    }
    public static final class Key<T> {
        private final String mName;
        private final Class<T> mClassType;
        /**
         * Key constructor.
         * @param name Key's name, it should be same with the feature name.
         * @param classType The feature' class type, if the feature is a
         *                  mode, the type will be ICameraMode.class, if the
         *                  feature is a setting, the type will be ICameraSetting.class.
         */
        public Key(String name, Class<T> classType) {
            mName = name;
            mClassType = classType;
        }

        /**
         * Get the key description name.
         * @return The key name.
         */
        public String getName() {
            return mName;
        }

        @Override
        public final int hashCode() {
            return mName.hashCode();
        }

        @SuppressWarnings("unchecked")
        @Override
        public final boolean equals( Object object) {
            return object instanceof Key && ((Key<T>) object).mName.equals(mName);
        }
    }
}
