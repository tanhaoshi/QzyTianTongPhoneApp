package com.qzy.tt.probuf.lib.data;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.tiantong.lib.eventbus.MessageEvent;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneAudioCmd extends MessageEvent {

    private int protoId = -1;  // 协议id

    private GeneratedMessageV3 message;

    private PhoneAudioCmd() {

    }

    private PhoneAudioCmd(int protoId) {
        this.protoId = protoId;
    }

    private PhoneAudioCmd(int protoId, GeneratedMessageV3 msg) {
        this.protoId = protoId;
        message = msg;
    }

    public int getProtoId() {
        return protoId;
    }

    public void setProtoId(int protoId) {
        this.protoId = protoId;
    }

    public GeneratedMessageV3 getMessage() {
        return message;
    }

    public void setMessage(GeneratedMessageV3 message) {
        this.message = message;
    }

    public static final PhoneAudioCmd getPhoneAudioCmd(int protoId) {
        return new PhoneAudioCmd(protoId);
    }

    public static final PhoneAudioCmd getPhoneAudioCmd(int protoId, GeneratedMessageV3 msg) {
        return new PhoneAudioCmd(protoId, msg);
    }

}
