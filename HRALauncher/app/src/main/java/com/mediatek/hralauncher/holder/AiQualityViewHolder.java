package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.mediatek.hralauncher.R;

public class AiQualityViewHolder extends BaseHolder implements  View.OnClickListener{

    private String TAG="AiQualityViewHolder.";

    public AiQualityViewHolder(Context mContext) {
        super(mContext);
    }

    @Override
    int getLayoutId() {
        return R.layout.view_holder_ai_quality;
    }

    @Override
    void initView(Context context, View view) {

    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick "+v);
    }
}
