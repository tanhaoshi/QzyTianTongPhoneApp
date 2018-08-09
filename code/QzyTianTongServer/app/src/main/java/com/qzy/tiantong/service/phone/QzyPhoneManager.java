package com.qzy.tiantong.service.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tiantong.service.utils.WifiUtils;
import com.qzy.utils.LogUtils;

import java.lang.reflect.Method;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class QzyPhoneManager {

    private Context mContext;
    private ITianTongServer mServer;

    public QzyPhoneManager(Context context, ITianTongServer server) {
        mContext = context;
        mServer = server;

        //打开WiFi
        WifiUtils.setWifiApEnabled(context, true);

        setPhoneListener();
    }

    /**
     * 设置电话监听
     */
    private void setPhoneListener() {
        TelephonyManager tManager = (TelephonyManager) mContext.getSystemService(Service.TELEPHONY_SERVICE);
        tManager.listen(new MyTelephoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        tManager.listen(new PhoneSignalStatListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }


    /**
     * 打电话
     *
     * @param phoneNum
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        mContext.startActivity(intent);
    }

    /**
     * 挂断电话
     *
     * @param
     */
    public void hangupPhone() {
        endCall();
    }

    private void endCall() {
        // IBinder iBinder = ServiceManager.getService(TELEPHONY_SERVICE);
        // ServiceManager 是被系统隐藏掉了 所以只能用反射的方法获取
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            telephony.endCall();
            LogUtils.d("endcall .....");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void answerRingCall() {
        // IBinder iBinder = ServiceManager.getService(TELEPHONY_SERVICE);
        // ServiceManager 是被系统隐藏掉了 所以只能用反射的方法获取
        try {
            Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
            IBinder binder = (IBinder) method.invoke(null, new Object[]{Context.TELEPHONY_SERVICE});
            ITelephony telephony = ITelephony.Stub.asInterface(binder);
            telephony.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //电话状态监听
    public class MyTelephoneListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    //等待接听状态
                    String mIncomingNumber = incomingNumber;
                    LogUtils.e("=====RINGING :" + mIncomingNumber + "=========");
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    LogUtils.e("======get call======== ");
                    mServer.onPhoneStateChange(TtPhoneState.RING);
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
    }

    //电话信号强度监听
    private class PhoneSignalStatListener extends PhoneStateListener {
        //获取信号强度

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
                    mServer.onPhoneSignalStrengthChange(gsmSignalStrength);
                    break;
                case -1:
                    LogUtils.e("network type -1,signaleS = " + gsmSignalStrength);
                    break;
            }
        }
    }


}
