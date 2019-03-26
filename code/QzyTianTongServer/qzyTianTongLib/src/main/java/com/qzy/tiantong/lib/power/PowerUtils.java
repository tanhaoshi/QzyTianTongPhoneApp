package com.qzy.tiantong.lib.power;


public final class PowerUtils {

    //唤醒天通猫模块
    public static byte[] wakeup_command = new byte[]{(byte) 0xAA, (byte) 0x55,(byte) 0x01,(byte)0x04};

    //休眠天通猫模块
    public static byte[] sleep_command = new byte[]{(byte) 0xAA, (byte) 0x55,(byte) 0x01,(byte)0x05};


    public static byte[] wakeupCommand() {
        return wakeup_command;
    }

    public static byte[] sleepCommand() {
        return sleep_command;
    }
}
