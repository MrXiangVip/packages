package com.mediatek.hralauncher.model.interf;

import com.mediatek.hralauncher.holder.BaseHolder;

import java.util.List;

public interface ILauncherPagersView {
    void initAppPages(String gradePeriod,  List<BaseHolder> list);

    void onCreateCardPages(String gradePeriod);
}
