package com.qzy.tiantong.service.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.utils.TtUpdateUtils;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;

public class TtUsbManager {

    private Context mContext;


    //服务端netty管理工具
    private NettyServerManager mNettyServerManager;

    public TtUsbManager(Context context, NettyServerManager manager) {
        mContext = context;
        mNettyServerManager = manager;

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qzy.test.usb.open");
        intentFilter.addAction("com.qzy.test.usb.close");
        mContext.registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 设置model
     *
     * @param ttOpenBeiDou
     */
    public void parseUsbModel(TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou) {
        if (ttOpenBeiDou == null) {
            LogUtils.e("ttOpenBeiDou is null ....");
            return;
        }

        boolean isOpen = ttOpenBeiDou.getIsOpen();
        LogUtils.e("isOpen = " + isOpen);
        if (isOpen) {
            boolean state = TtUpdateUtils.openTtUpdateMode();
            sendUsbModelToPhoneClient();
            return;
        }

        boolean state = TtUpdateUtils.closeTtUpdateMode();
        sendUsbModelToPhoneClient();
    }


    public void sendUsbModelToPhoneClient() {

        if (mNettyServerManager == null) {
            LogUtils.e("mNettyServerManager is null ...");
            return;
        }

        int mode = TtUpdateUtils.readTtUpdateMode();
        TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = TtOpenBeiDouProtos.TtOpenBeiDou.newBuilder()
                .setIsOpen(mode == 1 ? true : false)
                .setResponseStatus(true)
                .build();
        mNettyServerManager.sendData(null, PhoneCmd.getPhoneCmd(PrototocalTools.IProtoClientIndex.tt_phone_beidoustatus_usb, ttOpenBeiDou));
    }


    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.e("action = " + action);
            if (action.equals("com.qzy.test.usb.open")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TtUpdateUtils.openTtUpdateMode();
                    }
                }).start();

            } else if (action.equals("com.qzy.test.usb.close")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        TtUpdateUtils.closeTtUpdateMode();
                    }
                }).start();

            }

        }
    };


}
