package com.qzy.tt.phone.data.impl;

public interface ITtPhoneDataListener {

    //信号强度
    void isTtSignalStrength(int signalLevel);

    //simcard 的状态
    void isTtSimCard(boolean isIn);

    //电池电量
    void isTtPhoneBattery(int level,int scal);

}
