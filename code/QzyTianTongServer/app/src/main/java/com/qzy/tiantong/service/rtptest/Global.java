package com.qzy.rtptest;

public class Global {
    public static boolean sendToSelf = false;
    public static RtpManager rtpManager = null;
    public static boolean ok = false;
    public static String IP = "192.168.1.3";
    public static int PROT = 6000;

    public static void free() {
        sendToSelf = false;
        RtpManager rtpManager = null;
        boolean ok = false;

        String ip = "192.168.1.3";
        int port = 6000;
    }

}
