package com.example.launcher.dragndrop;

import com.example.launcher.Launcher;

public class SpringLoadedDragController implements OnAlarmListener{

    Alarm mAlarm;
    private Launcher mLauncher;

    public SpringLoadedDragController(Launcher launcher) {
        mLauncher = launcher;
        mAlarm = new Alarm();
        mAlarm.setOnAlarmListener(this);
    }
}
