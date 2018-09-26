package com.tt.qzy.view.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.qzy.data.PhoneCmd;
import com.qzy.eventbus.EventBusUtils;
import com.qzy.eventbus.IMessageEventBustType;
import com.qzy.eventbus.MessageEventBus;
import com.qzy.tt.data.TtPhoneBatteryProtos;
import com.qzy.tt.data.TtPhoneSignalProtos;
import com.qzy.tt.phone.common.CommonData;
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


    public MainFragementPersenter(Context context) {
        mContext = context;
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
    public void startConnect() {

        if (CommonData.getInstance().isConnected()) {
            Intent intent = new Intent(mContext, UserEditorsActivity.class);
            ((Activity) mContext).startActivityForResult(intent, 99);
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
        String ssid = NetworkUtil.getConnectWifiSsid(mContext);
        if(TextUtils.isEmpty(ssid) || ssid.length() < 6 || !Constans.STANDARD_WIFI_NAME.equals(ssid.substring(1, 6))){
            NToast.shortToast(mContext, mContext.getString(R.string.TMT_connect_tiantong_please));
            Intent intent = new Intent("android.settings.WIFI_SETTINGS");
            mContext.startActivity(intent);
            return;
        }

        EventBusUtils.post(new MessageEventBus(IMessageEventBustType.EVENT_BUS_TYPE_CONNECT_TIANTONG));
    }

    /**
     * 获取天通信号强度
     *
     * @param obj
     * @return
     */
    public int getTianTongSignalValue(Object obj) {
        PhoneCmd cmd = (PhoneCmd) obj;
        TtPhoneSignalProtos.PhoneSignalStrength signalStrength = (TtPhoneSignalProtos.PhoneSignalStrength) cmd.getMessage();
        int value = signalStrength.getSignalStrength();
        return value;
    }

    /**
     * 获取电量level
     * @param obj
     * @return
     */
    public int getBatteryLevel(Object obj){
        PhoneCmd cmd = (PhoneCmd) obj;
        TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery = (TtPhoneBatteryProtos.TtPhoneBattery) cmd.getMessage();
        int level = ttPhoneBattery.getLevel();
        return level;
    }

    /**
     * 获取 sdcal
     * @param obj
     * @return
     */
    public int getBatteryScal(Object obj){
        PhoneCmd cmd = (PhoneCmd) obj;
        TtPhoneBatteryProtos.TtPhoneBattery ttPhoneBattery = (TtPhoneBatteryProtos.TtPhoneBattery) cmd.getMessage();
        int scal = ttPhoneBattery.getLevel();
        return scal;
    }


}
