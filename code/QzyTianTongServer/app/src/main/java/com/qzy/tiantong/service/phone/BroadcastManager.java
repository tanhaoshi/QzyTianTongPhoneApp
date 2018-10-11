package com.qzy.tiantong.service.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.service.ITianTongServer;


/**
 * Created by yj.zhang on 2018/8/3/003.
 */

public class BroadcastManager {

    private Context mContext;
    private ITianTongServer mServer;

    private String lastPhoneState = "0";

    public BroadcastManager(Context context, ITianTongServer server) {
        mContext = context;
        mServer = server;
    }

    /**
     * 注册广播接收器
     */
    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.test.incoming");
        intentFilter.addAction("com.test");
        intentFilter.addAction("com.test.close");
        intentFilter.addAction("com.qzy.phone.state");
        mContext.registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d("action = " + action);
            if (action.equals("com.test.incoming")) {
                String number = intent.getStringExtra("phone_number");
                if(!TextUtils.isEmpty(number)){
                    mServer.onPhoneIncoming(TtPhoneState.INCOMING, number);
                }

            } else if (action.equals("com.test")) {
                //打开设备
                //mServer.startRecorder();
                //mServer.startPlayer();
                mServer.initTtPcmDevice();

            } else if (action.equals("com.qzy.phone.state")) {
                String phoneState = intent.getStringExtra("phone_state");
                LogUtils.d("phoneState = " + phoneState);
                if(!phoneState.equals(lastPhoneState) ) {
                    lastPhoneState = phoneState;
                    if (phoneState.equals("2")) {
                        //mServer.startRecorder();
                        //mServer.startPlayer();
                        mServer.initTtPcmDevice();
                        mServer.onPhoneStateChange(TtPhoneState.CALL);
                    } else if (phoneState.equals("0")) {
                        //关闭设备
                        // mServer.closeRecorderAndPlayer();
                        mServer.freeTtPcmDevice();
                        mServer.onPhoneStateChange(TtPhoneState.NOCALL);
                    }

                }

            } else if (action.equals("com.test.close")) {
                //关闭设备
               // mServer.closeRecorderAndPlayer();
                mServer.freeTtPcmDevice();
            }

        }
    };


    public void release(){
        mContext.unregisterReceiver(mReceiver);
    }


}
