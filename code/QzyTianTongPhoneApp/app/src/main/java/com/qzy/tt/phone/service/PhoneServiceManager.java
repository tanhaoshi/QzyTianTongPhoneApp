package com.qzy.tt.phone.service;

import android.content.Context;


import com.qzy.data.PhoneCmd;
import com.qzy.data.PhoneStateUtils;
import com.qzy.phone.pcm.AllLocalPcmManager;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateLisenter;
import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.socks.library.KLog;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;


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

//    private QzySensorManager mQzySensorManager;
    public PhoneServiceManager(Context context) {
        mContext = context;
        mPhoneNettyManager = new PhoneNettyManager(context);
        TtPhoneDataManager.getInstance().init(context, mPhoneNettyManager);
        setTtPhoneCallState();
//        mQzySensorManager = new QzySensorManager(context);
        if (SPUtils.containsShare(context, Constans.AUTO_EXITS)) {
            KLog.i("phone service manager start record ");
            initProtocal();
        }
    }

    /**
     * 初始化通讯协议
     */
    private void initProtocal() {
        mAllLocalPcmManager = AllLocalPcmManager.getInstance(mContext);
    }

    /*@Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEventBus event) {
        switch (event.getType()) {
            case IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_STATE:
                updatePhoneState((PhoneCmd) event.getObject());
                break;
        }
    }*/

    /**
     * 设置电话通话状态
     */
    private void setTtPhoneCallState() {
        TtPhoneDataManager.getInstance().setTtPhoneCallStateLisenter("PhoneServiceManager", new ITtPhoneCallStateLisenter() {
            @Override
            public void onTtPhoneCallState(PhoneCmd phoneCmd) {
                updatePhoneState(phoneCmd);
            }
        });

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
                startPlayerProtocal();
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
     * 开始播放
     */
    public void startPlayerProtocal() {
        if (mAllLocalPcmManager != null) {
            mAllLocalPcmManager.startPlayer();
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
            mAllLocalPcmManager.free();  // modifed by yj.zhang 2019 03 19  把onstop改成了free
        }
    }

    /**
     * 释放
     */
    public void relese() {
        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.free();
        }

        if (TtPhoneDataManager.getInstance() != null) {
            TtPhoneDataManager.getInstance().free();
        }

        releaseProtocal();


//        if (mQzySensorManager != null) {
//            mQzySensorManager.freeSenerState();
//        }
    }
}
