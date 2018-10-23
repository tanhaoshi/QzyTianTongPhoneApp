package com.qzy.tiantong.service.boot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.service.TianTongService;

/**
 * Created by yj.zhang on 2018/7/9/009.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.e("BootReceiver start tiantong service...");
        context.startService(new Intent(context.getApplicationContext(),TianTongService.class));
    }


}
