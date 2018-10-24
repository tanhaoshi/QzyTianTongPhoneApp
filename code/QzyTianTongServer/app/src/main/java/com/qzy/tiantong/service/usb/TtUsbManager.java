package com.qzy.tiantong.service.usb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Keep;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.NettyServerManager;
import com.qzy.tiantong.service.utils.TtUpdateUtils;
import com.qzy.tt.data.TtOpenBeiDouProtos;
import com.qzy.tt.probuf.lib.data.PhoneCmd;
import com.qzy.tt.probuf.lib.data.PrototocalTools;
import com.qzy.usb.UsbTool;

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

        boolean returnState = new UsbTool().switchMode(isOpen);

        sendUsbModelToPhoneClient(returnState);
    }


    public void sendUsbModelToPhoneClient(boolean state) {

        if (mNettyServerManager == null) {
            LogUtils.e("mNettyServerManager is null ...");
            return;
        }
        LogUtils.e("send the message to the aplication layer  ");
        //int mode = TtUpdateUtils.readTtUpdateMode();
        TtOpenBeiDouProtos.TtOpenBeiDou ttOpenBeiDou = TtOpenBeiDouProtos.TtOpenBeiDou.newBuilder()
                .setIsOpen(state)
                .setResponseStatus(state)
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
