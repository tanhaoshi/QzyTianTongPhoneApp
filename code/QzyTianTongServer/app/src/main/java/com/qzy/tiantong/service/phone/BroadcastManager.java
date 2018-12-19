package com.qzy.tiantong.service.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.service.ITianTongServer;
import com.qzy.tiantong.service.utils.Constant;
import com.qzy.tiantong.service.utils.LedManager;
import com.qzy.tiantong.service.utils.PhoneUtils;
import com.qzy.tiantong.service.utils.WifiUtils;


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
        intentFilter.addAction("com.qzy.tt.ACTION_RECOVERY_WIFI"); //恢复出厂设置
        intentFilter.addAction(PhoneUtils.actionstrDelCallback);
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
                       /* if(mHandler.hasMessages(1)){
                            mHandler.removeMessages(1);
                        }*/
                        mServer.initTtPcmDevice();
                        mServer.onPhoneStateChange(TtPhoneState.CALL);
                    } else if (phoneState.equals("0")) {
                        //关闭设备
                        // mServer.closeRecorderAndPlayer();
                        /*if(mHandler.hasMessages(1)){
                            mHandler.removeMessages(1);
                        }*/
                        mServer.freeTtPcmDevice();
                        mServer.onPhoneStateChange(TtPhoneState.NOCALL);
                    }else if (phoneState.equals("5")) {
                        //mHandler.sendEmptyMessageDelayed(1,40 * 1000);
                       // mServer.onPhoneStateChange(TtPhoneState.RING);
                    }

                }

            } else if (action.equals("com.test.close")) {
                //关闭设备
               // mServer.closeRecorderAndPlayer();
                mServer.freeTtPcmDevice();

            }else if(action.equals(PhoneUtils.actionstrDelCallback)){

            }else if(action.equals("com.qzy.tt.ACTION_RECOVERY_WIFI")){
                LedManager.setRecoveryLedStatus(true);
                setWifiPasswd(Constant.WIFI_PASSWD);
                doMasterClear(context);
            }
        }
    };

    /**
     * 设置wifi密码
     */
    public void setWifiPasswd(String passwd) {
        WifiUtils.setWifiPasswdToSharedpref(mContext, passwd);
        WifiUtils.setWifiApEnabled(mContext, WifiUtils.getSsidName(), passwd, false);
        WifiUtils.setWifiApEnabled(mContext, WifiUtils.getSsidName(), passwd, true);
    }

    /**
     * 恢复出厂设置
     *
     * @param context
     */
    public void doMasterClear(Context context) {
        LogUtils.i("the system recover");
        Intent intent = new Intent("android.intent.action.MASTER_CLEAR");
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
        intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", false);
        context.sendBroadcast(intent);
        // Intent handling is asynchronous -- assume it will happen soon.
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    mServer.initTtPcmDevice();
                    break;
            }
        }
    };


    public void release(){
        mContext.unregisterReceiver(mReceiver);
    }


}
