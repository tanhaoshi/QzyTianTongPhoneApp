package com.qzy.tiantong.service.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;

import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.utils.WifiUtils;
import com.qzy.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class QzyPhoneManager {

    private static final int NETWORKTYPE_WIFI = 0;
    private static final int NETWORKTYPE_4G = 1;
    private static final int NETWORKTYPE_2G = 2;
    private static final int NETWORKTYPE_NONE = 3;


    private Context mContext;
    private ITianTongServer mServer;

    public QzyPhoneManager(Context context, ITianTongServer server) {
        mContext = context;
        mServer = server;

        //打开WiFi
        WifiUtils.setWifiApEnabled(context,true);

        setPhoneListener();
    }

    private void setPhoneListener(){
        TelephonyManager tManager = (TelephonyManager)mContext.getSystemService(Service.TELEPHONY_SERVICE);
        tManager.listen(new MyTelephoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        tManager.listen(new PhoneSignalStatListener(),PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

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

    /**
     * 打电话
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
     * @param
     */
    public void hangupPhone() {

    }

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
            int netWorkType = getNetWorkType(mContext);
            switch (netWorkType) {
                case NETWORKTYPE_WIFI:
                    LogUtils.e("network type wifi,signaleS：" + gsmSignalStrength);
                    break;
                case NETWORKTYPE_2G:
                    LogUtils.e("network type 2G,signaleS：" + gsmSignalStrength);
                    break;
                case NETWORKTYPE_4G:
                    LogUtils.e("network type 4G,signaleS：" + gsmSignalStrength);
                    break;
                case NETWORKTYPE_NONE:
                    LogUtils.e("network type none,signaleS：" + gsmSignalStrength);
                    break;
                case -1:
                    LogUtils.e("network type -1,signaleS：" + gsmSignalStrength);
                    break;
            }
        }
    }


    public static int getNetWorkType(Context context) {
        int mNetWorkType = -1;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                return isFastMobileNetwork(context) ? NETWORKTYPE_4G : NETWORKTYPE_2G;
            }
        } else {
            mNetWorkType = NETWORKTYPE_NONE;//没有网络
        }
        return mNetWorkType;
    }

    /**判断网络类型*/
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
            //这里只简单区分两种类型网络，认为4G网络为快速，但最终还需要参考信号值
            return true;
        }
        return false;
    }


}
