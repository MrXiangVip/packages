package com.android.factorymode.util;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Log;

import com.android.factorymode.R;

import java.util.ArrayList;
import java.util.List;

public class ConfigDatas {
    public  Context  mContext;
    public  static List<FactoryBean> mDatas=null;
    private static ConfigDatas mConfigDatas;
    private static final String TAG = Utils.TAG+"FactoryData";

    private ConfigDatas(Context context ){
        mContext = context;
        if( mDatas == null ){
            mDatas = parseFactory( context );
//            readDatasStatus( mDatas );
        }
    }

    public static ConfigDatas getInstance(Context context ){

        Log.d(TAG, "getInstance: "+mDatas);
        if (mConfigDatas == null) {
            mConfigDatas = new ConfigDatas(context);
        }
        return mConfigDatas;
    }
    public static List<FactoryBean> parseFactory(Context context){
        Log.d(TAG, "parseFactory: ");
        List<FactoryBean> datas = new ArrayList<>() ;
        try {
            XmlResourceParser parser = context.getResources().getXml(R.xml.factory_config);
            int event = parser.getEventType();
            while (event != XmlResourceParser.END_DOCUMENT) {
                Log.d(TAG, "Parser: " + event);
                switch (event) {
                    case XmlResourceParser.START_DOCUMENT:
                        Log.d(TAG, "start: ");
                        break;
                    case XmlResourceParser.START_TAG:
                        String name = parser.getName();
                        Log.d(TAG, "start tag: " + name);
                        if( name.equals( Utils.COMPONENT ) ){
                            int count = parser.getAttributeCount();
                            String attrName="";
                            String factoryName="";
                            int factoryTitle=0;
                            String fragment="";
                            boolean visible = true;
                            for(int i=0; i<count; i++){
                                attrName = parser.getAttributeName(i);
                                Log.d(TAG, i+" Parser: "+ attrName );
                                if( attrName.equals( Utils.NAME ) ){
                                    factoryName = parser.getAttributeValue(i);
                                    Log.d(TAG, "name : "+ factoryName);
                                } else if( attrName.equals( Utils.TITLE ) ){
                                    factoryTitle = parser.getAttributeResourceValue(i,0);
                                    Log.d(TAG, i+"title: "+ factoryTitle );
                                }else if( attrName.equals( Utils.FRAGMENT )){
                                    fragment = parser.getAttributeValue(i);
                                    Log.d(TAG, i+" fragment: "+ fragment );
                                }else if( attrName.equals( Utils.VISIBLE ) ){
                                    visible = parser.getAttributeBooleanValue(i, true);
                                    Log.d(TAG, i+" visible: "+ visible );

                                }

                            }
                            if( visible ) {
                                FactoryBean bean = new FactoryBean(factoryName, factoryTitle, fragment, Utils.NONE);
                                datas.add(bean);
                            }
                        }
                        break;
                    case XmlResourceParser.END_TAG:
                        Log.d(TAG, "end tag: ");
                        break;
                    default:
                        Log.d(TAG, "default: ");
                        break;
                }
                event = parser.next();
            }
        } catch (Exception e) {
            Log.d(TAG, "getAttr: "+e.toString());
            e.printStackTrace();
        }
        return datas;
    }


    public List<FactoryBean> getListFactoryBean( ){
        return mDatas;
    }
}
