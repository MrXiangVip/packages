package com.example.settings.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.UserHandle;
import android.util.Log;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class LocalBluetoothManager {

    private static final String TAG = "LocalBluetoothManager ";
    private final BroadcastReceiver mBroadcastReceiver = new BluetoothBroadcastReceiver();
    private static LocalBluetoothManager sInstance;
    private final UserHandle mUserHandle;
    private final Context mContext;
    private final IntentFilter mAdapterIntentFilter;
    private final Map<String, EventHandler > mHandlerMap;
    private final Collection<BluetoothCallback> mCallbacks = new CopyOnWriteArrayList<>();

    private final Handler  mReceiverHandler;
    interface EventHandler {
        void onReceive(Context context, Intent intent, BluetoothDevice device);
    }
    private class BluetoothBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(TAG, "onReceive "+action);
            BluetoothDevice device = intent
                    .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            Log.d(TAG, device.getAddress() + device.getName());

            EventHandler handler = mHandlerMap.get(action);
            if (handler != null) {
                handler.onReceive(context, intent, device);
            }
        }
    }

    private LocalBluetoothManager(Context context, Handler handler, UserHandle userHandle) {
        mContext = context;
        mUserHandle = userHandle;
        mAdapterIntentFilter = new IntentFilter();
        mHandlerMap = new HashMap<>();
        mReceiverHandler = handler;

        addHandler(BluetoothDevice.ACTION_FOUND, new DeviceFoundHandler());
        registerAdapterIntentReceiver();
    }

    void addHandler(String action, EventHandler handler) {
        mHandlerMap.put(action, handler);
        mAdapterIntentFilter.addAction(action);
    }

    void registerAdapterIntentReceiver() {
        registerIntentReceiver(mBroadcastReceiver, mAdapterIntentFilter);
    }

    private void registerIntentReceiver(BroadcastReceiver receiver, IntentFilter filter) {
            // If userHandle has not been provided, simply call registerReceiver.
            mContext.registerReceiver(receiver, filter, null, mReceiverHandler);
    }

    public static synchronized LocalBluetoothManager getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LocalBluetoothManager(context, /* handler= */ null, null);

        }
        return sInstance;
    }
    public void registerCallback(BluetoothCallback callback) {
        mCallbacks.add(callback);
    }
    void dispatchDeviceAdded(BluetoothDevice cachedDevice) {
        for (BluetoothCallback callback : mCallbacks) {
            callback.onDeviceAdded(cachedDevice);
        }
    }
    private class DeviceFoundHandler implements EventHandler {
        @Override
        public void onReceive(Context context, Intent intent, BluetoothDevice device) {
            Log.d(TAG, "onReceive" +device);
            dispatchDeviceAdded( device);
        }
    }
}