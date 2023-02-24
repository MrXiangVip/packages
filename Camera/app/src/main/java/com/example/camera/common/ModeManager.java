package com.example.camera.common;

public class ModeManager implements IModeListener{
    private IApp mApp;
    private IAppUi mAppUi;

    @Override
    public void create(IApp app) {
        mApp = app;

    }

    @Override
    public void resume() {

    }
}
