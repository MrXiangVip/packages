package com.mediatek.hralauncher.model.interf;

import com.mediatek.hralauncher.common.AppInfo;

import java.util.List;

public interface ISystemPMHooker {
    List<AppInfo> onQueryAll(String str, List<AppInfo> list);
    Boolean shouldIgnoreApp( String str,  String str2);
}
