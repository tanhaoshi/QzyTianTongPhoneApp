package com.qzy.tiantong.service.netty;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;

import com.qzy.led.Netled;
import com.qzy.tiantong.lib.eventbus.MessageEvent;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.lib.utils.QzySystemUtils;
import com.qzy.tiantong.service.BuildConfig;
import com.qzy.tiantong.service.mobiledata.IMobileDataManager;
import com.qzy.tiantong.service.phone.BatteryManager;
import com.qzy.tiantong.service.gps.GpsManager;
import com.qzy.tiantong.service.phone.PhoneClientManager;
import com.qzy.tiantong.service.phone.SmsPhoneManager;
import com.qzy.tiantong.service.phone.TtPhoneState;
import com.qzy.tiantong.service.phone.TtPhoneSystemanager;
import com.qzy.tiantong.service.phone.data.SmsInfo;
import com.qzy.tiantong.service.time.DateTimeManager;
import com.qzy.tiantong.service.usb.TtUsbManager;
import com.qzy.tiantong.service.mobiledata.MobileDataManager;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tt.data.CallPhoneBackProtos;
import com.qzy.tt.data.CallPhoneStateProtos;
import com.qzy.tt.data.TtCallRecordProtos;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhoneGetServerVersionProtos;
import com.qzy.tt.data.TtPhoneMobileDataProtos;
import com.qzy.tt.data.TtPhoneRecoverSystemProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.data.TtPhoneSimCards;
import com.qzy.tt.data.TtPhoneSmsProtos;
import com.qzy.tt.data.TtPhoneSosStateProtos;
import com.qzy.tt.data.TtShortMessageProtos;
import com.qzy.tt.probuf.lib.data.PhoneAudioCmd;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;

/**
 * Created by yj.zhang on 2018/8/9.
 */

public class PhoneNettyManager implements IMobileDataManager{

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

    private GpsManager mGpsManager;

    private TtUsbManager mTtUsbManager;

    private DateTimeManager mDateTimeManager;

    /**
     * 电量获取
     */
    private BatteryManager mBatteryManager;

    private MobileDataManager mMobileDataManager;

    private TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery;

    private Timer timerCalling;

    private boolean isG4Test = false;

    public PhoneNettyManager(Context context, NettyServerManager manager) {
        mContext = context;
        mNettyServerManager = manager;

        mDateTimeManager = new DateTimeManager(context, mNettyServerManager);
        mGpsManager = new GpsManager(mContext, mNettyServerManager);
        mSmsPhoneManager = new SmsPhoneManager(context, mGpsManager,iOnSMSCallback);

        mTtUsbManager = new TtUsbManager(mContext, mNettyServerManager);

        mMobileDataManager = new MobileDataManager(mContext,this);

        EventBus.getDefault().register(this);

        if(!isG4Test) {
            initSignal();
        }

        initSendThread();

        initBattery();
        LogUtils.e("getCallLog...11111..");

        // initCallingTimer();
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

                        //发送gps状态
                        mGpsManager.sendGpsState();
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
     * 返回版本号信息给app
     *
     * @param ttPhoneGetServerVersion
     */
    public void getServerVerion(TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion ttPhoneGetServerVersion) {
        LogUtils.d("getServerVerion versionName ");
        try {

            if(checkNettManagerIsNull()){
                return;
            }
            LogUtils.d("getServerVerion versionName ");
            String versionName = BuildConfig.VERSION_NAME;
            String sieralNo = QzySystemUtils.getSerialNumberCustom();
            LogUtils.d(" ip = " + ttPhoneGetServerVersion.getIp() + " versionName = " + versionName + " sieralNo = " + sieralNo);
            TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion ttPhoneGetServerVersion1 = TtPhoneGetServerVersionProtos.TtPhoneGetServerVersion.newBuilder()
                    .setIp(ttPhoneGetServerVersion.getIp())
                    .setServerApkVersionName(versionName)
                    .setServerSieralNo(sieralNo)
                    .build();
            mNettyServerManager.sendData(ttPhoneGetServerVersion.getIp(),PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_version_info, ttPhoneGetServerVersion1));

        } catch (Exception e) {
            e.printStackTrace();
        }
//        ip = 192.168.1.103 versionName = 1.0.0 sieralNo =
//        ip = 192.168.43.164 versionName = 1.0.0 sieralNo =
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
            Netled.init();

            boolean isSimIn = PhoneUtils.ishasSimCard(mContext);
            LogUtils.d("isSimIn = " + isSimIn);
            if(isSimIn){
                Netled.setNetledState(true);
                Netled.setNetledFlash(true);
            }

        }catch (Exception e){
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
                sendTtCallPhoneBackToClientTimer();
            }
        } else if (state == TtPhoneState.RING) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.RING;
        } else if (state == TtPhoneState.CALL) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.CALL;
        } else if (state == TtPhoneState.HUANGUP) {
            phoneState = CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP;
            if (currentPhoneState != phoneState) {
                PhoneClientManager.getInstance().setEndCallUser();
                sendTtCallPhoneBackToClientTimer();
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
        String callInigIp = PhoneClientManager.getInstance().isCallingIp();
//        if (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL) {
//            if(!TextUtils.isEmpty(callInigIp)){
//                mNettyServerManager.sendData(callInigIp, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
//            }
//        } else {
//            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
//        }
//        if (!TextUtils.isEmpty(callInigIp) && (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL)) {
//            mNettyServerManager.sendData(callInigIp, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
//        } else {
//            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
//        }

        if ((phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL) || (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.RING)) {
            if(!TextUtils.isEmpty(callInigIp)){
                mNettyServerManager.sendData(callInigIp, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
            }
        } else {
            if (TextUtils.isEmpty(callInigIp) && (phoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.CALL)){
                return;
            }
            mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.call_phone_state, callPhoneState));
        }
    }


    /**
     * 定时发送 发送当前正有人通话
     */
    private void sendTtCallPhoneBackToClientTimer() {

        String callingIp = PhoneClientManager.getInstance().isCallingIp();
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
    public void sendTtCallPhoneSignalToClient(int value) {
        currentSignalValue = value;
        if(!isG4Test) {
            controlSignal(value);
        }

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
        // LogUtils.d("hasSim = " + hasSim + " currentSignalValue = " + currentSignalValue);
        if(!isG4Test) {
            if(hasSim) {
                Netled.setNetledState(true);
                controlSignal(currentSignalValue); // 检测卡的状态来控制灯的闪烁
            }
        }
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
        LogUtils.e("signal value = " + value);
        if (value == 99) {
            Netled.setNetledFlash(true);

        } else {
            Netled.setNetledFlash(false);
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
        Netled.setNetledState(false);
        Netled.destroy();
    }

    /**
     * 获取天通猫初始状态 , 并发送
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public void getServerMobileDataStatus(){
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
            mMobileDataManager.setMobileDataState( mContext,ttPhoneMobileData.getIsEnableData());
//            if (mMobileDataManager.getMobileDataState(mContext)) {
//                //这个是打开了的
//                sendMobileData(true);
//            } else {
//                //这个是关闭了的
//                sendMobileData(false);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getMobileDataSwitch(boolean isSwitch) {
        if(mMobileDataManager.getMobileDataState(mContext)){
            LogUtils.i("the physical is open mobile data");
        }else{
            LogUtils.i("the physical mobile data is close");
        }
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
    public void getServerSosInitStatus(){
        if(mSmsPhoneManager.isSosState()){
            //打开
            sendSosinitStatus(true);
        }else{
            //关闭
            sendSosinitStatus(false);
        }
    }

    private void sendSosinitStatus(boolean isSwitch){
        TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState = TtPhoneSosStateProtos.TtPhoneSosState.newBuilder()
                .setIsResponse(true)
                .setIsSwitch(isSwitch)
                .build();
        mNettyServerManager.sendData(null,PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.response_server_sos_init_status,
                ttPhoneSosState));

    }

    /**
     * 关闭服务天通猫服务
     */
    public void closeServerSos(TtPhoneSosStateProtos.TtPhoneSosState ttPhoneSosState){
        if(ttPhoneSosState == null){
            return;
        }
       mSmsPhoneManager.stopSendSosMsg();
    }

    public void free() {

        if (mHandler != null && mHandler.hasMessages(1)) {
            mHandler.removeMessages(1);
        }
        mHandler = null;

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
