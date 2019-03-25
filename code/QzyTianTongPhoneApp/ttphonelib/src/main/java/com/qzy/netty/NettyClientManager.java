package com.qzy.netty;


import com.google.protobuf.Message;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.utils.ByteUtils;
import com.socks.library.KLog;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class NettyClientManager implements NettyClient.IConnectedReadDataListener{

    private NettyClient mNettyClent;

    private INettyListener iNettyListener;

    private Thread mReconnectedThread;

    private boolean isConnected = false;
    private String ip;
    private int port;

    public NettyClientManager(INettyListener listener){
        iNettyListener = listener;
        mNettyClent = new NettyClient(this);
    }

    public void startConnect(int port , String ip){
        this.ip = ip;
        this.port = port;
        mNettyClent.starConnect(port,ip);
    }

    @Override
    public void onReceiveData(ByteBufInputStream inputStream) {
       if(iNettyListener != null){
           iNettyListener.onReceiveData(inputStream);
       }
    }

    @Override
    public void onConnectedState(boolean state) {
        isConnected = state;
        if(isConnected){
//            stopReconnected();
        }else {
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_REQUEST_SERVER_NONCONNECT));
//            startReconnected(port,ip);
            mNettyClent.stopConnected();
        }
        if(iNettyListener != null){
            if(state) {
                iNettyListener.onConnected();
            }else{
                iNettyListener.onDisconnected();
            }
        }
    }


    private void startReconnected(final int port,final String ip){
        isConnected = false;
        if(mReconnectedThread == null) {
            mReconnectedThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!isConnected){
                        try{
                            startConnect(port,ip);
                            Thread.sleep(2000);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        mReconnectedThread.start();
    }

    private void stopReconnected(){
        try{
            isConnected = true;
            if(mReconnectedThread != null && mReconnectedThread.isAlive()) {
                mReconnectedThread.interrupt();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            mReconnectedThread = null;
        }
    }

    public void sendData(PhoneCmd cmd){
        try {
            if (mNettyClent != null && mNettyClent.getConnectHanlerCtx() != null) {
                ByteBuf buff = mNettyClent.getConnectHanlerCtx().alloc().buffer(36);
                ByteBufOutputStream stream = new ByteBufOutputStream(buff);
                stream.write(PrototocalTools.HEAD);  // 添加协议头
                stream.writeInt(cmd.getProtoId());
                Message msg = cmd.getMessage().toBuilder().build();
                ByteBuf dataBuff = mNettyClent.getConnectHanlerCtx().alloc().buffer();
                ByteBufOutputStream dataStream = new ByteBufOutputStream(dataBuff);
                msg.writeDelimitedTo(dataStream);
                dataStream.flush();
                int len = dataBuff.capacity();
                stream.writeInt(len);
                stream.write(dataBuff.array(), 0, len);
                //stream.writeBytes(cmdData);
                KLog.d("write buff:" + ByteUtils.byteArrToHexString(buff.array()));
                stream.flush();
                mNettyClent.getConnectHanlerCtx().writeAndFlush(buff);
            }
        }catch (Exception e){
            e.printStackTrace();
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
     * 是否连接
     * @return
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * 接口回调
     */
    public interface INettyListener{
        void onReceiveData(ByteBufInputStream inputStream);
        void onConnected();
        void onDisconnected();
    }

}
