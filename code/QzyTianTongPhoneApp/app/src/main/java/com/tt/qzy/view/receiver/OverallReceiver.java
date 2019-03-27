package com.tt.qzy.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.qzy.tt.phone.service.TtPhoneService;
import com.socks.library.KLog;
import com.tt.qzy.view.evenbus.MainFragmentEvenbus;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.NetworkUtil;


/**
 * Created by qzy009 on 2018/8/28.
 */

public class OverallReceiver extends BroadcastReceiver {

    public static final String CLEAR_RECENTS = "com.android.systemui.recents.action.CLEAR_RECENT_TASKS";

    @Override
    public void onReceive(Context context, Intent intent) {
        KLog.i("action = " + intent.getAction());
        switch (intent.getAction()){
            //wifi的处理
            case CLEAR_RECENTS:
                Intent intent1 = new Intent(context, TtPhoneService.class);
                context.startService(intent1);
                break;
        }
    }

}
