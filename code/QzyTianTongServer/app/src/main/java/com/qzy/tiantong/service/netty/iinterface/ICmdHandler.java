package com.qzy.tiantong.service.netty.iinterface;

import io.netty.buffer.ByteBufInputStream;

public interface ICmdHandler {
    /**
     * 处理协议
     * @param inputStream
     */
    void handlerCmd(ByteBufInputStream inputStream);

    /**
     * 份协议id处理协议
     * @param protoId
     * @param inputStream
     */
    void handProcessCmd(int protoId,ByteBufInputStream inputStream);


    /**
     * 发送协议
     * @param what
     * @param obj
     */
    void senMsg(int what,Object obj);


}
