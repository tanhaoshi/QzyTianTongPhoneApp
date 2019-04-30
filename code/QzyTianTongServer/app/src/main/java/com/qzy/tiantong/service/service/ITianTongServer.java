package com.qzy.tiantong.service.service;


import com.qzy.tiantong.lib.localsocket.LocalPcmSocketManager;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.netty.PhoneNettyManager;
import com.qzy.tiantong.service.phone.QzyPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tt.data.TtPhoneSmsProtos;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public interface ITianTongServer {

    //对外提供电话操控工具
    QzyPhoneManager getQzyPhoneManager();

    PhoneNettyManager getPhoneNettyManager();

    SystemSleepManager getSystemSleepManager();

    LocalPcmSocketManager getLocalSocketManager();

    boolean setCurrenCallingIp(String ip);
    void setEndCallingIp(String ip);

    //更新打电话时 电话状态
    void onPhoneStateChange(TtPhoneState state);

    //电话进入
    void onPhoneIncoming(TtPhoneState state,String phoneNumber);

    //更新天通模块信号强度
    void onPhoneSignalStrengthChange(int value,int dbm);

    //打开底层pcm工具
    void initTtPcmDevice();

    //关闭底层pcm工具
    void freeTtPcmDevice();

    //发送短信
    void sendSms(TtPhoneSmsProtos.TtPhoneSms ttPhoneSms);


}
