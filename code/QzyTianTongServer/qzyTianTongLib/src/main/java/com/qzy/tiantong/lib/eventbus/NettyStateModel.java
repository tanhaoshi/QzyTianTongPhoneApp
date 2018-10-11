package com.qzy.tiantong.lib.eventbus;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class NettyStateModel extends MessageEvent {

    private boolean isConnected;

    public NettyStateModel(){

    }

    public NettyStateModel(boolean isConnected){
        this.isConnected = isConnected;
    }


    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }


}
