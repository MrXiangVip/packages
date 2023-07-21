package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.view.View;

import com.mediatek.hralauncher.R;

public class AiSubjectViewHolder extends  BaseHolder{

    public static int SUBJECT_MATH = 0;
    public static int SUBJECT_CHINESE = 0;
    public static int SUBJECT_ENGLISH = 0;
    public static int SUBJECT_PHYSICAL = 0;
    int subject;
    public AiSubjectViewHolder(Context mContext, int mSubject) {
        super(mContext);
        subject = mSubject;
    }

    @Override
    int getLayoutId() {
        return R.layout.viewholder_ai_subject;
    }

    @Override
    void initView(Context context, View view) {

    }
}
