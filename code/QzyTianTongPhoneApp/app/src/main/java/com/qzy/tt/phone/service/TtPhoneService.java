package com.qzy.tt.phone.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
        startNotifaction();
    }

    //8.0 启动服务必须调用
    private void startNotifaction(){
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel("1", "channel_name",
                    NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(getApplicationContext(), "1").build();

            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPhoneServiceManager != null){
            mPhoneServiceManager.relese();
        }
    }


    /**
     * 启动服务
     * @param context
     */
    public static void startPhoneService(Context context){
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(new Intent(context, TtPhoneService.class));
        } else {
            // Pre-O behavior.
            context.startService(new Intent(context, TtPhoneService.class));
        }
    }
}
