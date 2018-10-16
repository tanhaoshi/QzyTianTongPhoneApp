package com.qzy.tiantong.service.netty;

import android.content.Context;
import android.text.TextUtils;

import com.qzy.led.Netled;
import com.qzy.tiantong.lib.eventbus.MessageEvent;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.phone.BatteryManager;
import com.qzy.tiantong.service.phone.CallLogManager;
import com.qzy.tiantong.service.phone.PhoneClientManager;
import com.qzy.tiantong.service.phone.SmsPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSimCards;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.probuf.lib.data.PhoneAudioCmd;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

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

    private CallLogManager mCallLogManager;

    public PhoneNettyManager(Context context, NettyServerManager manager) {
        mContext = context;
        mNettyServerManager = manager;

        mSmsPhoneManager = new SmsPhoneManager(context, iOnSMSCallback);


        EventBus.getDefault().register(this);

        //initSignal();

        initSendThread();

        initBattery();
        LogUtils.e("getCallLog...11111..");
    }

    /**
     * 初始化电量管理
     */
    private void initBattery() {
        mBatteryManager = new BatteryManager(mContext, new BatteryManager.onBatteryListenr() {
            @Override
            public void onBattery(int level, int scal) {
                if (ttPhoneBattery == null) {
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
    private void initSendThread() {
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
                        //sim 卡是否插入
                        sendSimStateToPhoneClient();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mStateThread.start();
    }


    /**
     * 检查netty管理类是否为空
     *
     * @return
     */
    private boolean checkNettManagerIsNull() {
        if (mNettyServerManager == null) {
            LogUtils.e("mNettyServerManager is null ..");
            return true;
        }
        return false;
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
            if (currentPhoneState != phoneState) {
                PhoneClientManager.getInstance().setEndCallUser();
            }
        } else if (state == TtPhoneState.RING) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.RING;
        } else if (state == TtPhoneState.CALL) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.CALL;
        } else if (state == TtPhoneState.HUANGUP) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP;
            if (currentPhoneState != phoneState) {
                PhoneClientManager.getInstance().setEndCallUser();
            }
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
        if (checkNettManagerIsNull()) return;
        CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.newBuilder()
                .setPhoneState(phoneState)
                .setPhoneNumber(phoneNumber)
                .build();
        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));

    }

    /**
     * 发送客户端 有用户正在通话
     *
     * @param ip
     */
    public void sendTtCallPhoneBackToClient(String ip, String callingIp, boolean flag) {
        if (checkNettManagerIsNull()) return;
        CallPhoneBackProtos.CallPhoneBack callPhoneBack = CallPhoneBackProtos.CallPhoneBack.newBuilder()
                .setIp(callingIp)
                .setIsCalling(flag)
                .build();
        mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_call_phone_back, callPhoneBack));
    }

    /**
     * 发送信号强度
     *
     * @param value
     */
    public void sendTtCallPhoneSignalToClient(int value) {
        currentSignalValue = value;
        //controlSignal(value);
        sendSignalToPhoneClient(value);
    }

    /**
     * 发送电池电量
     */
    public void sendTtPhoneBatteryToClient() {
        if (checkNettManagerIsNull()) return;
        if (ttPhoneBattery != null) {
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_phone_battery, ttPhoneBattery));
        }
    }


    /**
     * 内部发送signal接口
     *
     * @param value
     */
    private void sendSignalToPhoneClient(int value) {
        if (checkNettManagerIsNull()) return;
        TtPhoneSignalProtos.PhoneSignalStrength signalStrength = TtPhoneSignalProtos.PhoneSignalStrength.newBuilder()
                .setSignalStrength(value)
                .build();
        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_phone_signal, signalStrength));
    }

    /**
     * 发送sim状态
     */
    private void sendSimStateToPhoneClient() {
        if (checkNettManagerIsNull()) return;
        boolean hasSim = PhoneUtils.ishasSimCard(mContext);
        LogUtils.d("hasSim = " + hasSim);
        TtPhoneSimCards.TtPhoneSimCard simCard = TtPhoneSimCards.TtPhoneSimCard.newBuilder()
                .setIsSimCard(hasSim)
                .build();
        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_phone_simcard, simCard));
    }


    /**
     * 发送所有通话记录
     *
     * @param callRecordProto
     */
    public void sendCallLogToPhoneClient(String ip, TtCallRecordProtos.TtCallRecordProto callRecordProto) {
        LogUtils.d("callRecordProto list = " + callRecordProto.getCallRecordList().size());
        mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_call_record, callRecordProto));
    }

    /**
     * 发送所有短信
     *
     * @param ttShortMessage
     */
    public void sendCallLogToPhoneClient(String ip, TtShortMessageProtos.TtShortMessage ttShortMessage) {
        LogUtils.d("ttShortMessage list = " + ttShortMessage.getShortMessageList().size());
        mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_short_message, ttShortMessage));
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
            if (checkNettManagerIsNull()) return;
            TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                    .setIp(ip)
                    .setPhoneNumber(phoneNumber)
                    .setIsSend(false)
                    .setIsSendSuccess(true)
                    .setIsReceiverSuccess(false)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
        }

        @Override
        public void onSendFailed(String ip, String phoneNumber) {
            if (checkNettManagerIsNull()) return;
            TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                    .setIp(ip)
                    .setPhoneNumber(phoneNumber)
                    .setIsSend(false)
                    .setIsSendSuccess(false)
                    .setIsReceiverSuccess(false)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
        }

        @Override
        public void onReceiveSuccess(String ip, String phoneNumber) {
            if (checkNettManagerIsNull()) return;
            TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                    .setIp(ip)
                    .setPhoneNumber(phoneNumber)
                    .setIsSend(false)
                    .setIsSendSuccess(false)
                    .setIsReceiverSuccess(true)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
        }

        @Override
        public void onReceiveFailed(String ip, String phoneNumber) {
            if (checkNettManagerIsNull()) return;
            TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                    .setIp(ip)
                    .setPhoneNumber(phoneNumber)
                    .setIsSend(false)
                    .setIsSendSuccess(false)
                    .setIsReceiverSuccess(false)
                    .build();
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));
        }

        @Override
        public void onReceiveSms(String phoneNumber, String smsBody) {
            TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.newBuilder()
                    .setNumberPhone(phoneNumber)
                    .setType(1)// 接收
                    .setMessage(smsBody)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_receiver_short_message, shortMessage));
        }
    };

    public void sendPhoneAudioData(PhoneAudioCmd cmd) {
        String ip = PhoneClientManager.getInstance().isCallingIp();
        if (!TextUtils.isEmpty(ip)) {
            mNettyServerManager.sendPhoneAudioData(ip, cmd);
        }
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
