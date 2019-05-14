package com.qzy.tt.phone.data.impl;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtPhonePositionProtos;
import com.qzy.tt.data.TtShortMessageProtos;

/**
 * 底层所有回调处理类
 */
public interface IAllTtPhoneDataListener {

    //服务连接状态
    void isTtServerConnected(boolean connected);

    //检测服务是否需要更新
    void IsServerUpdate(Object o);

    //更新时出现异常
    void updateError(Object o);

    //更新服务成功
    void updateServerSucceed(Object o);

    //信号强度
    void isTtSignalStrength(int signalLevel,int dbm);

    //simcard 的状态
    void isTtSimCard(boolean isIn);

    //电池电量
    void isTtPhoneBattery(int level,int scal);

    //返回gps经纬度
    void isTtPhoneGpsPositon(PhoneCmd phoneCmd);

    //返回服务版本号
    void isTtPhoneServerVersion(PhoneCmd phoneCmd);

    //同步通话记录
    void syncCallRecord(TtCallRecordProtos.TtCallRecordProto ttCallRecordProto);

    //同步短信
    void syncShortMessage( TtShortMessageProtos.TtShortMessage ttShortMessage);

    //同步短信信号
    void syncShortMessageSignal(int protoId, GeneratedMessageV3 messageV3, TtShortMessageProtos.TtShortMessage.ShortMessage ttShortMessage);


    //电话状态回调
    void onTtPhoneCallState(PhoneCmd phoneCmd);


    //设备通话占用回调
    void onPhoneCallStateBack(PhoneCmd phoneCmd);

    //返回初始化 sos状态
    void onServerTtPhoneSosState(PhoneCmd phoneCmd);

    //短信发送状态回调
    void onServerTtPhoneSmsSendState(PhoneCmd phoneCmd);

    void onUpdatePercent(Integer percent);

}
