package com.qzy.tt.phone.data;

import com.qzy.tt.phone.netty.PhoneNettyManager;

/**
 * 数据管理类
 */
public class TtPhoneDataManger {

    private static TtPhoneDataManger instance;

    private PhoneNettyManager phoneNettyManager;

    public static TtPhoneDataManger getInstance(){
        return instance;
    }

    /**
     * 初始化
     */
    public static void init(PhoneNettyManager manager){
       instance = new TtPhoneDataManger(manager);
    }

    private TtPhoneDataManger(PhoneNettyManager manager){
        phoneNettyManager = manager;
    }


    /**
     * 注册数据回调
     * @param iTtPhoneDataListener
     */
    public void setTtPhoneDataListener(ITtPhoneDataListener iTtPhoneDataListener){
        phoneNettyManager.getmCmdHandler().setmDataListener(iTtPhoneDataListener);
    }



    public void free(){
        instance = null;
    }


}
