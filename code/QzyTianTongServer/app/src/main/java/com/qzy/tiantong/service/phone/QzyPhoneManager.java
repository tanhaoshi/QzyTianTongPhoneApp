package com.qzy.tiantong.service.phone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.utils.WifiUtils;
import com.qzy.utils.LogUtils;

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
        WifiUtils.setWifiApEnabled(context,true);

        setPhoneListener();
    }

    private void setPhoneListener(){
        TelephonyManager tManager = (TelephonyManager)mContext.getSystemService(Service.TELEPHONY_SERVICE);
        tManager.listen(new MyTelephoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
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

}
