package com.android.factorymode.util;

import android.util.Log;

public class FactoryBean {

    private static final String TAG =Utils.TAG +"FactoryBean" ;
    private String name;

    private int titleID;

    private String fragment;

    private int status = Utils.NONE;

    public FactoryBean(String name, int titleID, String fragment, int status) {
        this.name = name;
        this.titleID = titleID;
        this.fragment = fragment;
        this.status = status;
    }
    public int getTitleID( ){
        return  titleID;
    }
    public void setTitleID(int titleID ){
        this.titleID = titleID;
    }

    public String getFragment( ){
        return  fragment;
    }
    public void setFragment(String action) {
        this.fragment = fragment;
    }

    public int getStatus( ){
        return  status;
    }
    public void setStatus(int status ){
        Log.d(TAG, "setStatus: "+status);
        this.status = status;
    }
}
