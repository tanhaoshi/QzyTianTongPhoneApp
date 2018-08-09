package com.qzy.tiantong.service.service;

import com.qzy.tiantong.service.phone.QzyPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public interface ITianTongServer {

    //对外提供电话操控工具
    QzyPhoneManager getQzyPhoneManager();

    //更新打电话时 电话状态
    void onPhoneStateChange(TtPhoneState state);

    //更新天通模块信号强度
    void onPhoneSignalStrengthChange(int value);

    //打开底层pcm工具
    void initTtPcmDevice();

    //关闭底层pcm工具
    void freeTtPcmDevice();


}
