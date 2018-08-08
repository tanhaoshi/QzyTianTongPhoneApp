package com.qzy.data;

import com.google.protobuf.GeneratedMessageV3;
import com.qzy.eventbus.MessageEvent;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneCmd extends MessageEvent{

    private int protoId = -1;  // 协议id

    private GeneratedMessageV3 message;

    public PhoneCmd(){

    }

    public PhoneCmd(int protoId){
        this.protoId = protoId;
    }

    public PhoneCmd(int protoId,GeneratedMessageV3 msg){
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

}
