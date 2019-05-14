package com.qzy.tt.phone.data.impl;

import com.qzy.data.PhoneCmd;

/**
 * 连接状态回调类
 */
public interface IMainFragment {

    //服务连接成功
    void isTtServerConnected(boolean connected);

    //服务是否需要更新
    void isUpdateServer(Object o);

    void updatePercent(Integer percent);

    //服务升级出现异常
    void serverUpdateError(Object o);

    //服务升级成功
    void updateServerSucceed(Object o);

    //服务连接失败
    void disConnectServer();

    //手动打开gps返回值
    void isTtPhoneGpsPosition(PhoneCmd phoneCmd);

    //获取SOS状态 初始化或手动打开
    void isServerSosStatus(Object o);
}
