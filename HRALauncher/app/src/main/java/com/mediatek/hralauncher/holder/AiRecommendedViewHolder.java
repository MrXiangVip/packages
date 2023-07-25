package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.mediatek.hralauncher.R;

public class AiRecommendedViewHolder extends BaseHolder implements View.OnClickListener{

    Context mContext;
    private String TAG="AiRecommendedViewHolder.";

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

    @Override
    public void onScrollIn() {
        super.onScrollIn();
    }

    @Override
    public void onScrollOut() {
        super.onScrollOut();
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick ");
    }
}
