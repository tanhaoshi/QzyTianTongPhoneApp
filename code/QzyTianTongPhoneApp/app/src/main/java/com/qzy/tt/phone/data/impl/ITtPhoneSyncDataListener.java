package com.qzy.tt.phone.data.impl;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtShortMessageProtos;

public interface ITtPhoneSyncDataListener {
    //同步通话记录
    void syncCallRecord(TtCallRecordProtos.TtCallRecordProto ttCallRecordProto);

    //同步短信
    void syncShortMessage( TtShortMessageProtos.TtShortMessage ttShortMessage);

    //同步短信信号
    void syncShortMessageSignal(int protoId,GeneratedMessageV3 messageV3, TtShortMessageProtos.TtShortMessage.ShortMessage ttShortMessage);
}
