package com.qzy.tt.phone.bean;

import io.netty.buffer.ByteBufInputStream;

public class PhoneDataBean {

    private int protoId;

    private ByteBufInputStream inputStream;

    public PhoneDataBean(int protoId,ByteBufInputStream inputStream){
      this.protoId = protoId;
      this.inputStream = inputStream;
    }


    public int getProtoId() {
        return protoId;
    }

    public void setProtoId(int protoId) {
        this.protoId = protoId;
    }

    public ByteBufInputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(ByteBufInputStream inputStream) {
        this.inputStream = inputStream;
    }

}
