package com.qzy.tiantong.service.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.qzy.tiantong.lib.localsocket.LocalPcmSocketManager;
import com.qzy.tiantong.lib.power.PowerUtils;
import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.service.netty.PhoneNettyManager;
import com.qzy.tiantong.service.netudp.MultiServerSocket;
import com.qzy.tiantong.service.netudp.NetUdpThread;
import com.qzy.tiantong.service.phone.PhoneClientManager;
import com.qzy.tiantong.service.phone.data.ClientInfoBean;
import com.qzy.tiantong.service.utils.AppUtils;
import com.qzy.tiantong.service.utils.Constant;
import com.qzy.tiantong.service.utils.SPUtils;
import com.qzy.tt.data.CallPhoneStateProtos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//修改时间 4月13号 18：49
public final class SystemSleepManager {

    private static final String COM_QZY_SLEEP_RK = "com.qzy.sleepRK";

    private LocalPcmSocketManager mLocalPcmSocketManager;

    private PhoneNettyManager mPhoneNettyManager;

    private boolean isTtSleep = false;

    private Context mContext;

    //计时器
    private CountDownTimer countDownTimer;

    private long duration = 10 * 1000;

    private List<String> stringList = new ArrayList<>();

    public SystemSleepManager(Context context, ITianTongServer server) {
        this.mLocalPcmSocketManager = server.getLocalSocketManager();
        this.mPhoneNettyManager = server.getPhoneNettyManager();
        mContext = context;
        registerF12();
    }

    private void registerF12() {
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(COM_QZY_SLEEP_RK);
        mContext.registerReceiver(broadcastReceiver, intentFilter1);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            LogUtils.i("action value = " + action);
            if (action.equals(COM_QZY_SLEEP_RK)) {
                stringList.add(action);
                doSleeep();
            }
        }
    };

    /**
     * 控制系统休眠
     */
    public void controlSystemSleep() {

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        //程序初始化的时候将开启30秒的定时器
        countDownTimer = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LogUtils.i("ontick " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                controlSleep();
            }
        };
        countDownTimer.start();
    }

    /**
     * 检查当模块休眠状态条件
     */
    private void controlSleep() {
        try {
            if (!checkCurrentSingnalValue()) {
                controlSystemSleep();
                return;
            }

            if (checkPhoneStateImcoming()) {
                controlSystemSleep();
                return;
            }

            boolean isCalling = checkPhoneStateCalling();
            boolean isSos = isSosState();
            boolean isGpsOpen = isGpsState();

            LogUtils.i("controlSleep isCalling = " + isCalling + "  isSosState " + isSos + " isGpsOpen = " + isGpsOpen);

            if (isCalling || isSos || isGpsOpen) {
                //三者有一个存在 就将唤醒天通模块 然后继续跑定时器
                isTtSleep = getTianTongModeSleep();
                if (isTtSleep) {
                    wakeupTianTong();
                }
                controlSystemSleep();
                return;
            }//否者调用模块休眠
//            doSleeep();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 先让模块休眠  再然系统休眠
     */
    private void doSleeep() {

        LogUtils.i("broad cast receive list size = " + stringList.size());

        try {

//            sleepTianTong();

            if (!checkAllState()) {
                LogUtils.i("check all state can't sleep ...");
                return;
            }

            isTtSleep = getTianTongModeSleep();

            if (isTtSleep) {

                LogUtils.i("broad cast receive list size = " + stringList.size());

                LogUtils.i("tian tong system go to sleep ");

                if (mPhoneNettyManager.isGotoSleep) {
                    mPhoneNettyManager.isGotoSleep = false;
                    mPhoneNettyManager.stopTimer();

                    ConcurrentHashMap concurrentHashMap =
                            PhoneClientManager.getInstance().getmHaspMapPhoneClient();
                    AppUtils.requireNonNull(concurrentHashMap);
                    LogUtils.i("before sleep system to look at connect size = " +
                    concurrentHashMap.size());

                    SPUtils.putShare(mContext, Constant.CONNECT_COUNT,concurrentHashMap.size());

                    gotoSleep();
                }

            } else {

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
            synchronized (this) {
                LogUtils.e("GGGGGG control system go to sleep successddd ....  = " + isTtSleep);
                powerManager.getClass().getMethod("goToSleep", new Class[]{long.class}).invoke(powerManager, SystemClock.uptimeMillis());
                LogUtils.e("GGGGG control system go to sleep end ... = " + isTtSleep);
            }
        } catch (IllegalAccessException e) {
            LogUtils.e("IllegalAccessException error :" + e.getMessage().toString());
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            LogUtils.e("InvocationTargetException error :" + e.getMessage().toString());
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            LogUtils.e("NoSuchMethodException error :" + e.getMessage().toString());
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
        String callingIp = PhoneClientManager.getInstance().isCallingIp();

        if (!TextUtils.isEmpty(callingIp)) {
            LogUtils.e("calling ip = " + callingIp);
            return true;
        }
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
        if (mPhoneNettyManager.getmSmsPhoneManager().isKeyF2Incoming) {
            wakeupTianTong();
        }
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
        return mPhoneNettyManager.getmGpsManager().isGpsOpen;
    }

    /**
     * 发送天通模块休眠
     */
    public void sleepTianTong() {
        try {

            if (checkAllState()) {
                isTtSleep = getTianTongModeSleep();
                if (isTtSleep) {
                    return;
                }
                LogUtils.i("control model go to sleep start");
                mLocalPcmSocketManager.sendCommand(PowerUtils.sleepCommand());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查所有状态是否满足休眠条件
     *
     * @return
     */
    private boolean checkAllState() {
        boolean isCalling = checkPhoneStateCalling();
        boolean isSos = isSosState();
        boolean isGpsOpen = isGpsState();
        boolean isComingPhone = checkPhoneStateImcoming();

        LogUtils.i("controlSignalStrength isCalling = " + isCalling + "  isSosState " + isSos + " isGpsOpen = " + isGpsOpen + "isComingPhone = " + isComingPhone);
        //如果存在 退出 不进行模块休眠
        if (isCalling || isSos || isGpsOpen || isComingPhone) {
            return false;
        }

        return true;
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
     * 休眠断掉所有的客户端
     */
    public void disconnectAllClient() {
        ConcurrentHashMap<String, ClientInfoBean> clients = PhoneClientManager.getInstance().getmHaspMapPhoneClient();
        for (Map.Entry<String, ClientInfoBean> entry : clients.entrySet()) {
            if (entry.getValue() != null && entry.getValue().getCtx() != null) {
                entry.getValue().getCtx().close();
            }
        }
        clients.clear();
    }

    /**
     * 唤醒广播通知所有客户端重新连接
     */

    public void callConnectAllClient() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(1000);
//                    new NetUdpThread(8991).send("all client connect me", 8991);
//                    Thread.sleep(2000);
//                    new NetUdpThread(8991).send("all client connect me", 8991);
//                    Thread.sleep(2000);
//                    new NetUdpThread(8991).send("all client connect me", 8991);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        Integer count = (Integer) SPUtils.getShare
                (mContext,Constant.CONNECT_COUNT,0);
        LogUtils.i("wake up system connect count = " + count);
        ConcurrentHashMap concurrentHashMap =
                PhoneClientManager.getInstance().getmHaspMapPhoneClient();
        LogUtils.i("wake up the map save connect size = " + concurrentHashMap.size());
//
        MultiServerSocket multiServerSocket = MultiServerSocket.getInstance();
        multiServerSocket.init();
        multiServerSocket.sendData();
    }

    /**
     * 读取天通休眠模块
     *
     * @return
     */
    public boolean getTianTongModeSleep() {
        try {

            File file = new File("/sys/bus/platform/drivers/tt-platdata/bp_mode");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String value = br.readLine().trim();
            LogUtils.d("getTianTongModeSleep value = " + value);
            if (value.equals("1")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 信号控制模块休眠
     */
    public void controlSignalStrength(int gsmSignalStrength) {

        if (gsmSignalStrength >= 0 && gsmSignalStrength < 97) {

            if (!getTianTongModeSleep()) {

                LogUtils.i("controlSignalStrength control model go to sleep");

                sleepTianTong();
            }

        } else {

        }
    }

    /**
     * 释放
     */
    public void free() {
        mContext.unregisterReceiver(broadcastReceiver);
        MultiServerSocket.getInstance().recycle();
    }

}
