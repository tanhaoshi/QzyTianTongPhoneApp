package com.qzy.tiantong.service.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tt.data.TtPhoneBatteryProtos;


/**
 * Created by yj.zhang on 2018/9/21.
 */

public class QzyBatteryManager {

    private Context mContext;

    private onBatteryListenr callback;



    public QzyBatteryManager(Context context, onBatteryListenr listenr) {
        mContext = context;
        callback  = listenr;

        init();
    }

    private void init() {
        mContext.registerReceiver(mBatInfoReveiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver mBatInfoReveiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (intent.ACTION_BATTERY_CHANGED.equals(action)) {
                int level = intent.getIntExtra("level", 0);
                int scale = intent.getIntExtra("scale", 100);
                LogUtils.d("level = " + level + " scale =" + scale);
                if(callback != null){
                    callback.onBattery(level,scale);
                }
            }
        }
    };

    public void getBattery(){
        BatteryManager batteryManager = (BatteryManager)mContext.getSystemService(Context.BATTERY_SERVICE);
        int battery = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        if(callback != null){
            callback.onBattery(battery,100);
        }
    }

    public void free() {
        mContext.unregisterReceiver(mBatInfoReveiver);
    }

    public interface onBatteryListenr {
        void onBattery(int level, int scal);
    }

}
