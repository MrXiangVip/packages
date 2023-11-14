package com.example.camera.host;

public interface IViewManager {
    void setVisibility(int visibility);
    int getVisibility();
//    void setEnabled(boolean enabled);
//    boolean isEnabled();

    void onCreate();

    void onResume();

}
