package com.qzy.tt.phone.data;

public interface ITtPhoneDataListener {

    //服务连接状态
    void isTtServerConnected(boolean connected);

    //信号强度
    void isTtSignalStrength(int signalLevel);

    //simcard 的状态
    void isTtSimCard(boolean isIn);

    //电池电量
    void isTtPhoneBattery(int level,int scal);

}
