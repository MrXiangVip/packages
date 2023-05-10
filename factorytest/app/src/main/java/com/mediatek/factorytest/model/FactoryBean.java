package com.mediatek.factorytest.model;

import android.util.Log;

import com.mediatek.factorytest.utils.Utils;

public class FactoryBean {
    private static final String TAG = Utils.TAG +"FactoryBean" ;
    private String key;

//    private String title;
    private int titleID;

    private String action;

    private int status = Utils.NONE;

    public FactoryBean(String key, int titleID, String action, int status) {
        this.key = key;
        this.titleID = titleID;
        this.action = action;
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getTitleID( ){
        return  titleID;
    }
    public void setTitleID(int titleID ){
        this.titleID = titleID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getStatus( ){
        return  status;
    }
    public void setStatus(int status ){
        Log.d(TAG, "setStatus: "+status);
        this.status = status;
    }

    public String toString( ){
        String s = "key :"+key+"  titleID:"+titleID+"  action:"+action+"  status:"+status+"\n";
        return s;
    }
}
