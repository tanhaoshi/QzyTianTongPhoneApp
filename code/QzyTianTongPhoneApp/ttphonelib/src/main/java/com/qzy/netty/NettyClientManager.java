package com.qzy.netty;


import com.google.protobuf.Message;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.utils.ByteUtils;
import com.socks.library.KLog;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class NettyClientManager implements NettyClient.IConnectedReadDataListener {

    //private NettyClient mNettyClent;

    private INettyListener iNettyListener;

    private Thread mReconnectedThread;

    private boolean isConnected = false;
    private String ip;
    private int port;

    public NettyClientManager(INettyListener listener) {
        iNettyListener = listener;

    }

    public void startConnect(int port, String ip) {
        NettyClient.initNettyClient(this);
        this.ip = ip;
        this.port = port;
        NettyClient.getInstance().starConnect(port, ip);
    }

    @Override
    public void onReceiveData(ByteBufInputStream inputStream) {
        if (iNettyListener != null) {
            iNettyListener.onReceiveData(inputStream);
        }
    }

    @Override
    public void onConnectedState(boolean state) {
        isConnected = state;
        if (iNettyListener != null) {
            if (state) {
                iNettyListener.onConnected();
            } else {
                iNettyListener.onDisconnected();
            }
        }

        if (isConnected) {
//            stopReconnected();
        } else {
//            startReconnected(port,ip);
            if (NettyClient.getInstance() != null) {
                NettyClient.getInstance().stopConnected();
            }
        }

    }

    @Override
    public void onException(ChannelHandlerContext ctx) {

    }


    public void startReconnected(final int port, final String ip) {
        isConnected = false;
        if (mReconnectedThread == null) {
            mReconnectedThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (!isConnected) {
                        try {
                            Thread.sleep(2000);
                            startConnect(port, ip);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        mReconnectedThread.start();
    }

    private void stopReconnected() {
        try {
            isConnected = true;
            if (mReconnectedThread != null && mReconnectedThread.isAlive()) {
                mReconnectedThread.interrupt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mReconnectedThread = null;
        }
    }

    public void sendData(PhoneCmd cmd) {
        try {
            if (NettyClient.getInstance() != null && NettyClient.getInstance().getConnectHanlerCtx() != null) {
                ByteBuf buff = NettyClient.getInstance().getConnectHanlerCtx().alloc().buffer(36);
                ByteBufOutputStream stream = new ByteBufOutputStream(buff);
                stream.write(PrototocalTools.HEAD);  // 添加协议头
                stream.writeInt(cmd.getProtoId());
                Message msg = cmd.getMessage().toBuilder().build();
                ByteBuf dataBuff = NettyClient.getInstance().getConnectHanlerCtx().alloc().buffer();
                ByteBufOutputStream dataStream = new ByteBufOutputStream(dataBuff);
                msg.writeDelimitedTo(dataStream);
                dataStream.flush();
                int len = dataBuff.capacity();
                stream.writeInt(len);
                stream.write(dataBuff.array(), 0, len);
                stream.flush();
                NettyClient.getInstance().getConnectHanlerCtx().writeAndFlush(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 释放资源
     */
    public void release() {
        if (NettyClient.getInstance() != null) {
            NettyClient.getInstance().stopConnected();
        }

        if(mReconnectedThread.isAlive()){
            mReconnectedThread.interrupt();
        }
        mReconnectedThread = null;
    }

    /**
     * 是否连接
     *
     * @return
     */
    public boolean isConnected() {
        return isConnected;
    }

    /**
     * 接口回调
     */
    public interface INettyListener {
        void onReceiveData(ByteBufInputStream inputStream);

        void onConnected();

        void onDisconnected();

        void onException(ChannelHandlerContext ctx);
    }

}
