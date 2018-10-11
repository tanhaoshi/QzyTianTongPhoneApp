package com.qzy.tiantong.ttpcm.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.qzy.IPcmManager;
import com.qzy.tiantong.lib.utils.LogUtils;

/**
 * Created by yj.zhang on 2018/8/18.
 */

public class WritePcmManager {

    private static WritePcmManager manager;

    private IPcmManager iPcmManagerWrite;

    private Context mContext;

    /**
     * 使用必须先 init
     *
     * @param context
     * @return
     */
    public static WritePcmManager init(Context context) {
        if (manager == null) {
            manager = new WritePcmManager(context);
        }
        return manager;
    }

    public static WritePcmManager getInstance() {
        return manager;
    }

    private WritePcmManager(Context context) {
        mContext = context;
        initBinderPcmWrite();
    }


    public void free() {
        unBindWriteService();
        manager = null;
    }

    public boolean start() {
        try {
            iPcmManagerWrite.start();
               /* writeThread = new Thread(mWriteThread);
                writeThread.start();*/
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void stop() {
        try {
            iPcmManagerWrite.stop();

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开启底层write
     */
    private void initBinderPcmWrite() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.qzy.tiantong.write", "com.qzy.tiantong.lib.service.WriteService"));
        mContext.bindService(intent, mWriteConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 连接write service 服务
     */
    private ServiceConnection mWriteConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.d("connect write service success...");
            iPcmManagerWrite = IPcmManager.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.e("connect write service failed...");
            iPcmManagerWrite = null;

        }
    };


    /**
     * 解绑write服务
     */
    public void unBindWriteService() {
        if (mWriteConnection != null) {
            if (iPcmManagerWrite != null) {
                try {
                    iPcmManagerWrite.stop();
                   /* if (writeThread != null && writeThread.isAlive()) {
                        writeThread.interrupt();
                    }
                    writeThread = null;*/
                    iPcmManagerWrite = null;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mContext.unbindService(mWriteConnection);
        }
    }

    public IPcmManager getiPcmManagerWrite() {
        return iPcmManagerWrite;
    }

}
