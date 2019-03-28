package com.tt.qzy.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.socks.library.KLog;

public class HomeKeyEventBroadcastReceiver extends BroadcastReceiver{

    private static final String SYSTEM_EVENT_REASON = "reason";
    private static final String SYSTEM_HOME_KEY = "homekey";
    private static final String SYSTEM_RECENT_APPS = "recentapps";
    private static final String BUILD_NAME = "conquest";

    private static final String CLOSE_SYSTEM_DIALOGS = "android.intent.action.CLOSE_SYSTEM_DIALOGS";

    private HomeKeyListenerHelper.HomeKeyListener listener;

    public HomeKeyEventBroadcastReceiver(){

    }

    public HomeKeyEventBroadcastReceiver(HomeKeyListenerHelper.HomeKeyListener l) {
        listener = l;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent) {
            return;
        }
        String action = intent.getAction();
        KLog.i("action : = " + action);
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_EVENT_REASON);
            if (null != reason) {
                if (reason.equals(SYSTEM_HOME_KEY)) {
                    //Home key short pressed.
                    if (null != listener) {
                        listener.onHomeKeyShortPressed();
                    }
                } else if (reason.equals(SYSTEM_RECENT_APPS)) {
                    //Home key long pressed.
                    if (null != listener) {
                        listener.onHomeKeyLongPressed();
                    }
                }
            }
        }

        if(action.equals(CLOSE_SYSTEM_DIALOGS)){
            if(Build.BRAND.equals(BUILD_NAME)){
//
            }
        }
    }
}
