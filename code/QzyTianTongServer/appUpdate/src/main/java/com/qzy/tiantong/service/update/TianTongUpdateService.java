package com.qzy.tiantong.service.update;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qzy.tiantong.lib.utils.LogUtils;

public class TianTongUpdateService extends Service {

    private UpdateServiceManager mUpdateServiceManager;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("onCreate");
        mUpdateServiceManager = new UpdateServiceManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        LogUtils.e("onStartCommand");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onStartCommand");

        if (mUpdateServiceManager != null) {
            mUpdateServiceManager.free();
        }

        Intent intent = new Intent(this, TianTongUpdateService.class);
        startService(intent);

    }

}
