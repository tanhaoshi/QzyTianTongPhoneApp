package com.qzy.tiantong.service.netty;

import android.content.Context;

import com.qzy.data.PhoneAudioCmd;
import com.qzy.data.PhoneCmd;
import com.qzy.data.PrototocalTools;
import com.qzy.tiantong.lib.eventbus.MessageEvent;
import com.qzy.led.Netled;
import com.qzy.tiantong.netty.NettyServerManager;
import com.qzy.tiantong.service.phone.BatteryManager;
import com.qzy.tiantong.service.phone.SmsPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tiantong.lib.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class PhoneNettyManager {

    //服务端netty管理工具
    private NettyServerManager mNettyServerManager;


    //客户端手机管理
    private PhoneClientManager mPhoneClientManager;

    private Thread mStateThread;
    private CallPhoneStateProtos.CallPhoneState.PhoneState currentPhoneState;
    private String currentPhoneNumber = "";
    private int currentSignalValue = 99;

    private Context mContext;

    private SmsPhoneManager mSmsPhoneManager;

    /**
     * 电量获取
     */
    private BatteryManager mBatteryManager;
    private TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery;

    public PhoneNettyManager(Context context, NettyServerManager manager) {
        mContext = context;
        mNettyServerManager = manager;

        mSmsPhoneManager = new SmsPhoneManager(context, iOnSMSCallback);


        EventBus.getDefault().register(this);

        initSignal();

        initSendThread();

        initBattery();
    }

    /**
     * 初始化电量管理
     */
    private void initBattery(){
        mBatteryManager = new BatteryManager(mContext, new BatteryManager.onBatteryListenr() {
            @Override
            public void onBattery(int level, int scal) {
                if(ttPhoneBattery == null) {
                    ttPhoneBattery = TtPhoneBatteryProtos.TtPhoneBattery.newBuilder()
                            .setLevel(level)
                            .setScale(scal)
                            .build();
                }
            }
        });
    }



    /**
     * 初始化发送线程
     */
    private void initSendThread(){
        mStateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(3000);
                        // LogUtils.e("send all sate.....");
                        if (currentPhoneState != null) {
                            sendTtCallPhoneStateToClient(currentPhoneState, currentPhoneNumber);
                        }
                        sendSignalToPhoneClient(currentSignalValue);
                        //发送电量
                        sendTtPhoneBatteryToClient();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mStateThread.start();
    }


    /**
     * 控制信号灯初始化
     */
    private void initSignal() {
        Netled.init();
        Netled.setNetledState(true);
        Netled.setNetledFlash(true);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageEvent event) {
        if (event instanceof PhoneAudioCmd) {
            PhoneAudioCmd phoneAudioCmd = (PhoneAudioCmd) event;
            if (phoneAudioCmd.getProtoId() != PrototocalTools.IProtoClientIndex.tt_phone_audio) {
                return;
            }
            sendPhoneAudioData(phoneAudioCmd);
        }
    }

    /**
     * 更新电话状态
     *
     * @param state
     */
    public void updateTtCallPhoneState(TtPhoneState state, String phoneNumber) {
        LogUtils.e("updateTtCallPhoneState " + state.ordinal());
        CallPhoneStateProtos.CallPhoneState.PhoneState phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
        if (state == TtPhoneState.NOCALL) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
        } else if (state == TtPhoneState.RING) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.RING;
        } else if (state == TtPhoneState.CALL) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.CALL;
        } else if (state == TtPhoneState.HUANGUP) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP;
        } else if (state == TtPhoneState.HUANGUP) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP;
        } else if (state == TtPhoneState.INCOMING) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.INCOMING;
        }
        currentPhoneState = phoneState;
        currentPhoneNumber = phoneNumber;
        sendTtCallPhoneStateToClient(phoneState, phoneNumber);
    }


    /**
     * 发送电话状态到客户端
     *
     * @param phoneState
     */
    private void sendTtCallPhoneStateToClient(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState, String phoneNumber) {
        if (mNettyServerManager != null) {
            CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.newBuilder()
                    .setPhoneState(phoneState)
                    .setPhoneNumber(phoneNumber)
                    .build();
            mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
        }
    }

    /**
     * 发送信号强度
     *
     * @param value
     */
    public void sendTtCallPhoneSignalToClient(int value) {
        currentSignalValue = value;
        controlSignal(value);
        sendSignalToPhoneClient(value);
    }

    /**
     * 发送电池电量
     *
     */
    public void sendTtPhoneBatteryToClient() {
        if (mNettyServerManager != null && ttPhoneBattery != null) {
            mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_phone_battery, ttPhoneBattery));
        }
    }


    /**
     * 内部发送signal接口
     *
     * @param value
     */
    private void sendSignalToPhoneClient(int value) {
        if (mNettyServerManager != null) {
            TtPhoneSignalProtos.PhoneSignalStrength signalStrength = TtPhoneSignalProtos.PhoneSignalStrength.newBuilder()
                    .setSignalStrength(value)
                    .build();
            mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_phone_signal, signalStrength));
        }
    }


    /**
     * 发送短信
     *
     * @param ttPhoneSms
     */
    public void senSms(TtPhoneSmsProtos.TtPhoneSms ttPhoneSms) {
        mSmsPhoneManager.sendSms(ttPhoneSms.getIp(), ttPhoneSms.getPhoneNumber(), ttPhoneSms.getMessageText());
    }


    /**
     * 短信回调
     */
    private SmsPhoneManager.IOnSMSCallback iOnSMSCallback = new SmsPhoneManager.IOnSMSCallback() {
        @Override
        public void onSendSuccess(String ip, String phoneNumber) {
            if (mNettyServerManager != null) {
                TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                        .setIp(ip)
                        .setPhoneNumber(phoneNumber)
                        .setIsSend(false)
                        .setIsSendSuccess(true)
                        .setIsReceiverSuccess(false)
                        .build();
                mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
            }
        }

        @Override
        public void onSendFailed(String ip, String phoneNumber) {
            if (mNettyServerManager != null) {
                TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                        .setIp(ip)
                        .setPhoneNumber(phoneNumber)
                        .setIsSend(false)
                        .setIsSendSuccess(false)
                        .setIsReceiverSuccess(false)
                        .build();
                mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
            }
        }

        @Override
        public void onReceiveSuccess(String ip, String phoneNumber) {
            if (mNettyServerManager != null) {
                TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                        .setIp(ip)
                        .setPhoneNumber(phoneNumber)
                        .setIsSend(false)
                        .setIsSendSuccess(false)
                        .setIsReceiverSuccess(true)
                        .build();
                mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
            }
        }

        @Override
        public void onReceiveFailed(String ip, String phoneNumber) {
            if (mNettyServerManager != null) {
                TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                        .setIp(ip)
                        .setPhoneNumber(phoneNumber)
                        .setIsSend(false)
                        .setIsSendSuccess(false)
                        .setIsReceiverSuccess(false)
                        .build();
                mNettyServerManager.sendData(PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
            }
        }
    };

    public void sendPhoneAudioData(PhoneAudioCmd cmd) {
        mNettyServerManager.sendPhoneAudioData(cmd);
    }

    /**
     * 控制信号灯状态
     *
     * @param value
     */
    public void controlSignal(int value) {
        LogUtils.e("signal value = " + value);
        if (value == 99) {
            Netled.setNetledFlash(true);

        } else {
            Netled.setNetledFlash(false);
        }
    }

    /**
     * 是否控制信号灯
     */
    private void freeSingnal() {
        //控制信号灯
        Netled.setNetledState(false);
        Netled.destroy();
    }

    public void free() {
        EventBus.getDefault().unregister(this);
        freeSingnal();
        if (mStateThread != null && mStateThread.isAlive()) {
            mStateThread.interrupt();
        }
        mStateThread = null;

        if (mSmsPhoneManager != null) {
            mSmsPhoneManager.free();
        }
        if (mBatteryManager != null) {
            mBatteryManager.free();
        }

    }

}
