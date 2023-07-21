package com.mediatek.hralauncher.model.interf;

import com.mediatek.hralauncher.common.AppInfo;

import java.util.List;

public interface ISystemPackageManager {

    List<AppInfo> queryAll(String str);

}
