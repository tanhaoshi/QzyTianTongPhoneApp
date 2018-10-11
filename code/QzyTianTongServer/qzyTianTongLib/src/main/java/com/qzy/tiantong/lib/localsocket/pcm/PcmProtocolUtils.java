package com.qzy.tiantong.lib.localsocket.pcm;


import com.qzy.tiantong.lib.utils.ByteUtils;

public class PcmProtocolUtils {

    public static byte[] control_command_head = new byte[]{(byte) 0xAA, (byte) 0x55};


    private static byte[] combinCommand(byte[] data) {
        byte[] command = new byte[data.length + 3];
        command[0] = control_command_head[0];
        command[1] = control_command_head[1];
        command[2] = ByteUtils.intToByte(data.length);
        System.arraycopy(data, 0, command, 3, data.length);
        return command;
    }

    public static byte[] sendPhoneCalling() {
        byte[] command = new byte[2];
        command[0] = (byte) 0x02;
        command[1] = (byte) 0x01;
        return combinCommand(command);
    }

    public static byte[] sendPhoneHangup() {
        byte[] command = new byte[2];
        command[0] = (byte) 0x02;
        command[1] = (byte) 0x00;
        return combinCommand(command);
    }

    public static byte[] sendIpAndPort(byte[] data) {
        byte[] command = new byte[7];
        command[0] = (byte) 0x01;
        System.arraycopy(data, 0, command, 1, data.length);
        return combinCommand(command);
    }


}
