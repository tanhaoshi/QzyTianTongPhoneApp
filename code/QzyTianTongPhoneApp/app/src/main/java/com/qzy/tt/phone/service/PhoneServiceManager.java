package com.qzy.tt.phone.service;

import android.content.Context;


import com.qzy.eventbus.MessageEventBus;
import com.qzy.netty.NettyClientManager;
import com.qzy.tt.phone.netty.PhoneNettyManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneServiceManager implements NettyClientManager.INettyListener {

    private Context mContext;


    private PhoneNettyManager mPhoneNettyManager;

    public PhoneServiceManager(Context context) {
        mContext = context;
        mPhoneNettyManager = new PhoneNettyManager(context);
    }


    /**
     * 初始化通讯协议
     */
    private void initProtocal() {

    }


    @Override
    public void onReceiveData(ByteBufInputStream inputStream) {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisconnected() {

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventBus event) {

    }

    /**
     * 开始录音
     */
    public void startProtocal() {


    }


    /**
     * 结束录音
     */
    public void stopProtocal() {


    }


    /**
     * 开启录音方法
     *
     * @param state
     */
    private void setRecorderState(boolean state) {

    }

    private void releaseProtocal() {

    }


    /**
     * 释放
     */
    public void relese() {
        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.free();
        }
        releaseProtocal();

    }


}
