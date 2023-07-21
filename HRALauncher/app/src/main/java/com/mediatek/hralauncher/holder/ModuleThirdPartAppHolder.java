package com.mediatek.hralauncher.holder;

import android.content.Context;
import android.view.View;

import com.mediatek.hralauncher.R;
import com.mediatek.hralauncher.common.AppInfo;

import java.util.ArrayList;

public class ModuleThirdPartAppHolder extends ThirdPartAppHolder{
    public ModuleThirdPartAppHolder(Context mContext, ArrayList<AppInfo> appList) {
        super(mContext, appList);
    }

    int getLayoutId() {
        return R.layout.viewholder_module_thirdpart_app;
    }

    @Override
    void initView(Context context, View view) {
        super.initView(context, view);
    }
}
