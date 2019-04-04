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
import com.qzy.tiantong.service.netty.cmd.TianTongHandler;
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

    //计时器
    private CountDownTimer countDownTimer;

    private long duration = 30 * 1000;


    public SystemSleepManager(Context context, ITianTongServer server) {
        this.mLocalPcmSocketManager = server.getLocalSocketManager();
        this.mPhoneNettyManager = server.getPhoneNettyManager();
        mContext = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
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

    /**
     * 控制系统休眠
     */
    public void controlSystemSleep() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LogUtils.i("ontick " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                controlSleep();
            }
        };
        countDownTimer.start();
    }

    /**
     * 休眠逻辑判断
     */
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
            boolean isGpsOpne = isGpsState();

            if (isCalling || isSos || isGpsOpne) {
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
     * 先让模块休眠  再然系统休眠
     */
    private void doSleeep() {
        try {

            LogUtils.i("control system go to sleep start = " + isTtSleep);

            sleepTianTong();

            checkTiantongSleepState();
            LogUtils.i("control system go to sleep end  = " + isTtSleep);

            if (isTtSleep) {
                gotoSleep();
            } else {
                CountDownTimer timers = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long l) {

                    }

                    @Override
                    public void onFinish() {
                        controlSleep();
                    }
                };
                timers.start();
            }


        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    /**
     * 系统休眠接口
     */
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

    /**
     * gps 是否打开
     *
     * @return
     */
    private boolean isGpsState() {
        return mPhoneNettyManager.getmGpsManager().getmCurrenLocation() == null ? false : true;
    }

    /**
     * 发送天通模块休眠
     */
    public void sleepTianTong() {
        try {
            LogUtils.i("control model go to sleep");
            mLocalPcmSocketManager.sendCommand(PowerUtils.sleepCommand());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 唤醒天通模块
     */
    public void wakeupTianTong() {
        try {
            LogUtils.i("control model go to wakeup ");
            mLocalPcmSocketManager.sendCommand(PowerUtils.wakeupCommand());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    /**
     * 检查天通休眠状态
     */
    public void checkTiantongSleepState() {
        try {
            LogUtils.i("check model  sleep state");
            mLocalPcmSocketManager.sendCommand(PowerUtils.checkTtModeCommand());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    /**
     * 信号控制模块休眠
     */
    private boolean inPreSignal = false;
    private boolean inPreNoSignal = true;

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.O)
    public synchronized void controlSignalStrength(int gsmSignalStrength) {
        // 入网
        if (gsmSignalStrength >= 0 && gsmSignalStrength < 97) {
            //从无到有
            LogUtils.i("control model go to sleep");
            if (inPreNoSignal) {
                inPreNoSignal = false;
                inPreSignal = true;
                //要对它进行休眠
                //1代表已经休眠 0代表正常可工作状态
                sleepTianTong();
            }//从有到有
        } else {
            //从有到无
            //LogUtils.i("control model go to wakeup ");
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
