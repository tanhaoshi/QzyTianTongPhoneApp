package com.qzy.tt.phone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.socks.library.KLog;
import com.tt.qzy.view.activity.TellPhoneIncomingActivity;
import com.tt.qzy.view.utils.AppUtils;

public class TtPhoneService extends Service {

    private PhoneServiceManager mPhoneServiceManager;

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mPhoneServiceManager = new PhoneServiceManager(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.i("onDestroy");
        if(mPhoneServiceManager != null){
            mPhoneServiceManager.relese();
        }
    }
}
