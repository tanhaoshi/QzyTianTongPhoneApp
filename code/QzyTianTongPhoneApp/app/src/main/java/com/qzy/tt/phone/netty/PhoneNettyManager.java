package com.qzy.tt.phone.netty;

import android.content.Context;

import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.data.CallPhoneProtos;
import com.qzy.tt.phone.cmd.CmdHandler;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.utils.IPUtil;
import com.socks.library.KLog;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class PhoneNettyManager {
    private Context mContext;

    private NettyClientManager mNettyClientManager;

    private CmdHandler mCmdHandler;

    public PhoneNettyManager(Context context) {
        mContext = context;
        CommonData.getInstance().setLocalWifiIp(IPUtil.getLocalIPAddress(context));
        EventBusUtils.register(this);
        mNettyClientManager = new NettyClientManager(nettyListener);
        mCmdHandler = new CmdHandler(context);
    }


    /**
     * 开始连接
     */
    public void connect() {
        mNettyClientManager.startConnect();
    }

    /**
     * 断开连接
     */
    public void stop() {
        mNettyClientManager.release();
    }

    /**
     * 拨打电话
     *
     * @param phoneNumber
     */
    private void dialPhone(String phoneNumber) {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhoneNumber(phoneNumber)
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.CALL)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 挂断电话
     */
    private void endCall() {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.HUANGUP)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }

    /**
     * 接听电话接口
     */
    private void acceptCall() {
        CallPhoneProtos.CallPhone callPhone = CallPhoneProtos.CallPhone.newBuilder()
                .setIp(CommonData.getInstance().getLocalWifiIp())
                .setPhonecommand(CallPhoneProtos.CallPhone.PhoneCommand.ACCEPTCALL)
                .build();
        sendPhoneCmd(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoServerIndex.call_phone, callPhone));
    }


    /**
     * 发送连接状态
     */
    private void setConnectedState() {
        boolean isconnected = mNettyClientManager.isConnected();
        //设置全局状态
        CommonData.getInstance().setConnected(isconnected);
        if (isconnected) {
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SUCCESS));
        } else {
            EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_FAILED));
        }
    }


    private NettyClientManager.INettyListener nettyListener = new NettyClientManager.INettyListener() {
        @Override
        public void onReceiveData(ByteBufInputStream inputStream) {
            KLog.i("netty onReceiveData ...");
            if (mCmdHandler != null) {
                mCmdHandler.handlerCmd(inputStream);
            }
        }

        @Override
        public void onConnected() {
            KLog.i("netty connected ...");
            setConnectedState();
        }

        @Override
        public void onDisconnected() {
            KLog.i("netty disconnected ...");
            if(mCmdHandler != null){
                mCmdHandler.resetPhoneState();
            }
            setConnectedState();
        }
    };


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventBus event) {
        KLog.i("event type = " + event.getType());
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG:
                connect();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_DISCONNECT_TIANTONG:
                stop();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SELECTED:
                setConnectedState();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_DIAL:
                dialPhone((String) event.getObject());
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_HUNGUP:
                endCall();
                break;
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_ACCEPTCALL:
                acceptCall();
                break;
        }
    }

    /**
     * 发送命令到天通猫
     *
     * @param cmd
     */
    private void sendPhoneCmd(PhoneCmd cmd) {
        if (mNettyClientManager != null) {
            mNettyClientManager.sendData(cmd);
        }
    }

    /**
     * 释放
     */
    public void free() {
        EventBusUtils.unregister(this);
        if (mNettyClientManager != null) {
            mNettyClientManager.release();
        }
        CommonData.getInstance().free();
    }


}
