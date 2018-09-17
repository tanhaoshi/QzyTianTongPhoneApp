package com.tt.qzy.view.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.UserEditorsActivity;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NToast;
import com.tt.qzy.view.utils.NetworkUtil;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class MainFragementPersenter {

    private Context mContext;

    //是否连接天通
    private boolean isConnected = false;

    public MainFragementPersenter(Context context){
        mContext = context;
    }

    /**
     * 获取天同连接状态
     * @return
     */
    public boolean isConnected() {
        return isConnected;
    }


    /**
     * 设置天通连接状态
     * @param connected
     */
    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * 查询连接状态
     */
    public void checkConnectedSate() {
        KLog.i("checkConnectedSate");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                    EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG_SELECTED));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * 连接天通
     */
    public void startConnect(){

        if(isConnected()){
            Intent intent = new Intent(mContext, UserEditorsActivity.class);
            ((Activity)mContext).startActivityForResult(intent, 99);
            return;
        }
        //先判断wifi开关是否打开 跳转至打开wifi界面
        if (!NetworkUtil.isWifiEnabled(mContext)) {
            WifiManager wfManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
            wfManager.setWifiEnabled(true);
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }

        //判断是否连接着天通指定的wifi
        if (!Constans.STANDARD_WIFI_NAME.equals(NetworkUtil.getConnectWifiSsid(mContext).substring(1, 6))) {
            NToast.shortToast(mContext, mContext.getString(R.string.TMT_connect_tiantong_please));
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }


        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG));
    }

    /**
     * 获取天通信号强度
     * @param obj
     * @return
     */
    public int getTianTongSignalValue(Object obj){
        PhoneCmd cmd = (PhoneCmd) obj;
        TtPhoneSignalProtos.PhoneSignalStrength signalStrength = (TtPhoneSignalProtos.PhoneSignalStrength) cmd.getMessage();
        int value = signalStrength.getSignalStrength();
        return value;
    }


}
