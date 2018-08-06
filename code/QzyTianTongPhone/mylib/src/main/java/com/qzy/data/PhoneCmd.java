package com.qzy.data;

import com.google.protobuf.GeneratedMessageV3;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneCmd {

    private int protoId = -1;  // 协议id

    private GeneratedMessageV3 message;

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

}
