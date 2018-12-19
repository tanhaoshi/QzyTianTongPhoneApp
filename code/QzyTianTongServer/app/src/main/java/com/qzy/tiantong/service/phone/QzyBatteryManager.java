package com.qzy.tiantong.service.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.utils.LedManager;
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
            int level = 0;
            int scale = 100;
            String action = intent.getAction();
            if (intent.ACTION_BATTERY_CHANGED.equals(action)) {
                    level = intent.getIntExtra("level", 0);
                    scale = intent.getIntExtra("scale", 100);
                LogUtils.d("level = " + level + " scale =" + scale);
                if(callback != null){
                    callback.onBattery(level,scale);
                }
            }
            // 1.总共几种状态.
            //   1.当前处于充电  2.当前处于充电且充电充满 3.当前没有充电且电量没有满.4.电量低于20
            //   5.发sos情况下
//            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
//            LogUtils.i("status = " + LedManager.get32BitBinString(status));
//            //如果sos打开了 全部关闭 蓝灯闪 红灯关闭
//
//            //当前处于充电状态，且充电没有充满
//            if(status == BatteryManager.BATTERY_STATUS_CHARGING ){
//                //亮红灯
//                //关蓝灯
//                LedManager.setandCleanLedFlag(LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH,
//                        LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH
//                        | LedManager.FLAG_BATTERY_LOW_LED_TIMER);
//            //当前处于充电状态，且电量充满
//            }else if(status == BatteryManager.BATTERY_STATUS_FULL){
//                //亮蓝灯
//                //关红灯
//                LedManager.setandCleanLedFlag(LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH,
//                        LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH
//                        | LedManager.FLAG_BATTERY_FULL_BLUE_LED_TIMER);
//            //当前处于不是充电
//            } else if( level*100 / scale < 21){
//                //闪红灯
//                //关蓝灯
//                LedManager.setandCleanLedFlag(LedManager.FLAG_BATTERY_LOW_LED_TIMER
//                        | LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH,
//                        LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH);
//            }else{
//                //红灯蓝灯一起关闭
//                LedManager.setandCleanLedFlag(0,LedManager.FLAG_BATTERY_FULL_BLUE_LED_SWITCH
//                    | LedManager.FLAG_BATTERY_LOW_RED_LED_SWITCH);
//            }
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
