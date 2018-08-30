package com.tt.qzy.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.tt.qzy.view.evenbus.MainFragmentEvenbus;
import com.tt.qzy.view.utils.Constans;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by qzy009 on 2018/8/28.
 */

public class WifiReceiver extends BroadcastReceiver {

    private static final String TAG = "wifiReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        switch (intent.getAction()){
            //wifi的处理
            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                wifiState(intent,context);
                break;
        }
    }

    private void wifiState(Intent intent,Context context){
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
                EventBus.getDefault().post(new MainFragmentEvenbus(true,1));
            }
        }
    }

    private void batteryChanged(Intent intent){
        int current = intent.getExtras().getInt("level");// 获得当前电量
        int total = intent.getExtras().getInt("scale");// 获得总电量
        int percent = current * 100 / total;

        Log.i(getClass().getSimpleName().toString(),"当前手机电量:"+percent);
    }
}
