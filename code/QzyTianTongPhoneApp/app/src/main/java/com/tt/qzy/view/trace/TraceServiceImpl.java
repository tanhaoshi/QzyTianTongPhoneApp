package com.tt.qzy.view.trace;

import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.qzy.tt.phone.service.TtPhoneService;
import com.qzy.utils.LogUtils;
import com.socks.library.KLog;
import com.tt.qzy.view.utils.AppUtils;
import com.tt.qzy.view.utils.Constans;
import com.tt.qzy.view.utils.SPUtils;
import com.tt.qzy.view.utils.TimeTask;
import com.xdandroid.hellodaemon.AbsWorkService;

import java.util.TimerTask;

import io.reactivex.disposables.Disposable;


public class TraceServiceImpl extends AbsWorkService {

    public static boolean sShouldStopService;
    public static Disposable sDisposable;

    public static void stopService() {
        sShouldStopService = true;
        if (sDisposable != null) sDisposable.dispose();
        cancelJobAlarmSub();
    }

    public TraceServiceImpl() {
    }

    @Override
    public Boolean shouldStopService(Intent intent, int flags, int startId) {
        return sShouldStopService;
    }

    private TimeTask mTimeTask;

    @Override
    public void startWork(Intent intent, int flags, int startId) {
        if(mTimeTask == null){
            mTimeTask = new TimeTask(3000, new TimerTask() {
                @Override
                public void run() {
                    timerSendMessage();
                }
            });
            mTimeTask.start();
        }
    }

    private void timerSendMessage(){
        if(AppUtils.isServiceRunning("com.qzy.tt.phone.service.TtPhoneService",this)){
            // alive
        }else{
            // dead
           /* Intent intent = new Intent(this, TtPhoneService.class);
            startService(intent);
            SPUtils.putShare(this, Constans.SERVER_FLAG,true);*/
        }
    }

    @Override
    public void stopWork(Intent intent, int flags, int startId) {
        stopService();
    }

    @Override
    public Boolean isWorkRunning(Intent intent, int flags, int startId) {
        return sDisposable != null && !sDisposable.isDisposed();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent, Void alwaysNull) {
        return null;
    }

    @Override
    public void onServiceKilled(Intent rootIntent) {
    }
}
