package com.mediatek.hralauncher.model.interf;

import com.mediatek.hralauncher.common.AppInfo;

import java.util.List;

public interface ILauncherView {

    void initAppPages(String str , List<AppInfo> list);

    void onCreateCardPages(String mGradePeriod);

}
