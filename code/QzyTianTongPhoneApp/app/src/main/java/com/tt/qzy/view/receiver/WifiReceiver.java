package com.tt.qzy.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.tt.qzy.view.utils.Constans;

/**
 * Created by qzy009 on 2018/8/28.
 */

public class WifiReceiver extends BroadcastReceiver {

    private static final String TAG = "wifiReceiver";

    public WifiReceiver(){

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.RSSI_CHANGED_ACTION)) {
            Log.i(TAG, "wifi信号强度变化");
        }
        //wifi连接上与否
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {

            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
                Log.i(TAG, "wifi断开");
            } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                //获取当前wifi名称
                Log.i(TAG, "连接到网络 " + wifiInfo.getSSID());
                //当修改连接wifi情况下 去修改界面视图
                if(Constans.STANDARD_WIFI_NAME.equals(wifiInfo.getSSID().toString().substring(1,6))){
                }
            }
        }
        //wifi打开与否
        if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_DISABLED);
            if (wifistate == WifiManager.WIFI_STATE_DISABLED) {
                Log.i(TAG, "系统关闭wifi");
            } else if (wifistate == WifiManager.WIFI_STATE_ENABLED) {
                Log.i(TAG, "系统开启wifi");
            }
        }
    }
}
