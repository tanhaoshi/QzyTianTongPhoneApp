package com.qzy.tiantong.service.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qzy.tiantong.lib.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/7/9/009.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("BootReceiver start tiantong update service...");
        context.startService(new Intent(context.getApplicationContext(), TianTongUpdateService.class));
    }


}
