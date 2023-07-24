package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.adapter.ThirdAppAdapter;
import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.widget.layout.ThirdAppClassifyView;

import java.util.ArrayList;
import java.util.LinkedList;

public class ThirdPartAppHolder extends BaseHolder implements View.OnClickListener{
    private Context             mContext;
    private ThirdAppAdapter     mAdapter = null;
    private ArrayList<AppInfo>  mAppInfos= new ArrayList();
    ThirdAppClassifyView        mIconListView;
    private String TAG="ThirdPartAppHolder.";

    public ThirdPartAppHolder(Context context, ArrayList<AppInfo> appList) {
        super(context);
        mContext = context;
        mAppInfos = appList;
    }

    @Override
    int getLayoutId() {
        return R.layout.viewholder_thirdpart_app;
    }

    @Override
    void initView(Context context, View rootView) {
        mIconListView = rootView.findViewById( R.id.classify_view );
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDataView();
    }

    private void initDataView() {
        mAdapter = new ThirdAppAdapter(mContext);
        mIconListView.setAdapter( mAdapter );
        mAdapter.setMockSource( mAppInfos );
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick");
    }
}
