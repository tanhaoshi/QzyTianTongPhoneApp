package com.qzy.tt.phone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.socks.library.KLog;

public class TtPhoneService extends Service {

    private PhoneServiceManager mPhoneServiceManager;

    @Override
    public IBinder onBind(Intent intent) {
       return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.i("onCreate");
        mPhoneServiceManager = new PhoneServiceManager(getApplicationContext());
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
