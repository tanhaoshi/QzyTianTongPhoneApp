package com.tt.qzy.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimerService extends Service {

    public TimerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void timerSendMessage(){

    }
}
