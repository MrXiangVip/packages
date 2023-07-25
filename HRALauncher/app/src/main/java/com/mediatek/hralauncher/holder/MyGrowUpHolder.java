package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.view.View;

import com.mediatek.hralauncher.R;

public class MyGrowUpHolder extends BaseHolder{
    public MyGrowUpHolder(Context mContext) {
        super(mContext);
    }

    @Override
    public void onScrollIn() {
        super.onScrollIn();
    }

    @Override
    public void onScrollOut() {
        super.onScrollOut();
    }

    @Override
    int getLayoutId() {
        return R.layout.view_holder_my_grow_up;
    }

    @Override
    void initView(Context context, View view) {

    }
}
