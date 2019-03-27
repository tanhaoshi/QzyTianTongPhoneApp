package com.qzy.tt.phone.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.socks.library.KLog;
import com.tt.qzy.view.R;
import com.tt.qzy.view.activity.TellPhoneIncomingActivity;
import com.tt.qzy.view.service.CancelNoticeService;
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
        acquireWakeLock();
        //如果API大于18，需要弹出一个可见通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.drawable.icon);
            builder.setContentTitle("KeepAppAlive");
            builder.setContentText("DaemonService is runing...");
            startForeground(CancelNoticeService.NOTICE_ID, builder.build());
            // 如果觉得常驻通知栏体验不好
            // 可以通过启动CancelNoticeService，将通知移除，oom_adj值不变
            Intent intent = new Intent(this, CancelNoticeService.class);
            startService(intent);
        } else {
            startForeground(CancelNoticeService.NOTICE_ID, new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPhoneServiceManager = new PhoneServiceManager(getApplicationContext());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        KLog.i("onDestroy");
        if(mPhoneServiceManager != null){
            mPhoneServiceManager.relese();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(CancelNoticeService.NOTICE_ID);
        }

        releaseWakeLock();

        // 重启自己
        Intent intent = new Intent(getApplicationContext(), TtPhoneService.class);
        startService(intent);
    }

    private PowerManager.WakeLock mWakeLock;

    private void acquireWakeLock() {
        if(mWakeLock == null) {
            PowerManager pm = (PowerManager)getApplicationContext().getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    this.getClass().getCanonicalName());
            mWakeLock.acquire();
        }
    }

    private void releaseWakeLock() {
        if(mWakeLock != null) {
            mWakeLock.release();
            mWakeLock = null;
        }
    }
}
