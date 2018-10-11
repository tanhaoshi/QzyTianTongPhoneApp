package com.qzy.tiantong.service.ttpcm.aidl;

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

public class ReadPcmManager {

    private static ReadPcmManager manager;

    private IPcmManager iPcmManagerRead;

    private Context mContext;

    /**
     * 使用必须先 init
     *
     * @param context
     * @return
     */
    public static ReadPcmManager init(Context context) {
        if (manager == null) {
            manager = new ReadPcmManager(context);
        }
        return manager;
    }

    public static ReadPcmManager getInstance() {
        return manager;
    }

    private ReadPcmManager(Context context) {
        mContext = context;
        initBinderPcmRead();
    }


    public void free() {
        unBindReadService();
        manager = null;
    }

    public boolean start() {
        try {
            iPcmManagerRead.start();
            // readThread = new Thread(mReadThread);
            //readThread.start();
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void stop() {
        try {
            iPcmManagerRead.stop();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 开启底层读
     */
    private void initBinderPcmRead() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.qzy.tiantong.read", "com.qzy.tiantong.service.ReadService"));
        mContext.bindService(intent, mReadConnection, Context.BIND_AUTO_CREATE);
    }

    /**
     * 连接read service 服务
     */
    private ServiceConnection mReadConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogUtils.d("connect read service success...");
            iPcmManagerRead = IPcmManager.Stub.asInterface(iBinder);

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            LogUtils.e("connect read service failed...");
            iPcmManagerRead = null;

        }
    };


    /**
     * 解绑read服务
     */
    public void unBindReadService() {
        if (mReadConnection != null) {
            if (iPcmManagerRead != null) {
                try {
                    iPcmManagerRead.stop();
                   /* if (readThread != null && readThread.isAlive()) {
                        readThread.interrupt();
                    }
                    readThread = null;*/
                    iPcmManagerRead = null;
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            mContext.unbindService(mReadConnection);
        }
    }

    public IPcmManager getiPcmManagerRead() {
        return iPcmManagerRead;
    }

}
