package com.qzy.tt.probuf.lib.data;

import com.google.protobuf.GeneratedMessageV3;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneCmd {

    private int protoId = -1;  // 协议id

    private GeneratedMessageV3 message;

    private PhoneCmd() {

    }

    private PhoneCmd(int protoId) {
        this.protoId = protoId;
    }

    private PhoneCmd(int protoId, GeneratedMessageV3 msg) {
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

    public static final PhoneCmd getPhoneCmd(int protoId) {
        return new PhoneCmd(protoId);
    }

    public static final PhoneCmd getPhoneCmd(int protoId, GeneratedMessageV3 msg) {
        return new PhoneCmd(protoId, msg);
    }

}
