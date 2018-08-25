package com.qzy.netty;


import com.google.protobuf.Message;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.utils.ByteUtils;
import com.qzy.utils.LogUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
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
    public void onReceiveData(ByteBufInputStream inputStream) {
       if(iNettyListener != null){
           iNettyListener.onReceiveData(inputStream);
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
                LogUtils.d("write buff:" + ByteUtils.byteArrToHexString(buff.array()));
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
     * 接口回调
     */
    public interface INettyListener{
        void onReceiveData(ByteBufInputStream inputStream);
        void onConnected();
        void onDisconnected();
    }

}
