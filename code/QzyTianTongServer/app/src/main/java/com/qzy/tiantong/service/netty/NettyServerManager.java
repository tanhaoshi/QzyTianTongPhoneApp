package com.qzy.tiantong.netty;

import com.google.protobuf.Message;
import com.qzy.data.PhoneAudioCmd;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class NettyServerManager implements NettyServer.IServerListener{

    private NettyServer mNettyServer;

    private INettyServerListener iNettyServerListener;

    public NettyServerManager(INettyServerListener listener){
        iNettyServerListener = listener;
        mNettyServer = new NettyServer(this);
    }


    @Override
    public void onConnected(String ip, boolean state) {
        if(iNettyServerListener == null){
            return;
        }
        if(state){
            iNettyServerListener.onConnected(ip);
        }else{
            iNettyServerListener.onDisconnected(ip);
        }

    }

    @Override
    public void onReceiveData(ByteBufInputStream inputStream) {
        if(iNettyServerListener == null){
            return;
        }
        iNettyServerListener.onReceiveData(inputStream);
    }

    /**
     * 开启netty服务
     */
    public void startNettyServer(){
        if(mNettyServer != null){
            mNettyServer.startServer();
        }
    }

    /**
     * 开启netty服务
     */
    public void stopNettyServer(){
        if(mNettyServer != null){
            mNettyServer.stopServer();
        }
    }

    /**
     * 发送数据
     */
    public void sendData(PhoneCmd cmd){
        try {
            if (mNettyServer != null && mNettyServer.getConnectHanlerCtx() != null) {
                ByteBuf buff = mNettyServer.getConnectHanlerCtx().alloc().buffer(36);
                ByteBufOutputStream stream = new ByteBufOutputStream(buff);
                stream.write(PrototocalTools.HEAD);  // 添加协议头
                stream.writeInt(cmd.getProtoId());
                Message msg = cmd.getMessage().toBuilder().build();
                ByteBuf dataBuff = mNettyServer.getConnectHanlerCtx().alloc().buffer();
                ByteBufOutputStream dataStream = new ByteBufOutputStream(dataBuff);
                msg.writeDelimitedTo(dataStream);
                dataStream.flush();
                int len = dataBuff.capacity();
                stream.writeInt(len);
                stream.write(dataBuff.array(), 0, len);
                //stream.writeBytes(cmdData);
                //LogUtils.d("write buff:" + ByteUtils.byteArrToHexString(buff.array()));
                stream.flush();
                mNettyServer.getConnectHanlerCtx().writeAndFlush(buff);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sendPhoneAudioData(PhoneAudioCmd cmd){
        try {
            if (mNettyServer != null && mNettyServer.getConnectHanlerCtx() != null) {
                ByteBuf buff = mNettyServer.getConnectHanlerCtx().alloc().buffer(36);
                ByteBufOutputStream stream = new ByteBufOutputStream(buff);
                stream.write(PrototocalTools.HEAD);  // 添加协议头
                stream.writeInt(cmd.getProtoId());
                Message msg = cmd.getMessage().toBuilder().build();
                ByteBuf dataBuff = mNettyServer.getConnectHanlerCtx().alloc().buffer();
                ByteBufOutputStream dataStream = new ByteBufOutputStream(dataBuff);
                msg.writeDelimitedTo(dataStream);
                dataStream.flush();
                int len = dataBuff.capacity();
                stream.writeInt(len);
                stream.write(dataBuff.array(), 0, len);
                //stream.writeBytes(cmdData);
                LogUtils.d("write buff:" + ByteUtils.byteArrToHexString(buff.array()));
                stream.flush();
                mNettyServer.getConnectHanlerCtx().writeAndFlush(buff);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 释放资源
     */
    public void release(){
        if(mNettyServer != null){
            mNettyServer.release();
        }
    }


    /**
     * 接口回调
     */
    public interface INettyServerListener{
        void onReceiveData(ByteBufInputStream inputStream);
        void onConnected(String ip);
        void onDisconnected(String ip);
    }



}
