package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.view.View;

import com.mediatek.hralauncher.R;

public class AiRecommendedViewHolder extends BaseHolder{

    Context mContext;
    @Override
    int getLayoutId() {
        return R.layout.viewholder_ai_recommended;
    }

    @Override
    void initView(Context context, View view) {

    }

    @Override
    public void initDataBeforeInflate(Context context) {
        super.initDataBeforeInflate(context);
    }

    public AiRecommendedViewHolder(Context context){
        super(context);
        mContext = context;
    }
}
