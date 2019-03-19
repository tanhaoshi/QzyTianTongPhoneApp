package com.qzy.tiantong.service.utils;


import com.qzy.tiantong.lib.utils.LogUtils;

public class PowerControl {
    private static boolean inDoSleep = false;
    private static boolean inDoWakeup = false;

    private static Thread sleepThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (ModuleDormancyUtil.getNodeString(Constant.WAKE_PATH).equals("0")){
                //挂断状态下 要去睡眠
                ModuleDormancyUtil.writeNode(Constant.WAKE_PATH,"0");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    private static Thread wakeupThread = new Thread(new Runnable() {
        @Override
        public void run() {
            if (ModuleDormancyUtil.getNodeString(Constant.WAKE_PATH).equals("1")){
                //挂断状态下 要去睡眠
                ModuleDormancyUtil.writeNode(Constant.WAKE_PATH,"1");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    });
    private static boolean sTTStatus;

    public static synchronized void doSleep(){
        LogUtils.i("Notify Tiantong goto Sleep");
        if(!inDoSleep){
            sleepThread.start();
            inDoSleep = true;
            inDoWakeup = false;
        }
    }

    public static synchronized void doWakeup() {
        LogUtils.i("Notify Tiantong goto Wakeup");
        if(!inDoWakeup){
            wakeupThread.start();
            inDoWakeup = true;
            inDoSleep = false;
        }
    }

    public static boolean getTTStatus() {
        sTTStatus = ModuleDormancyUtil.getNodeString(Constant.WAKE_PATH).equals("0");
        if(!sTTStatus)
            LogUtils.i("Current Tiantong is Sleeping");
        else{
            LogUtils.i("Current Tiantong is Wakeup");
        }
        return sTTStatus;
    }
}
