package com.qzy.tt.phone.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
    public void onDestroy() {
        super.onDestroy();

        if(mPhoneServiceManager != null){
            mPhoneServiceManager.relese();
        }
    }



}
