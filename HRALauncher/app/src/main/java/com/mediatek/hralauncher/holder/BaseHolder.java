package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.application.LauncherApplication;

public abstract class BaseHolder {
    private final String TAG = "BaseHolder.";
    Context context;
    View mRootView;

    abstract int getLayoutId();

    abstract void initView(Context context, View view);

    public void initDataBeforeInflate(Context context) {
    }

    public void onCreate() {
    }

    BaseHolder(Context mContext) {
        context = mContext;
        initDataBeforeInflate(context);
        long time = System.currentTimeMillis();
        if (R.layout.viewholder_first_ai == getLayoutId()) {
            mRootView = LauncherApplication.view;
            if (null == mRootView) {
                mRootView = LayoutInflater.from(context).inflate(getLayoutId(), null, false);
            }
        } else {
            mRootView = LayoutInflater.from(context).inflate(getLayoutId(), null, false);
            //ScreenAdapterTools.getInstance()?.loadView(mRootView!!)
        }
        Log.d(TAG, "inflate耗时：(" + this.getClass().getSimpleName() + "):" + (System.currentTimeMillis() - time));
        initView(context, mRootView);
    }

    public View getRootView() {
        return mRootView;
    }

    public  void onScrollIn(){
    }

    public  void onScrollOut(){
    }
}
