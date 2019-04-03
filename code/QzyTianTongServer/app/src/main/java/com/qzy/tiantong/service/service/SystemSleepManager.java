package com.qzy.tiantong.service.service;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;

import com.qzy.tiantong.lib.localsocket.LocalPcmSocketManager;
import com.qzy.tiantong.lib.power.PowerUtils;
import com.qzy.tiantong.lib.utils.ByteUtils;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.PhoneNettyManager;
import com.qzy.tiantong.service.utils.TimeTask;
import com.qzy.tt.data.CallPhoneStateProtos;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;
import java.util.TimerTask;

public final class SystemSleepManager {

    private LocalPcmSocketManager mLocalPcmSocketManager;
    private PhoneNettyManager mPhoneNettyManager;


    private boolean isTtSleep = false;

    private Context mContext;

    private CountDownTimer countDownTimer;

    public SystemSleepManager(Context context, PhoneNettyManager phoneNettyManager, LocalPcmSocketManager localPcmSocketManager) {
        this.mLocalPcmSocketManager = localPcmSocketManager;
        this.mPhoneNettyManager = phoneNettyManager;
        mContext = context;
        controlSystemSleep();

        mLocalPcmSocketManager.setSocketCallback(new LocalPcmSocketManager.ISocketCallback() {
            @Override
            public void connected() {

            }

            @Override
            public void disconneted() {

            }

            @Override
            public void onData(byte[] data) {
                if (data[0] == (byte) 0x55 && data[1] == (byte) 0xaa && data[2] == (byte) 0x02 && data[3] == (byte) 0x06) {
                    isTtSleep = false;
                    if (data[4] == (byte) 0x01) {
                        isTtSleep = true;
                    }

                }
            }
        });


    }

    private long duration = 30 * 1000;

    public void controlSystemSleep() {

        if(countDownTimer != null){
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LogUtils.i("ontick " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                mHandler.sendEmptyMessage(1);
            }
        };
        countDownTimer.start();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    LogUtils.i("handleMessage 1111 " );
                    controlSleep();
                    break;

            }
        }
    };

    private void controlSleep() {
        try {
            if (!checkCurrentSingnalValue()) {
                //LogUtils.i("checkCurrentSingnalValue");
                controlSystemSleep();
                return;
            }
            if (checkPhoneStateImcoming()) {
                //.i("checkPhoneStateImcoming");
                controlSystemSleep();
                return;
            }

            boolean isCalling = checkPhoneStateCalling();
            boolean isSos = isSosState();

            if (isCalling || isSos) {
                LogUtils.i("checkPhoneStateCalling()  = " + isCalling + "  isSosState " + isSos);
                controlSystemSleep();
                return;
            }
            doSleeep();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 实际休眠处理
     */
    public void doSleeep() {
        try {

            LogUtils.i("control system go to sleep start = " + isTtSleep);
            //控制天通休眠
            // controlSignalStrength(mPhoneNettyManager.currentSignalValue);

            mLocalPcmSocketManager.sendCommand(PowerUtils.sleepCommand());


//            mLocalPcmSocketManager.sendCommand(PowerUtils.checkTtModeCommand());


            LogUtils.i("control system go to sleep end  = " + isTtSleep);

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //if (isTtSleep) {
                        LogUtils.i("gotoSleep  = " + isTtSleep);
                        gotoSleep();
                   // }

                }
            }, 20 * 1000);


        } catch (RemoteException e) {

            e.printStackTrace();
        }
    }


    private void gotoSleep() {
        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        try {
            powerManager.getClass().getMethod("goToSleep", new Class[]{long.class}).invoke(powerManager, SystemClock.uptimeMillis());
            LogUtils.i("control system go to sleep successddd ....  = " + isTtSleep);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查信号强度判断是否入网
     *
     * @return
     */
    private boolean checkCurrentSingnalValue() {
        if (mPhoneNettyManager.currentSignalValue >= 0 && mPhoneNettyManager.currentSignalValue < 97) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 检查电话状态
     *
     * @return
     */
    private boolean checkPhoneStateCalling() {
        if (mPhoneNettyManager.currentPhoneState != null) {
            if (mPhoneNettyManager.currentPhoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.HUANGUP ||
                    mPhoneNettyManager.currentPhoneState == CallPhoneStateProtos.CallPhoneState.PhoneState.NOCALL) {
                return false;

            } else {
                //如果有电话进来了. //或者来电话了. //或者正在通话中.
                return true;
            }
        } else {

            return false;
        }
    }

    /**
     * 检查底层来电
     *
     * @return
     */
    private boolean checkPhoneStateImcoming() {
        return mPhoneNettyManager.getmSmsPhoneManager().isKeyF2Incoming;
    }


    /**
     * sos 是否开启
     *
     * @return
     */
    private boolean isSosState() {
        return !mPhoneNettyManager.getmSmsPhoneManager().isThread;
    }

    private boolean inPreSignal = false;
    private boolean inPreNoSignal = true;

    private synchronized void controlSignalStrength(int gsmSignalStrength) {
        // 入网
        if (gsmSignalStrength >= 0 && gsmSignalStrength < 97) {
            //从无到有
            LogUtils.i("control model go to sleep");
            if (inPreNoSignal) {
                inPreNoSignal = false;
                inPreSignal = true;
                //要对它进行休眠
                //1代表已经休眠 0代表正常可工作状态
                try {
                    LogUtils.i("control model go to sleep");
                    mLocalPcmSocketManager.sendCommand(PowerUtils.sleepCommand());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }//从有到有
        } else {
            //从有到无
            LogUtils.i("control model go to wakeup ");
            if (inPreSignal) {
                inPreNoSignal = true;
                inPreSignal = false;
                LogUtils.i("signal loss do wakeup\n");
//                    if(!PowerControl.getTTStatus()) {
//                        PowerControl.doWakeup();
//                    }
            }//从无到无
        }
    }


    /**
     * 释放
     */
    public void free() {

    }


}
