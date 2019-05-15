package com.qzy.tiantong.service.netty;

import android.text.TextUtils;

import com.google.protobuf.Message;
import com.qzy.tiantong.lib.service.netty.NettyServer;
import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.phone.PhoneClientManager;
import com.qzy.tiantong.service.phone.data.ClientInfoBean;
import com.qzy.tt.probuf.lib.data.PhoneAudioCmd;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class NettyServerManager implements NettyServer.IServerListener {

    private NettyServer mNettyServer;

    private INettyServerListener iNettyServerListener;


    public NettyServerManager(INettyServerListener listener) {
        iNettyServerListener = listener;
        mNettyServer = new NettyServer(this);
    }


    @Override
    public void onConnected(ChannelHandlerContext ctx, String ip, boolean state) {
        if (iNettyServerListener == null) {
            return;
        }
        if (state) {
            //新加入客户端
            PhoneClientManager.getInstance().addPhoneClient(ip, ctx);
            iNettyServerListener.onConnected(ip);
        } else {
            //客户端退出
            PhoneClientManager.getInstance().removePhoneClient(ip);
            iNettyServerListener.onDisconnected(ip);
        }

    }

    @Override
    public void onReceiveData(ByteBufInputStream inputStream) {
        if (iNettyServerListener == null) {
            return;
        }
        iNettyServerListener.onReceiveData(inputStream);
    }

    /**
     * 开启netty服务
     */
    public void startNettyServer(int port) {
        if (mNettyServer != null) {
            mNettyServer.startServer(port);
        }
    }

    /**
     * 停止netty服务
     */
    public void stopNettyServer() {
        if (mNettyServer != null) {
            mNettyServer.stopServer();
        }
    }

    /**
     * 发送数据
     *
     * @param ip  null 代表所有客户端
     * @param cmd
     */
    public void sendData(String ip, PhoneCmd cmd) {
        try {
            if (TextUtils.isEmpty(ip)) { //发送所有终端
                ConcurrentHashMap<String, ClientInfoBean> hashMap = PhoneClientManager.getInstance().getmHaspMapPhoneClient();
                for (Map.Entry<String, ClientInfoBean> entry : hashMap.entrySet()) {
                    if (entry.getValue() != null && entry.getValue().getCtx() != null) {
                        sendData(entry.getValue().getCtx(), cmd);
                    }
                }
                return;
            }

            ChannelHandlerContext ctx = PhoneClientManager.getInstance().getChannelHandlerContext(ip);

            if (ctx != null) {
                sendData(ctx, cmd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送数据
     *
     * @param ctx
     * @param cmd
     */
    private void sendData(ChannelHandlerContext ctx, PhoneCmd cmd) {
        try {
            if (mNettyServer != null && ctx != null) {
                ByteBuf buff = ctx.alloc().buffer(36);
                ByteBufOutputStream stream = new ByteBufOutputStream(buff);
                stream.write(PrototocalTools.HEAD);  // 添加协议头
                stream.writeInt(cmd.getProtoId());
                Message msg = cmd.getMessage().toBuilder().build();
                ByteBuf dataBuff = ctx.alloc().buffer();
                ByteBufOutputStream dataStream = new ByteBufOutputStream(dataBuff);
                msg.writeDelimitedTo(dataStream);
                dataStream.flush();
                int len = dataBuff.capacity();
                stream.writeInt(len);
                stream.write(dataBuff.array(), 0, len);
                //stream.writeBytes(cmdData);
                LogUtils.d("ProtoId :" + cmd.getProtoId());
                stream.flush();
                ctx.writeAndFlush(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送音频数据
     *
     * @param cmd
     */
    public void sendPhoneAudioData(String ip, PhoneAudioCmd cmd) {
        try {
            ChannelHandlerContext ctx = PhoneClientManager.getInstance().getChannelHandlerContext(ip);
            if (ctx != null) {
                sendPhoneAudioData(ctx, cmd);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送音频数据
     *
     * @param ctx
     * @param cmd
     */
    private void sendPhoneAudioData(ChannelHandlerContext ctx, PhoneAudioCmd cmd) {
        try {
            if (mNettyServer != null && ctx != null) {
                ByteBuf buff = ctx.alloc().buffer(36);
                ByteBufOutputStream stream = new ByteBufOutputStream(buff);
                stream.write(PrototocalTools.HEAD);  // 添加协议头
                stream.writeInt(cmd.getProtoId());
                Message msg = cmd.getMessage().toBuilder().build();
                ByteBuf dataBuff = ctx.alloc().buffer();
                ByteBufOutputStream dataStream = new ByteBufOutputStream(dataBuff);
                msg.writeDelimitedTo(dataStream);
                dataStream.flush();
                int len = dataBuff.capacity();
                stream.writeInt(len);
                stream.write(dataBuff.array(), 0, len);
                //stream.writeBytes(cmdData);
                LogUtils.d("write buff:" + ByteUtils.byteArrToHexString(buff.array()));
                stream.flush();
                ctx.writeAndFlush(buff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 释放资源
     */
    public void release() {
        if (mNettyServer != null) {
            mNettyServer.release();
            PhoneClientManager.getInstance().free();
        }
    }

    /**
     * 接口回调
     */
    public interface INettyServerListener {
        void onReceiveData(ByteBufInputStream inputStream);

        void onConnected(String ip);

        void onDisconnected(String ip);
    }


}
