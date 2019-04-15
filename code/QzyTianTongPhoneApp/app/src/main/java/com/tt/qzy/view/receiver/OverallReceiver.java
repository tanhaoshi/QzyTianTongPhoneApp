package com.tt.qzy.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.qzy.tt.phone.data.TtPhoneDataManager;
import com.qzy.tt.phone.service.TtPhoneService;
import com.socks.library.KLog;


/**
 * Created by qzy009 on 2018/8/28.
 */

public class OverallReceiver extends BroadcastReceiver {

    public static final String CLEAR_RECENTS = "com.android.systemui.recents.action.CLEAR_RECENT_TASKS";

    public static final String CLEAR_TELL_PHONE_ACTIVITY = "com.qzy.clear_tell_phone.activity";

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            //wifi的处理
            case CLEAR_RECENTS:
                Intent intent1 = new Intent(context, TtPhoneService.class);
                context.startService(intent1);
                break;
            case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                wifiState(intent,context);
                break;
        }
    }

    private void wifiState(Intent intent,Context context){
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
            TtPhoneDataManager.getInstance().disconnectTtPhoneServer();
            Intent finshIntent = new Intent(CLEAR_TELL_PHONE_ACTIVITY);
            context.sendBroadcast(finshIntent);
        } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
//            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//            if(Constans.STANDARD_WIFI_NAME.equals(wifiInfo.getSSID().toString().substring(1,6))){
//            }
        }
    }
}
