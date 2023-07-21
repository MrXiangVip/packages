package com.mediatek.hralauncher.model.interf;

import com.mediatek.hralauncher.common.AppInfo;
import com.mediatek.hralauncher.holder.BaseHolder;
import com.mediatek.hralauncher.holder.ThirdPartAppHolder;

import java.util.List;

public interface ILauncherPageFactory {

    List<BaseHolder> createHolders(String str , List<AppInfo> list);

}
