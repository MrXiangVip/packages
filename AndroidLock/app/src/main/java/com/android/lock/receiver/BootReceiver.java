package com.android.lock.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private String TAG="BootReceiver.XSHX";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "action "+intent.getAction());
    }
}
