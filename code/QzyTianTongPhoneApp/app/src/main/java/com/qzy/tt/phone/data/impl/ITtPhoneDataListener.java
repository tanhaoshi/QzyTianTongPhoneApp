package com.qzy.tt.phone.data.impl;

/**
 * 信号强度 title状态更新回调
 */
public interface ITtPhoneDataListener {

    //信号强度
    void isTtSignalStrength(int signalLevel,int signalDbm);

    //simcard 的状态
    void isTtSimCard(boolean isIn);

    //电池电量
    void isTtPhoneBattery(int level,int scal);

}
