package com.qzy.tiantong.service.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import com.qzy.tiantong.lib.service.CancelNoticeService;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.R;
import com.qzy.tiantong.service.utils.CrashHandler;

import java.util.concurrent.ConcurrentHashMap;


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
        acquireWakeLock();

        manager = new TianTongServiceManager(this);

        //如果API大于18，需要弹出一个可见通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher);
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
    private PendingIntent restartIntent;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 如果Service被终止
        // 当资源允许情况下，重启service
        CrashHandler.CrashUploader crashUploader = new CrashHandler.CrashUploader() {

            @Override
            public void uploadCrashMessage(ConcurrentHashMap<String, Object> info) {
            }
        };
        //默認調用false false為我們自定義的捕捉異常
        CrashHandler.getInstance().init(this, crashUploader, restartIntent,false);

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d("onDestroy");
        if (manager != null) {
            manager.release();
        }

        // 如果Service被杀死，干掉通知
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            NotificationManager mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mManager.cancel(CancelNoticeService.NOTICE_ID);
        }
        LogUtils.d("TianTongService---->onDestroy，前台service被杀死");

        releaseWakeLock();

        // 重启自己
        Intent intent = new Intent(getApplicationContext(), TianTongService.class);
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
