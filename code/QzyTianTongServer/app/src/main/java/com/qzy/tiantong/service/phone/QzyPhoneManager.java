package com.qzy.tiantong.service.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;

import com.android.internal.telephony.ITelephony;
import com.qzy.tiantong.lib.localsocket.LocalPcmSocketManager;
import com.qzy.tiantong.lib.power.PowerUtils;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.atcommand.AtCommandToolManager;
import com.qzy.tiantong.service.atcommand.AtCommandTools;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.utils.Constant;
import com.qzy.tiantong.service.utils.ModuleDormancyUtil;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tiantong.service.utils.PowerControl;
import com.qzy.tt.data.TtPhoneWifiProtos;

import java.lang.reflect.Method;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class QzyPhoneManager {

    private Context mContext;
    private ITianTongServer mServer;
    private int mstate;
    // 休眠标记位
    private boolean dormancyFlag = true;

    private AtCommandToolManager mAtCommandToolManager;

    private LocalPcmSocketManager mLocalPcmSocketManager;

    public QzyPhoneManager(Context context, ITianTongServer server,LocalPcmSocketManager localPcmSocketManager) {
        mContext = context;
        mServer = server;

        setPhoneListener();
        //初始化at指令
        mAtCommandToolManager = new AtCommandToolManager(context, new AtCommandToolManager.IAtResultListener() {
            @Override
            public void onResult(String cmd, String result) {

            }
        });
        this.mLocalPcmSocketManager = localPcmSocketManager;
    }

    /**
     * 设置wifi密码
     */
    public void setWifiPasswd(TtPhoneWifiProtos.TtWifi ttWifi) {
        Intent intent = new Intent("com.qzy.tt.ACTION_CHANGE_WIFI");
        intent.putExtra("passwd", ttWifi.getPasswd());
        mContext.sendBroadcast(intent);
    }


    /**
     * 设置电话监听
     */
    private void setPhoneListener() {
        TelephonyManager tManager = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
        tManager.listen(new MyTelephoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        tManager.listen(new MyTelephoneListener(), PhoneStateListener.LISTEN_SERVICE_STATE);
        tManager.listen(new MyTelephoneListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    /**
     * 打电话
     *
     * @param phoneNum
     */
    public void callPhone(String ip, String phoneNum) {

        wakeupCallChannel();

        LogUtils.i("start call phone ...");

        //设置电话ip
        if (!mServer.setCurrenCallingIp(ip)) {
            LogUtils.e("has user calling pleas waiting...");
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
        LogUtils.e("tel phone ..  " + phoneNum);
    }

    /** 打电话之前将模块进行唤醒 */
    private void wakeupCallChannel(){
        try {
            mLocalPcmSocketManager.sendCommand(PowerUtils.wakeupCommand());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 挂断电话
     *
     * @param
     */
    public void hangupPhone(String ip) {
        mServer.setEndCallingIp(ip);
        endCall();
        try {
            mLocalPcmSocketManager.sendCommand(PowerUtils.sleepCommand());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        endCallingAndClearIp();
    }


    /**
     * 挂断并清除通话ip
     */
    private void endCallingAndClearIp(){
        LogUtils.e("endCallingAndClearIp ..  " );
        mServer.freeTtPcmDevice();
        mServer.onPhoneStateChange(TtPhoneState.HUANGUP);
    }

    /**
     * 接听电话
     */
    public void acceptCalling(String ip) {
        //设置电话ip
        mServer.setCurrenCallingIp(ip);

        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean state = answerRingCall();
            }
        }).start();

        sendPhoneCalling();
    }


    private void endCall() {
        // IBinder iBinder = ServiceManager.getService(TELEPHONY_SERVICE);
        // ServiceManager 是被系统隐藏掉了 所以只能用反射的方法获取
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            telephony.endCall();
            if(mAtCommandToolManager != null){
                mAtCommandToolManager.sendAtCommand(AtCommandTools.at_command_hungup);
            }
            LogUtils.d("endcall .....");
            sendPhonehangup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private boolean answerRingCall() {
        // IBinder iBinder = ServiceManager.getService(TELEPHONY_SERVICE);
        // ServiceManager 是被系统隐藏掉了 所以只能用反射的方法获取
        LogUtils.d("answerRingCall start ..");

        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            telephony.answerRingingCall();
            LogUtils.d("answerRingCall .....");
            return true;

        } catch (Exception e) {
            LogUtils.e("Sandy", e);
            try {
                LogUtils.e("Sandy", e);
                Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
                KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
                intent.putExtra("android.intent.extra.KEY_EVENT", keyEvent);
                mContext.sendOrderedBroadcast(intent, "android.permission.CALL_PRIVILEGED");
                return true;
            } catch (Exception e2) {

                try {
                    LogUtils.e("Sandy", e2);
                    Intent meidaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    KeyEvent keyEvent = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK);
                    meidaButtonIntent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                    mContext.sendOrderedBroadcast(meidaButtonIntent, null);
                    return true;
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 发送接通
     */
    private void sendPhoneCalling() {
        Intent intent = new Intent("com.qzy.phone.state");
        intent.putExtra("phone_state", "2");
        mContext.sendBroadcast(intent);
        LogUtils.d("sendPhonehangup send phone state = 2");
    }

    /**
     * 发送挂断
     */
    private void sendPhonehangup() {
        Intent intent = new Intent("com.qzy.phone.state");
        intent.putExtra("phone_state", "0");
        mContext.sendBroadcast(intent);
        LogUtils.d("sendPhonehangup send phone state = 0");
        if (mServer != null) {
            mServer.onPhoneStateChange(TtPhoneState.HUANGUP);
        }
    }


    //电话状态监听
    public class MyTelephoneListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            mstate = state;
            LogUtils.i("Call State Changed value = " + mstate);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    //等待接听状态
                    String mIncomingNumber = incomingNumber;
                    LogUtils.e("=====RINGING :" + mIncomingNumber + "=========");
                    mServer.initTtPcmDevice();
                    mServer.onPhoneIncoming(TtPhoneState.INCOMING, mIncomingNumber);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    LogUtils.e("======get call 1111  RING ======== ");
//                    mServer.onPhoneStateChange(TtPhoneState.RING);
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    LogUtils.e("========hung up=======");
                    //mServer.closeRecorderAndPlayer();
                    mServer.freeTtPcmDevice();
                    mServer.onPhoneStateChange(TtPhoneState.HUANGUP);
                    //挂断状态
                    break;
            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            //获取网络信号强度
            //获取0-4的5种信号级别，越大信号越好,但是api23开始才能用
            //            int level = signalStrength.getLevel();
            int gsmSignalStrength = signalStrength.getGsmSignalStrength();
            //获取网络类型
            int netWorkType = PhoneUtils.getNetWorkType(mContext);

            switch (netWorkType) {
                case PhoneUtils.NETWORKTYPE_WIFI:
                    LogUtils.e("network type wifi,signaleS = " + gsmSignalStrength);
                    break;
                case PhoneUtils.NETWORKTYPE_2G:
                    LogUtils.e("network type 2G,signaleS = " + gsmSignalStrength);
                    break;
                case PhoneUtils.NETWORKTYPE_4G:
                    LogUtils.e("network type 4G,signaleS = " + gsmSignalStrength);
                    break;
                case PhoneUtils.NETWORKTYPE_NONE:
                    LogUtils.e("network type none,signaleS = " + gsmSignalStrength);
//                    controlSignalStrength(gsmSignalStrength);
                    mServer.onPhoneSignalStrengthChange(gsmSignalStrength);
                    break;
                case -1:
                    LogUtils.e("network type -1,signaleS = " + gsmSignalStrength);
                    break;
            }
        }

        @Override
        public void onServiceStateChanged(ServiceState serviceState) {
            super.onServiceStateChanged(serviceState);
            LogUtils.i("service state = " + serviceState.getState());
            switch (serviceState.getState()) {
                case ServiceState.STATE_IN_SERVICE:
                    mServer.onPhoneSignalStrengthChange(8);
                    break;
                case ServiceState.STATE_OUT_OF_SERVICE:
                    mServer.onPhoneSignalStrengthChange(99);
                    break;
            }
        }
    }

//    private boolean inPreSignal = false;
//    private boolean inPreNoSignal = true;
//    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.O)
//    private synchronized void controlSignalStrength(int gsmSignalStrength){
//        // 入网
//            if(gsmSignalStrength >= 0 && gsmSignalStrength < 97){
//                //从无到有
//                LogUtils.i("control model go to sleep");
//                if(inPreNoSignal) {
//                    inPreNoSignal = false;
//                    inPreSignal = true;
//                    //要对它进行休眠
//                    //1代表已经休眠 0代表正常可工作状态
//                    try {
//                        LogUtils.i("control model go to sleep");
//                        mLocalPcmSocketManager.sendCommand(PowerUtils.sleepCommand());
//                    } catch (RemoteException e) {
//                        e.printStackTrace();
//                    }
//                }//从有到有
//            }else{
//                //从有到无
//                LogUtils.i("control model go to wakeup ");
//                if(inPreSignal) {
//                    inPreNoSignal = true;
//                    inPreSignal = false;
//                    LogUtils.i("signal loss do wakeup\n");
////                    if(!PowerControl.getTTStatus()) {
////                        PowerControl.doWakeup();
////                    }
//                }//从无到无
//            }
//    }

    public void release() {
        if (mAtCommandToolManager != null) {
            mAtCommandToolManager.free();
        }
    }
}
