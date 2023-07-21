package com.mediatek.hralauncher.model.impl;

import android.content.Context;
import android.util.Log;

import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.model.interf.IAppPosMgr;
import com.mediatek.hralauncher.util.SharepreferenceUtil;

import java.util.ArrayList;
import java.util.List;

public class AppPositionManager implements IAppPosMgr {
    private final Context mContext;
    public final SharepreferenceUtil mShareUtils;
    private String TAG="AppPositionManager.";

    public AppPositionManager(Context context) {
        this.mContext = context.getApplicationContext();
        this.mShareUtils = SharepreferenceUtil.getSharepferenceInstance(this.mContext);
    }

    @Override
    public List<List<AppInfo>> buildPages(String str, List<AppInfo> list) {
        Log.d(TAG,"buildPages "+str);
        List<List<AppInfo>> result = new ArrayList<>();
        return result;
    }
}
