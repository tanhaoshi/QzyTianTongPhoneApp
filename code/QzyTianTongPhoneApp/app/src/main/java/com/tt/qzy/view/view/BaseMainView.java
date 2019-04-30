package com.tt.qzy.view.view;

import com.tt.qzy.view.view.base.BaseView;

public interface BaseMainView extends BaseView{

    //信号强度
    void isTtSignalStrength(int signalLevel,int signalDbm);

    //simcard 的状态
    void isTtSimCard(boolean isIn);

    //电池电量
    void isTtPhoneBattery(int level,int scal);
}
