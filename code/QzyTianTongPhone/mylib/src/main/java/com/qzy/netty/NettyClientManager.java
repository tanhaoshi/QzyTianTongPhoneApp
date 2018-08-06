package com.qzy.netty;


import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class NettyClientManager implements NettyClient.IConnectedReadDataListener{

    private NettyClient mNettyClent;

    private INettyListener iNettyListener;

    public NettyClientManager(INettyListener listener){
        iNettyListener = listener;
        mNettyClent = new NettyClient(this);
    }

    public void startConnect(){
        mNettyClent.starConnect();
    }


    @Override
    public void onReceiveData(byte[] data) {
       if(iNettyListener != null){
           iNettyListener.onReceiveData(data);
       }
    }

    @Override
    public void onConnectedState(boolean state) {
        if(iNettyListener != null){
            if(state) {
                iNettyListener.onConnected();
            }else{
                iNettyListener.onDisconnected();
            }
        }
    }

    public void sendData(byte[] data){
        if(mNettyClent != null){
            mNettyClent.sendData(data);
        }
    }


    /**
     * 释放资源
     */
    public void release(){
        if(mNettyClent != null){
            mNettyClent.stopConnected();
        }
    }

    /**
     * 接口回调
     */
    public interface INettyListener{
        void onReceiveData(byte[] data);
        void onConnected();
        void onDisconnected();
    }

}
