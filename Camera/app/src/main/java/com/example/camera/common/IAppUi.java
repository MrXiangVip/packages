package com.example.camera.common;

import java.util.List;

public interface IAppUi {




    class ModeItem {

    }

    void setModeChangeListener(IAppUiListener.OnModeChangeListener listener);
    void registerMode(List<ModeItem> items);
    void updateCurrentMode(String mode);

}
