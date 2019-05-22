package com.qzy.tt.phone.service;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.example.nativeaudio.NativeAudio;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PhoneStateUtils;
import com.qzy.phone.pcm.AllLocalPcmManager;
import com.qzy.tt.phone.common.CommonData;
import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.data.impl.ITtPhoneCallStateLisenter;
import com.qzy.tt.phone.netty.PhoneNettyManager;
import com.qzy.utils.LogUtils;
import com.qzy.utils.ToastUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.utils.ToastUtil;


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
        initProtocal();
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
                LogUtils.i("setTtPhoneCallState settings phone state");
                updatePhoneState(phoneCmd);
            }
        });
    }


    /**
     * 更新天通电话状态
     *
     * @param cmd
     */
    private boolean isRing = false;
    private boolean isComing = false;

    private void updatePhoneState(PhoneCmd cmd) {
        String callingIp = PhoneStateUtils.getTtPhoneStateNowCallingIp(cmd);
        LogUtils.i(" callingIp = " + callingIp + " phone state = " + PhoneStateUtils.getTtPhoneState(cmd).ordinal() + " localip = " + CommonData.getInstance().getLocalWifiIp());
        switch (PhoneStateUtils.getTtPhoneState(cmd)) {
            case NOCALL:
                stopProtocal();
                isRing = false;
                break;
            case RING:
                if (CommonData.getInstance().isCallingIp(callingIp)) {
                    isRing = true;
                    startPlayerProtocal();
                }
                break;

            case CALL:
                if (CommonData.getInstance().isCallingIp(callingIp)) {
                    if (!isRing || isComing) {
                        startPlayerProtocal();
                    }
                    startProtocal();
                }
                break;
            case HUANGUP:
                isRing = false;
                isComing = false;
                stopProtocal();
                break;
            case INCOMING:
                isRing = false;
                isComing = true;
                break;
            case UNRECOGNIZED:
                isRing = false;
                isComing = false;
                stopProtocal();
                break;
            default:
                isRing = false;
                isComing = false;
                break;
        }
    }



    /**
     * 开始录音
     */
    public void startProtocal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mAllLocalPcmManager != null) {
                    mAllLocalPcmManager.start();
                }
            }
        }).start();

    }

    /**
     * 开始播放
     */
    public void startPlayerProtocal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mAllLocalPcmManager != null) {
                    mAllLocalPcmManager.startPlayer();
                }
            }
        }).start();

    }

    /**
     * 结束录音
     */
    public void stopProtocal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mAllLocalPcmManager != null) {
                    mAllLocalPcmManager.stop();
                }
            }
        }).start();

    }


    /**
     * 释放协议资源
     */
    private void releaseProtocal() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mAllLocalPcmManager != null) {
                    mAllLocalPcmManager.free();  // modifed by yj.zhang 2019 03 19  把onstop改成了free
                }
            }
        }).start();

    }

    /**
     * 释放
     */
    public void relese() {
        LogUtils.e("relese ..... ");
        if (mPhoneNettyManager != null) {
            mPhoneNettyManager.free();
        }

        if (TtPhoneDataManager.getInstance() != null) {
            TtPhoneDataManager.getInstance().free();
        }


        releaseProtocal();
    }
}
