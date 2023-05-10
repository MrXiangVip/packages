package com.mediatek.factorytest.data;

import android.content.Context;
import android.util.Log;


import com.mediatek.factorytest.model.FactoryBean;
import com.mediatek.factorytest.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class NvramManger implements DataInterface {
    private static final String TAG = Utils.TAG+"NvramManger";
    private static NvramManger nvramManger;

    private static final String CUSTOM_ADDRESS_FILENAME = "/vendor/nvdata/APCFG/APRDEB/PRODUCT_INFO";
    private static int FACTORYTEST_VALUE = 342;

    private NvramManger(Context context){

    }


    public synchronized static NvramManger getInstance(Context context) {
        if (nvramManger == null) {
            nvramManger = new NvramManger(context);
        }
        return nvramManger;
    }
    @Override
    public void readDatasStatus(List<FactoryBean> mDatas) {
        readDatasStatusFromNvram(mDatas);
    }

    @Override
    public void storeDatas(List<FactoryBean> mDatas) {
        storeDatasToNvram(mDatas);
    }

    public  void readDatasStatusFromNvram(List<FactoryBean> mDatas ){
        try {


        } catch (Exception e) {
            Log.d(TAG,"readFileByNamevec Exception == "+e);
            e.printStackTrace();
        }
    }

    public  void storeDatasToNvram(List<FactoryBean>  mDatas){
        try {

        } catch (Exception e) {
            Log.d(TAG,"writeFileByNamevec Exception == "+e);
            e.printStackTrace();
        }
    }


}
