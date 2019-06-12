package com.qzy.tiantong.service.netty;

import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.qzy.tiantong.lib.eventbus.MessageEvent;
import com.qzy.tiantong.lib.power.PowerUtils;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.lib.utils.QzySystemUtils;
import com.qzy.tiantong.service.BuildConfig;
import com.qzy.tiantong.service.atcommand.AtCommandTools;
import com.qzy.tiantong.service.gps.GpsManager;
import com.qzy.tiantong.service.mobiledata.IMobileDataManager;
import com.qzy.tiantong.service.mobiledata.MobileDataManager;
import com.qzy.tiantong.service.phone.CallLogManager;
import com.qzy.tiantong.service.phone.PhoneClientManager;
import com.qzy.tiantong.service.phone.QzyBatteryManager;
import com.qzy.tiantong.service.phone.SmsPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tiantong.service.phone.TtPhoneSystemanager;
import com.qzy.tiantong.service.phone.data.CallLogInfo;
import com.qzy.tiantong.service.phone.data.ClientInfoBean;
import com.qzy.tiantong.service.phone.data.SmsInfo;
import com.qzy.tiantong.service.phone.data.SosMessage;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.time.DateTimeManager;
import com.qzy.tiantong.service.usb.TtUsbManager;
import com.qzy.tiantong.service.utils.AppUtils;
import com.qzy.tiantong.service.utils.Constant;
import com.qzy.tiantong.service.utils.LedManager;
import com.qzy.tiantong.service.utils.ModuleDormancyUtil;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tiantong.service.utils.SPUtils;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TimerSendProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtDeleCallLogProtos;
import com.qzy.tt.data.TtDeleSmsProtos;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhoneConnectBeatProtos;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhoneRecoverSystemProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSimCards;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneSosMessageProtos;
import com.qzy.tt.data.TtPhoneSosStateProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.probuf.lib.data.PhoneAudioCmd;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.util.internal.SystemPropertyUtil;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class PhoneNettyManager implements IMobileDataManager {

    private ITianTongServer mServer;

    //服务端netty管理工具
    private NettyServerManager mNettyServerManager;


    //客户端手机管理
    private PhoneClientManager mPhoneClientManager;

    private Runnable mStateThread;
    public CallPhoneStateProtos.CallPhoneState.PhoneState currentPhoneState;
    private String currentPhoneNumber = "";
    public int currentSignalValue = 99;
    public int currentSignaldbm = -142;

    private Context mContext;

    private SmsPhoneManager mSmsPhoneManager;

    private GpsManager mGpsManager;

    private TtUsbManager mTtUsbManager;

    private DateTimeManager mDateTimeManager;

    /**
     * 电量获取
     */
    private QzyBatteryManager mBatteryManager;

    private MobileDataManager mMobileDataManager;

    private TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery;

    /** 全局控制系统休眠 */
    public volatile boolean isGotoSleep = true;

    /** 记录卡是不是掉卡 然后再入卡 */
    public boolean isSimCard = true;

    public PhoneNettyManager(Context context, ITianTongServer server,NettyServerManager manager) {
        mContext = context;
        mServer = server;
        mNettyServerManager = manager;

        mDateTimeManager = new DateTimeManager(context, mNettyServerManager);
        mGpsManager = new GpsManager(mContext, mNettyServerManager,mServer);
        mSmsPhoneManager = new SmsPhoneManager(context,mServer, mGpsManager, iOnSMSCallback);

        mTtUsbManager = new TtUsbManager(mContext, mNettyServerManager);

        mMobileDataManager = new MobileDataManager(mContext, this);

        EventBus.getDefault().register(this);

        initSignal();

        //initSendThread();
        startTimer();

    }

    /**
     * 初始发送当前通话的time
     */
    private void initCallingTimer() {
        mHandler.sendEmptyMessageDelayed(1, 500);
        sendTtCallPhoneBackToClientTimer();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    initCallingTimer();
                    break;
            }
        }
    };

    private CountDownTimer countDownTimer;

    private void initSendThread() {

        setNewTimerSend(null);

        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(5 * 1000, 1000) {
            @Override
            public void onTick(long l) {
            // LogUtils.e("onTick = " + (l/1000));
            }

            @Override
            public void onFinish() {
                initSendThread();
            }
        };
        countDownTimer.start();
    }

    /**
     * 停止定时发送信息
     */
    public void stopTimer(){
        if(countDownTimer != null){
            countDownTimer.cancel();
        }
        countDownTimer = null;
    }

    /**
     * 开启定时发送状态
     */
    public void startTimer(){
        initSendThread();
    }

    /**  保存上次协议值 */
    private TimerSendProtos.TimerSend timerSendOld = null;

    /**
     * 新定时发送
     */
    public void setNewTimerSend(String ip) {
       // LogUtils.e("setNewTimerSend... " );
        if (currentPhoneState == null) {
            currentPhoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
        }
        TimerSendProtos.TimerSend timerSend = TimerSendProtos.TimerSend.newBuilder()
                .setCallPhoneState(getTtCallPhoneStateToClient(currentPhoneState, currentPhoneNumber))
                .setSigalStrength(TtPhoneSignalProtos.PhoneSignalStrength.newBuilder()
                        .setSignalStrength(currentSignalValue)
                        .setSignalDbm(currentSignaldbm)
                        .build())
                .setBatterValue(getTtPhoneBatteryToClient())
                .setTtPhoneSimcard(sendSimStateToPhoneClientNew())
                .setTtPhoneGpsPosition(mGpsManager.getLoactionToPhoneClientNew(mGpsManager.getmCurrenLocation()))
                .build();

        checkCureentLampStatus(getTtPhoneBatteryToClient().getLevel());

        if (mNettyServerManager == null) {
            LogUtils.e("mNettyServerManager is null ...");
            return;
        }

        if (timerSend != null) {

            if (timerSendOld != null && timerSendOld.toString().equals(timerSend.toString()) && TextUtils.isEmpty(ip)) {
                LogUtils.e("data is same  now return...");
                return;
            }

            if(!TextUtils.isEmpty(ip)){
                LogUtils.e("mNettyServerManager  ip = " + ip);
            }
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_timer_message, timerSend));

            timerSendOld = timerSend;
        }

        checkRialRecover();
    }

    private void checkRialRecover(){
        String rild_hwa_state = QzySystemUtils.getSystemProperties("init.svc.rild1");
        String radio_config_state = QzySystemUtils.getSystemProperties("init.svc.radio_config");
        String mux_hwa_state = QzySystemUtils.getSystemProperties("init.svc.mux_hwa");
        if(rild_hwa_state.equals("stopped") || radio_config_state.equals("stopped") || mux_hwa_state.equals("stopped")){
            LogUtils.i("The rial is stop>>>>>>>> ready start rildC");
            try {
                mServer.getLocalSocketManager().sendCommand(PowerUtils.recoverRial());
                LogUtils.i("The rial is start ");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取节点当前充电以及未充电的状态 及灯的控制
     */
    private void checkCureentLampStatus(int batteryLevel) {
        String status = ModuleDormancyUtil.getNodeString(Constant.LAMP_PATH);
        Integer integerStatus = Integer.valueOf(status) + 1;
        controlLamp(batteryLevel, 100, integerStatus);
    }

    /**
     * 灯 闪烁的控制
     */
    private void controlLamp(int level, int scale, int status) {
        //当前处于充电状态，且充电没有充满
        if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
            //亮红灯
            //关蓝灯
            LedManager.setandCleanLedFlag(mServer,LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH,
                    LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH
                            | LedManager.FLAG_BATTERY_LOW_LED_TIMER);
            //当前处于充电状态，且电量充满
        } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
            //亮蓝灯
            //关红灯
            LedManager.setandCleanLedFlag(mServer,LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH,
                    LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH
                            | LedManager.FLAG_BATTERY_FULL_BLUE_LED_TIMER);
            //当前处于不是充电
        } else if (level * 100 / scale < 21) {
            //闪红灯
            //关蓝灯
            LedManager.setandCleanLedFlag(mServer,LedManager.FLAG_BATTERY_LOW_LED_TIMER
                            | LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH,
                    LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH);
        } else {
            //红灯蓝灯一起关闭
            LedManager.setandCleanLedFlag(mServer,0, LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH
                    | LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH);
        }
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

    public TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion mTtPhoneGetServerVersion;



    /**
     * 返回版本号信息给appFF
     *
     * @param ttPhoneGetServerVersion
     */
    public void getServerVerion(TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion ttPhoneGetServerVersion) {
        AppUtils.requireNonNull(mServer.getQzyPhoneManager().mAtCommandToolManager);
        try {
            if (checkNettManagerIsNull()) {
                return;
            }

            if(mTtPhoneGetServerVersion == null || TextUtils.isEmpty(mTtPhoneGetServerVersion.getTiantongModelVersion())){
                mTtPhoneGetServerVersion = TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.newBuilder()
                        .setIp("192.168.43.1")
                        .setServerApkVersionName(BuildConfig.VERSION_NAME)
                        .setServerSieralNo(QzySystemUtils.getSerialNumberCustom())
                        .setTiantongModelVersion("未知")
                        .build();
            }
            LogUtils.d(" ip = " + mTtPhoneGetServerVersion.getIp() + " versionName = " + mTtPhoneGetServerVersion.getServerApkVersionName() + " sieralNo = " + mTtPhoneGetServerVersion.getServerSieralNo());
            mNettyServerManager.sendData(ttPhoneGetServerVersion.getIp(),
                    PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_version_info, mTtPhoneGetServerVersion));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 存储版本信息
     * @param moduleServer
     */
    public void saveServerVersion(String moduleServer){
        String versionName = BuildConfig.VERSION_NAME;
        String sieralNo = QzySystemUtils.getSerialNumberCustom();


        mTtPhoneGetServerVersion = TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.newBuilder()
                .setIp("192.168.43.1")
                .setServerApkVersionName(versionName)
                .setServerSieralNo(sieralNo)
                .setTiantongModelVersion(moduleServer.trim())
                .build();
        LogUtils.d(" saveServerVersion ip = " + mTtPhoneGetServerVersion.getIp() + " versionName = " + mTtPhoneGetServerVersion.getServerApkVersionName() + " sieralNo = " + mTtPhoneGetServerVersion.getServerSieralNo());
    }

    /**
     * 返回sos存储信息
     */
    public void getSosMsgInfo(TtPhoneSosMessageProtos.TtPhoneSosMessage sosmsg) {
        getSosMsgInfoip(sosmsg.getIp());
    }

    /**
     * 返回sos存储信息
     */
    public void getSosMsgInfoip(String ip) {
        SosMessage sosMessage = TtPhoneSystemanager.getSosMessage();
        boolean isExist = true;
        if (sosMessage == null) {
            isExist = false;
        }


        String phoneNumber = "";
        String message = "";
        int delayTime = -1;
        if (sosMessage != null) {
            phoneNumber = sosMessage.getPhoneNumber();
            message = sosMessage.getMessage();
            delayTime = sosMessage.getDelayTime();
        }

        TtPhoneSosMessageProtos.TtPhoneSosMessage ttPhoneSosMessage = TtPhoneSosMessageProtos.TtPhoneSosMessage.newBuilder()
                .setExistSetting(isExist)
                .setPhoneNumber(phoneNumber)
                .setMessageContent(message)
                .setDelaytime(delayTime)
                .build();

        mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_sos_info_msg, ttPhoneSosMessage));

    }

    /**
     * 恢复出厂设置
     *
     * @param ttPhoneRecoverSystem
     */
    public void getRecoverSystem(TtPhoneRecoverSystemProtos.TtPhoneRecoverSystem ttPhoneRecoverSystem) {
        LogUtils.d("getRecoverSystem");
        try {
            TtPhoneSystemanager.doMasterClear(mContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 控制信号灯初始化
     */
    private void initSignal() {
        try {
            boolean isSimIn = PhoneUtils.ishasSimCard(mContext);
            LogUtils.d("isSimIn = " + isSimIn);
            if (isSimIn) {
                LedManager.setandCleanLedFlag(mServer,LedManager.FLAG_NET_GREEN_LED_TIMER
                        | LedManager.FLAG_NET_GREEN_LED_SWITCH, LedManager.FLAG_POWER_BLUE_LED_SWITCH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private Object objects = new Object();
    /**
     * 更新电话状态
     *
     * @param state
     */
    public void updateTtCallPhoneState(TtPhoneState state, String phoneNumber) {
        synchronized (objects){
            LogUtils.e("updateTtCallPhoneState " + state.ordinal());
            CallPhoneStateProtos.CallPhoneState.PhoneState phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;

            if (state == TtPhoneState.NOCALL) {
                phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL;
                if (currentPhoneState != phoneState) {
                    PhoneClientManager.getInstance().setEndCallUser();
                    sendTtCallPhoneBackToClientTimer();
                }
            } else if (state == TtPhoneState.RING) {
                phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.RING;
            } else if (state == TtPhoneState.CALL) {
                phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.CALL;
                //处理同时接听电话,其他链接手机没有挂断Bug
//            disposeCallPhone();
            } else if (state == TtPhoneState.HUANGUP) {

                phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP;
                if (currentPhoneState != phoneState) {
                    PhoneClientManager.getInstance().setEndCallUser();
                    sendTtCallPhoneBackToClientTimer();
                }

                mSmsPhoneManager.isKeyF2Incoming = false;

            } else if (state == TtPhoneState.INCOMING) {
                phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.INCOMING;
            }
            currentPhoneState = phoneState;
            currentPhoneNumber = phoneNumber;
            sendTtCallPhoneStateToClient(phoneState, phoneNumber);
        }
    }

    /**
     * 发送电话状态到客户端
     *
     * @param phoneState
     */
    private void sendTtCallPhoneStateToClient(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState, String phoneNumber) {
        if (checkNettManagerIsNull()) return;
        if (phoneNumber == null) {
            phoneNumber = "13352528585";
        }

        String callInigIp = PhoneClientManager.getInstance().isCallingIp();
        if (TextUtils.isEmpty(callInigIp)) {
            callInigIp = "192.168.43.1";
        }
        CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.newBuilder()
                .setPhoneState(phoneState)
                .setPhoneNumber(phoneNumber)
                .setNowCallingIp(callInigIp)
                .build();

        if ((phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL) || (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.RING)) {
            if (!TextUtils.isEmpty(callInigIp)) {
                //LogUtils.i("sendTtCallPhoneStateToClient callingIp = " + callInigIp + " phonestate = " + callPhoneState.getPhoneState().ordinal());
                mNettyServerManager.sendData(callInigIp, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
            }
        } else {
            if (TextUtils.isEmpty(callInigIp) && (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL)) {
                return;
            }
            //LogUtils.i("sendTtCallPhoneStateToClient callingIp = null  " + " phonestate = " + callPhoneState.getPhoneState().ordinal());
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
        }
    }

    /**
     * 新定时发送通话状态
     *
     * @param phoneState
     * @param phoneNumber
     * @return
     */
    private CallPhoneStateProtos.CallPhoneState getTtCallPhoneStateToClient(CallPhoneStateProtos.CallPhoneState.PhoneState phoneState, String phoneNumber) {
        if (checkNettManagerIsNull()) return null;
        if (phoneNumber == null) {
            phoneNumber = "13352528585";
        }

        String callInigIp = PhoneClientManager.getInstance().isCallingIp();
        if (TextUtils.isEmpty(callInigIp)) {
            callInigIp = "192.168.43.1";
        }
        CallPhoneStateProtos.CallPhoneState callPhoneState = CallPhoneStateProtos.CallPhoneState.newBuilder()
                .setPhoneState(phoneState)
                .setPhoneNumber(phoneNumber)
                .setNowCallingIp(callInigIp)
                .build();
        if ((phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL) || (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.RING)) {
            if (!TextUtils.isEmpty(callInigIp)) {
                //LogUtils.i("sendTtCallPhoneStateToClient callingIp = " + callInigIp + " phonestate = " + callPhoneState.getPhoneState().ordinal());

                return callPhoneState;
            }
        } else {
            if (TextUtils.isEmpty(callInigIp) && (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL)) {
                return callPhoneState;
            }
        }

        return callPhoneState;
    }

    /**
     * 定时发送 发送当前正有人通话
     */
    private void sendTtCallPhoneBackToClientTimer() {
        String callingIp = PhoneClientManager.getInstance().isCallingIp();
        LogUtils.i("look up callingIp = " + callingIp);
        if (!TextUtils.isEmpty(callingIp)) {
            sendTtCallPhoneBackToClient(null, callingIp, true);
        } else {
            sendTtCallPhoneBackToClient(null, "", false);
        }
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
    public void sendTtCallPhoneSignalToClient(int value,int dbm) {
        currentSignalValue = value;
        currentSignaldbm = dbm;

        controlSignal(value);
    }

    /**
     * 获取 电量
     *
     * @return
     */
    public TtPhoneBatteryProtos.TtPhoneBattery getTtPhoneBatteryToClient() {
        String level = ModuleDormancyUtil.getNodeString(Constant.BATTERY_PATH);
        ttPhoneBattery = TtPhoneBatteryProtos.TtPhoneBattery.newBuilder()
                .setLevel(Integer.valueOf(level))
                .setScale(100)
                .build();
        return ttPhoneBattery;
    }


    /**
     * 新方式发送状态数据
     *
     * @return
     */
    private TtPhoneSimCards.TtPhoneSimCard sendSimStateToPhoneClientNew() {
        if (checkNettManagerIsNull()) return null;
        boolean hasSim = PhoneUtils.ishasSimCard(mContext);
        if(currentSignalValue != 99){
            hasSim = true;
        }
        if (hasSim) {
                //Netled.setNetledState(true);
            if(mTtPhoneGetServerVersion == null || TextUtils.isEmpty(mTtPhoneGetServerVersion.getTiantongModelVersion())) {
                mServer.getQzyPhoneManager().mAtCommandToolManager.sendAtCommand(AtCommandTools.AT_COMMAND_VERSION);
            }
            controlSignal(currentSignalValue); // 检测卡的状态来控制灯的闪烁
        } else {
                //未检测到卡,下面应该常亮亮蓝灯
            LedManager.setandCleanLedFlag(mServer,LedManager.FLAG_POWER_BLUE_LED_SWITCH,
                    LedManager.FLAG_NET_GREEN_LED_SWITCH
                            | LedManager.FLAG_POWER_BLUE_LED_TIMER);
        }
        TtPhoneSimCards.TtPhoneSimCard simCard = TtPhoneSimCards.TtPhoneSimCard.newBuilder()
                .setIsSimCard(hasSim)
                .build();

        isSimCard = hasSim;

        return simCard;
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
     * 短信回调
     */
    private SmsPhoneManager.IOnSMSCallback iOnSMSCallback = new SmsPhoneManager.IOnSMSCallback() {
        @Override
        public void onSendSuccess(String ip, String phoneNumber) {
            if (checkNettManagerIsNull()) return;
            TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                    .setIp(ip)
                    .setPhoneNumber(phoneNumber)
                    .setIsSend(true)
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
            /*if (checkNettManagerIsNull()) return;
            TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                    .setIp(ip)
                    .setPhoneNumber(phoneNumber)
                    .setIsSend(false)
                    .setIsSendSuccess(false)
                    .setIsReceiverSuccess(true)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));*/
        }

        @Override
        public void onReceiveFailed(String ip, String phoneNumber) {
            /*if (checkNettManagerIsNull()) return;
            TtPhoneSmsProtos.TtPhoneSms tt = TtPhoneSmsProtos.TtPhoneSms.newBuilder()
                    .setIp(ip)
                    .setPhoneNumber(phoneNumber)
                    .setIsSend(false)
                    .setIsSendSuccess(false)
                    .setIsReceiverSuccess(false)
                    .build();
            mNettyServerManager.sendData(ip, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.phone_send_sms_callback, tt));*/
        }

        @Override
        public void onReceiveSms(String phoneNumber, String smsBody) {
           /* TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.newBuilder()
                    .setNumberPhone(phoneNumber)
                    .setType(1)// 接收
                    .setMessage(smsBody)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_receiver_short_message, shortMessage));*/
        }

        @Override
        public void onReceiveSms(SmsInfo smsInfo) {
            TtShortMessageProtos.TtShortMessage.ShortMessage shortMessage = TtShortMessageProtos.TtShortMessage.ShortMessage.newBuilder()
                    .setId(smsInfo.getId())
                    .setNumberPhone(smsInfo.getNumber())
                    .setName(smsInfo.getName())
                    .setMessage(smsInfo.getBody())
                    .setTime(smsInfo.getDate())
                    .setType(smsInfo.getType())
                    .setIsRead(smsInfo.getIsRead() == 1 ? true : false)
                    .build();
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_receiver_short_message, shortMessage));
        }

        @Override
        public void onSosState(boolean isStart) {
            sendSosinitStatus(isStart);
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
        //LogUtils.e("signal value = " + value);
        if (value == 99) {
            //Netled.setNetledFlash(true);
            LedManager.setandCleanLedFlag(mServer,LedManager.FLAG_NET_GREEN_LED_TIMER
                    | LedManager.FLAG_NET_GREEN_LED_SWITCH, LedManager.FLAG_POWER_BLUE_LED_SWITCH);
        } else {
            //Netled.setNetledFlash(false);
            //LogUtils.i("set net_green timer flash and turnoff power led");
            LedManager.setandCleanLedFlag(mServer,LedManager.FLAG_NET_GREEN_LED_SWITCH,
                    LedManager.FLAG_NET_GREEN_LED_TIMER
                            | LedManager.FLAG_POWER_BLUE_LED_SWITCH);
        }
    }

    /**
     * 返回gps控制类
     *
     * @return
     */
    public GpsManager getmGpsManager() {
        return mGpsManager;
    }

    /**
     * 返回usb模式 切换类
     *
     * @return
     */
    public TtUsbManager getmTtUsbManager() {
        return mTtUsbManager;
    }

    /**
     * 时间管理类
     *
     * @return
     */
    public DateTimeManager getmDateTimeManager() {
        return mDateTimeManager;
    }

    /**
     * 获取短信管理工具
     *
     * @return
     */
    public SmsPhoneManager getmSmsPhoneManager() {
        return mSmsPhoneManager;
    }

    /**
     * 是否控制信号灯
     */
    private void freeSingnal() {
        //控制信号灯
//        Netled.setNetledState(false);
//        Netled.destroy();
    }

    /**
     * 获取天通猫初始状态 , 并发送
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void getServerMobileDataStatus() {
        try {
            boolean isStatus = mMobileDataManager.getMobileDataState(mContext);
            TtPhoneMobileDataProtos.TtPhoneMobileData mobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.newBuilder()
                    .setResponseStatus(isStatus)
                    .build();
            LogUtils.i("getServerMobileDataStatus look over status = " + isStatus);
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd
                    (PrototocalTools.IProtoClientIndex.response_server_mobile_data_init, mobileData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开天通猫移动数据
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void setEnablePhoneData(TtPhoneMobileDataProtos.TtPhoneMobileData ttPhoneMobileData) {
        try {
            mMobileDataManager.setMobileDataState(mContext, ttPhoneMobileData.getIsEnableData());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMobileDataSwitch(boolean isSwitch) {
        sendMobileData(isSwitch);
    }

    private void sendMobileData(boolean isStatus) {
        TtPhoneMobileDataProtos.TtPhoneMobileData mobileData = TtPhoneMobileDataProtos.TtPhoneMobileData.newBuilder()
                .setResponseStatus(isStatus)
                .build();
        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd
                (PrototocalTools.IProtoClientIndex.response_phone_data_status, mobileData));
    }

    /**
     * 获取天通猫sos初始状态
     */
    public void getServerSosInitStatus() {
        if (mSmsPhoneManager.isSosState()) {
            //打开
            sendSosinitStatus(true);
        } else {
            //关闭
            sendSosinitStatus(false);
        }

    }

    private void sendSosinitStatus(boolean isSwitch) {
        TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.newBuilder()
                .setIsResponse(true)
                .setIsSwitch(isSwitch)
                .build();
        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_sos_init_status,
                ttPhoneSosState));
    }

    /**
     * 控制天通猫服务开启关闭sos
     */
    public void switchServerSos(TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState) {
        if (ttPhoneSosState == null) {
            return;
        }
        if(ttPhoneSosState.getIsSwitch()){
            mServer.getSystemSleepManager().wakeupTianTong();
        }else{
            mServer.getSystemSleepManager().sleepTianTong();
        }
        mSmsPhoneManager.switchServerSos(ttPhoneSosState.getIsSwitch());
    }


    /**
     * 删除通话记录
     */
    public void deleteCallLog(final TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLog) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    if (ttDeleCallLog == null) {
                        LogUtils.e(" ttDeleCallLog is null");
                        return;
                    }

                    boolean state = CallLogManager.deleteCallLog(mContext, ttDeleCallLog);
                    TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLogR = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                            .setIp(ttDeleCallLog.getIp())
                            .setIsResponse(true)
                            .setState(state)
                            .build();

                    mNettyServerManager.sendData(ttDeleCallLog.getIp(), PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_del_calllog, ttDeleCallLogR));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 删除短信
     */
    public void deleteSms(final TtDeleSmsProtos.TtDeleSms ttDeleSms) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ttDeleSms == null) {
                        LogUtils.e(" ttDeleSms is null");
                        return;
                    }
                    CallLogManager.deleteSms(mContext, ttDeleSms);
                    /*TtDeleCallLogProtos.TtDeleCallLog ttDeleCallLogR = TtDeleCallLogProtos.TtDeleCallLog.newBuilder()
                            .setIp(ttDeleCallLog.getIp())
                            .setIsResponse(true)
                            .setState(state)
                            .build();

                    mNettyServerManager.sendData(ttDeleCallLog.getIp(),PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_del_calllog,ttDeleCallLogR));*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 更新服务端电话记录 中已读的状态 改为 来电状态
     */
    public void updateCallRecordStatus(final TtCallRecordProtos.TtCallRecordProto ttCallRecordProto) {
        try {
            if (null != ttCallRecordProto) {
                List<CallLogInfo> logInfos = new ArrayList<>();
                for (TtCallRecordProtos.TtCallRecordProto.CallRecord callRecord : ttCallRecordProto.getCallRecordList()) {
                    CallLogInfo callLogInfo = new CallLogInfo(callRecord.getId(), callRecord.getName(), callRecord.getPhoneNumber(), callRecord.getDate()
                            , callRecord.getType(), callRecord.getDuration());
                    logInfos.add(callLogInfo);
                }
                PhoneUtils.updateCallLogByID(mContext, logInfos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkBeat(TtPhoneConnectBeatProtos.TtPhoneConnectBeat ttPhoneConnectBeat){
        LogUtils.i("server check beat ");
        AppUtils.requireNonNull(ttPhoneConnectBeat);
        if(ttPhoneConnectBeat.getIsConnect() && ttPhoneConnectBeat.getRequest()){
            TtPhoneConnectBeatProtos.TtPhoneConnectBeat connectBeat =
                    TtPhoneConnectBeatProtos.TtPhoneConnectBeat.newBuilder()
                    .setResponse(true)
                    .setIsConnect(true)
                    .build();
            mNettyServerManager.sendData(null,PhoneCmd.getPhoneCmd
                    (PrototocalTools.IProtoClientIndex.RESPONSE_CONNECT_BEAT,connectBeat));
            LogUtils.i("server send beat ");
        }
    }

    public void free() {

        if (mHandler != null && mHandler.hasMessages(1)) {
            mHandler.removeMessages(1);
        }
        mHandler = null;

        if(countDownTimer != null){
            countDownTimer.cancel();
        }

        EventBus.getDefault().unregister(this);
        freeSingnal();

        if (mHandler != null && mStateThread != null) {
            mHandler.removeCallbacks(mStateThread);
        }

        if (mSmsPhoneManager != null) {
            mSmsPhoneManager.free();
        }
        if (mBatteryManager != null) {
            mBatteryManager.free();
        }
    }
}
