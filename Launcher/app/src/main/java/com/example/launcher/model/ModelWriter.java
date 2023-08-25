package com.example.launcher.model;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.launcher.LauncherModel;

public class ModelWriter {
    private final Context mContext;
    private final LauncherModel mModel;
    private final BgDataModel mBgDataModel;
    private final Handler mUiHandler;
    private final boolean mHasVerticalHotseat;
    private final boolean mVerifyChanges;
    public ModelWriter(Context context, LauncherModel model, BgDataModel dataModel,
                       boolean hasVerticalHotseat, boolean verifyChanges) {
        mContext = context;
        mModel = model;
        mBgDataModel = dataModel;
        mHasVerticalHotseat = hasVerticalHotseat;
        mVerifyChanges = verifyChanges;
        mUiHandler = new Handler(Looper.getMainLooper());
    }
}
