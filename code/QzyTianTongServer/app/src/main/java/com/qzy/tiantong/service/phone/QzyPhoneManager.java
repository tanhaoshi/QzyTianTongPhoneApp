package com.qzy.tiantong.service.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.android.internal.telephony.ITelephony;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.contants.QzyTtContants;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tiantong.service.utils.WifiUtils;

import java.lang.reflect.Method;
import java.util.UUID;

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
        WifiUtils.setWifiApEnabled(context, getSsidName(), QzyTtContants.WIFI_PASSWD, true);

        setPhoneListener();
    }

    private String getSsidName() {
        String ssid = getSsidToSharedpref();
        if (TextUtils.isEmpty(ssid)) {
            ssid = QzyTtContants.WIFI_SSID + UUID.randomUUID().toString().substring(0, 6);
            setSsidToSharedpref(ssid);
        }
        return ssid;
    }

    private void setSsidToSharedpref(String ssid) {
        SharedPreferences sp = mContext.getSharedPreferences("tt_server_config", Context.MODE_PRIVATE);
        sp.edit().putString("wifi_ssid", ssid).commit();
    }

    private String getSsidToSharedpref() {
        SharedPreferences sp = mContext.getSharedPreferences("tt_server_config", Context.MODE_PRIVATE);
        String ssid = sp.getString("wifi_ssid", "");
        return ssid;
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
    public void callPhone(String ip, String phoneNum) {

        //设置电话ip
        if(!mServer.setCurrenCallingIp(ip)){
            LogUtils.e("has user calling pleas waiting...");
            return;
        }

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
    public void hangupPhone(String ip) {
        mServer.setEndCallingIp(ip);
        endCall();
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
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    //等待接听状态
                    String mIncomingNumber = incomingNumber;
                    LogUtils.e("=====RINGING :" + mIncomingNumber + "=========");
                    mServer.onPhoneIncoming(TtPhoneState.INCOMING, mIncomingNumber);
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
