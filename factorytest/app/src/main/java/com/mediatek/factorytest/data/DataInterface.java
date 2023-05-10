package com.mediatek.factorytest.data;


import com.mediatek.factorytest.model.FactoryBean;

import java.util.List;

public interface DataInterface {

    public  void readDatasStatus(List<FactoryBean> mDatas );

    public  void storeDatas(List<FactoryBean>  mDatas);

}
