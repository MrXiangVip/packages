package com.mediatek.hralauncher.model.interf;

import com.mediatek.hralauncher.common.AppInfo;

import java.util.List;

public interface IAppPosMgr {
    List<List<AppInfo>> buildPages(String str, List<AppInfo> list);

}
