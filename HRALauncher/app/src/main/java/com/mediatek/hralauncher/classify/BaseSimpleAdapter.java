package com.mediatek.hralauncher.classify;

import com.mediatek.hralauncher.adapter.BaseMainAdapter;
import com.mediatek.hralauncher.adapter.BaseSubAdapter;

public interface BaseSimpleAdapter {
    BaseMainAdapter getMainAdapter();
    BaseSubAdapter getSubAdapter();
}
