package com.example.myapplication.sidebar.utils;

public enum GamepadConfigType {

    NATIVE(0),
    TOUCHSCREEN(1);

    private int id;
    GamepadConfigType (int id) {
        this.id = id;
    }
    int getId() {
        return id;
    }

    public boolean isNative(){
        return this.getId()==NATIVE.getId();
    }
}
