package com.qzy.tt.phone.common;

/**
 * Created by yj.zhang on 2018/7/31/031.
 */

public class CommonData {

    public static String localWifiIp ;

    public static boolean isConnected = false;

    public static boolean isStartRecorder = false;

    public static void relase(){
        isConnected = false;
        isStartRecorder = false;
        localWifiIp = null;
    }

}
