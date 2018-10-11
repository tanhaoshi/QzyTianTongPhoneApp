package com.qzy.tiantong.service.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.intercom.IntercomManager;
import com.qzy.tiantong.service.intercom.util.Constants;


public class PcmServices extends Service {

    public static String action_stop = "com.qzy.pcm.stop";
    public static String action_stop_pcm = "com.qzy.pcm.stop.pcm";
    public static String action_start_pcm = "com.qzy.pcm.start.pcm";
    public static String action_set_ip = "com.qzy.pcm.set_ip";
    public static String extra_ip = "extra_ip";

    private IntercomManager mIntercomManager;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("onCreate");


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(action_stop);
        intentFilter.addAction(action_stop_pcm);
        intentFilter.addAction(action_start_pcm);
        intentFilter.addAction(action_set_ip);
        registerReceiver(mReciver, intentFilter);
    }

    private void startDevice() {
        if (mIntercomManager == null) {
            mIntercomManager = new IntercomManager();
        }
    }

    private void stopDevice() {
        if (mIntercomManager != null) {
            mIntercomManager.release();
            mIntercomManager = null;
        }
    }


    private BroadcastReceiver mReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d("action = " + action);
            if (action_stop.equals(action)) {
                stopSelf();
            } else if (action_set_ip.equals(action)) {
                String ip = intent.getStringExtra(extra_ip);
                LogUtils.d("ip = " + ip);
                Constants.UNICAST_BROADCAST_IP = ip;
            } else if (action_stop_pcm.equals(action)) {
                stopDevice();
            } else if (action_start_pcm.equals(action)) {
                startDevice();
            }

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
        stopDevice();

        unregisterReceiver(mReciver);

        System.exit(0);

    }
}
