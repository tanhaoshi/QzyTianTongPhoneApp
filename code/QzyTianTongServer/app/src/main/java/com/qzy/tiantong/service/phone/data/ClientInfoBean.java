package com.qzy.tiantong.service.phone.data;

import io.netty.channel.ChannelHandlerContext;

public class ClientInfoBean {

    private String ip;
    private ChannelHandlerContext ctx;
    private boolean isCalling;

    public ClientInfoBean() {
    }

    public ClientInfoBean(String ip, ChannelHandlerContext ctx, boolean isCalling) {
        this.ip = ip;
        this.ctx = ctx;
        this.isCalling = isCalling;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public boolean isCalling() {
        return isCalling;
    }

    public void setCalling(boolean calling) {
        isCalling = calling;
    }
}
