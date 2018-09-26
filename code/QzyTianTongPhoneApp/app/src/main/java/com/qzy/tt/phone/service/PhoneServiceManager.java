package com.qzy.tt.phone.service;

import android.content.Context;


import com.qzy.QzySensorManager;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PhoneStateUtils;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.netty.NettyClientManager;
import com.qzy.phone.pcm.AllLocalPcmManager;
import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.socks.library.KLog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import io.netty.buffer.ByteBufInputStream;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class PhoneServiceManager {

    private Context mContext;


    /**
     * 服务通讯
     */
    private PhoneNettyManager mPhoneNettyManager;

    private AllLocalPcmManager mAllLocalPcmManager;

    /**
     * sensor
     *
     * @param context
     */

    private QzySensorManager mQzySensorManager;


    public PhoneServiceManager(Context context) {
        EventBusUtils.register(this);
        mContext = context;
        mPhoneNettyManager = new PhoneNettyManager(context);
        mQzySensorManager = new QzySensorManager(context);
        initProtocal();
    }


    /**
     * 初始化通讯协议
     */
    private void initProtocal() {
        mAllLocalPcmManager = AllLocalPcmManager.getInstance();
    }


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE:
                updatePhoneState((PhoneCmd) event.getObject());
                break;
        }
    }

    /**
     * 更新天通电话状态
     *
     * @param cmd
     */
    private void updatePhoneState(PhoneCmd cmd) {
        KLog.i("phone state = " + PhoneStateUtils.getTtPhoneState(cmd).ordinal());
        switch (PhoneStateUtils.getTtPhoneState(cmd)) {
            case NOCALL:
                stopProtocal();
                break;
            case RING:
                break;
            case CALL:
                startProtocal();
                break;
            case HUANGUP:
                stopProtocal();
                break;
            case INCOMING:
                break;
            case UNRECOGNIZED:
                stopProtocal();
                break;
        }
    }


    /**
     * 开始录音
     */
    public void startProtocal() {
        if (mAllLocalPcmManager != null) {
            mAllLocalPcmManager.start();
        }

    }


    /**
     * 结束录音
     */
    public void stopProtocal() {

        if (mAllLocalPcmManager != null) {
            mAllLocalPcmManager.stop();
        }
    }


    /**
     * 释放协议资源
     */
    private void releaseProtocal() {
        if (mAllLocalPcmManager != null) {
            mAllLocalPcmManager.stop();
        }
    }


    /**
     * 释放
     */
    public void relese() {
        EventBusUtils.unregister(this);
        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.free();
        }
        releaseProtocal();

        if (mQzySensorManager != null) {
            mQzySensorManager.freeSenerState();
        }

    }


}
