package com.qzy.tt.phone.data.impl;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.data.PhoneCmd;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtShortMessageProtos;

/**
 * 底层所有回调处理类
 */
public interface IAllTtPhoneDataListener {

    //服务连接状态
    void isTtServerConnected(boolean connected);

    //信号强度
    void isTtSignalStrength(int signalLevel);

    //simcard 的状态
    void isTtSimCard(boolean isIn);

    //电池电量
    void isTtPhoneBattery(int level,int scal);

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

}
