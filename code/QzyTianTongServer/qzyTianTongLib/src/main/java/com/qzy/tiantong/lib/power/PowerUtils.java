package com.qzy.tiantong.lib.power;


public final class PowerUtils {

    //唤醒天通猫模块
    public static byte[] wakeup_command = new byte[]{(byte) 0xAA, (byte) 0x55,(byte) 0x01,(byte)0x04};

    //休眠天通猫模块
    public static byte[] sleep_command = new byte[]{(byte) 0xAA, (byte) 0x55,(byte) 0x01,(byte)0x05};

    //休眠系统
    public static byte[] sleep_system = new byte[]{(byte)0xAA, (byte)0x55 ,(byte)0x01 ,(byte)0x08};


    public static byte[] check_tt_mode_sleep = new byte[]{(byte)0xAA, (byte)0x55 ,(byte)0x01 ,(byte)0x06};

    //重启rial库
    public static byte[] recover_rial = new byte[]{(byte)0xAA,(byte)0x55,(byte)0x01,(byte)0x09};


    public static byte[] wakeupCommand() {
        return wakeup_command;
    }

    public static byte[] sleepCommand() {
        return sleep_command;
    }

    public static byte[] sleepSystemCommand(){
        return sleep_system;
    }

    public static byte[] checkTtModeCommand(){
        return check_tt_mode_sleep;
    }

    public static byte[] recoverRial(){
        return recover_rial;
    }
}
