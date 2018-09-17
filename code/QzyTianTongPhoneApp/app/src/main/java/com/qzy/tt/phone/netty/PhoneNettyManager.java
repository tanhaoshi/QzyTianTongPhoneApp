package com.qzy.tt.phone.netty;

import android.content.Context;

import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.phone.cmd.CmdHandler;
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
     * 发送连接状态
     */
    private void setConnectedState() {
        boolean isconnected = mNettyClientManager.isConnected();
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
    }


}
