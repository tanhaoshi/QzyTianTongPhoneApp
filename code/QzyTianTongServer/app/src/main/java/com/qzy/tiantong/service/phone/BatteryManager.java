package com.qzy.tiantong.service.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.qzy.tiantong.lib.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/9/21.
 */

public class BatteryManager {

    private Context mContext;

    private onBatteryListenr callback;

    public BatteryManager(Context context,onBatteryListenr listenr) {
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

    public void free() {
        mContext.unregisterReceiver(mBatInfoReveiver);
    }

    public interface onBatteryListenr {
        void onBattery(int level, int scal);
    }

}
