package com.qzy.tiantong.service.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.text.TextUtils;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.contants.QzyTtContants;
import com.qzy.tiantong.service.utils.WifiUtils;

public class TiantongWifiManager {

    private Context mContext;

    public TiantongWifiManager(Context context) {
        mContext = context;

        initWifi();

        registerReceiver();
    }

    private void initWifi() {
        String passwd = WifiUtils.getWifiPasswdToSharedpref(mContext);
        if (TextUtils.isEmpty(passwd)) {

            passwd = QzyTtContants.WIFI_PASSWD;
        }

        //打开WiFi
        WifiUtils.setWifiApEnabled(mContext, WifiUtils.getSsidName(), passwd, true);
    }


    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.qzy.tt.ACTION_RECOVERY_WIFI"); //清除WiFi密码
        intentFilter.addAction("com.qzy.tt.ACTION_CHANGE_WIFI"); //清除WiFi密码
        mContext.registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 广播接收器
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.d("action = " + action);
            if (action.equals("com.qzy.tt.ACTION_RECOVERY_WIFI")) {
                setWifiPasswd(QzyTtContants.WIFI_PASSWD);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                     doMasterClear(context);
                    }
                },3000);
            } else if (action.equals("com.qzy.tt.ACTION_CHANGE_WIFI")) {
                String passwd = intent.getStringExtra("passwd");
                setWifiPasswd(passwd);
            }

        }
    };


    /**
     * 设置wifi密码
     */
    public void setWifiPasswd(String passwd) {
        WifiUtils.setWifiPasswdToSharedpref(mContext, passwd);
        WifiUtils.setWifiApEnabled(mContext, WifiUtils.getSsidName(), passwd, false);
        WifiUtils.setWifiApEnabled(mContext, WifiUtils.getSsidName(), passwd, true);
    }

    /**
     * 恢复出厂设置
     *
     * @param context
     */
    public void doMasterClear(Context context) {
        Intent intent = new Intent("android.intent.action.MASTER_CLEAR");
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
        intent.putExtra("android.intent.extra.WIPE_EXTERNAL_STORAGE", false);
        context.sendBroadcast(intent);
        // Intent handling is asynchronous -- assume it will happen soon.
    }

    public void free() {
        mContext.unregisterReceiver(mReceiver);
    }


}
