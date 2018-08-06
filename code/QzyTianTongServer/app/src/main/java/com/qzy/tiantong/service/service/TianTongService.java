package com.qzy.tiantong.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.qzy.utils.LogUtils;

public class TianTongService extends Service {
    private TianTongServiceManager manager;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d("onCreate");
        manager = new TianTongServiceManager(getApplicationContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
        if(manager != null){
            manager.release();
        }
    }
}
