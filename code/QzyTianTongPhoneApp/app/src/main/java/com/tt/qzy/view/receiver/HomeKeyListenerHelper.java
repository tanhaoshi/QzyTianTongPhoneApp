package com.tt.qzy.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class HomeKeyListenerHelper {

    private Context context;
    private BroadcastReceiver receiver;

    public static final String CLEAR_RECENTS = "com.android.systemui.recents.action.CLEAR_RECENT_TASKS";

    public HomeKeyListenerHelper(Context ctx) {
        context = ctx;
    }

    public void registerHomeKeyListener(HomeKeyListener l) {
        if (null != context) {
            registerListener(l);
        }
    }

    private void registerListener(HomeKeyListener l) {
        receiver = new HomeKeyEventBroadcastReceiver(l);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction(CLEAR_RECENTS);
        context.registerReceiver(receiver, intentFilter);
    }

    public void unregisterHomeKeyListener() {
        if (null != context && null != receiver) {
            context.unregisterReceiver(receiver);
        }
    }

    public interface HomeKeyListener {
        public void onHomeKeyShortPressed();
        public void onHomeKeyLongPressed();
    }

}
