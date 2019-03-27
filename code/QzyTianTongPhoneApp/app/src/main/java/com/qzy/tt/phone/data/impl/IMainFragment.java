package com.qzy.tt.phone.data.impl;

/**
 * 连接状态回调类
 */
public interface IMainFragment {

    //服务连接成功
    void isTtServerConnected(boolean connected);

    //服务连接失败
    void disConnectServer();
}
