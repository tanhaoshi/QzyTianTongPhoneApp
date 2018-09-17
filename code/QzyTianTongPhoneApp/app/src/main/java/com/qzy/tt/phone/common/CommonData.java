package com.qzy.tt.phone.common;

/**
 * Created by yj.zhang on 2018/9/17.
 */

public class CommonData {
    private static CommonData instance;
    //是否连接天通
    private boolean isConnected = false;

    //本机ip
    private String localWifiIp;

    public static CommonData getInstance() {
        if (instance == null) {
            instance = new CommonData();
        }
        return instance;
    }

    private CommonData() {

    }

    /**
     * 获取天同连接状态
     *
     * @return
     */
    public boolean isConnected() {
        return isConnected;
    }


    /**
     * 设置天通连接状态
     *
     * @param connected
     */
    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    /**
     * 获取本机ip
     * @return
     */
    public String getLocalWifiIp() {
        return localWifiIp;
    }

    /**
     * 设置本机ip
     * @param localWifiIp
     */
    public void setLocalWifiIp(String localWifiIp) {
        this.localWifiIp = localWifiIp;
    }

    /**
     * 释放资源
     */
    public void free() {
        instance = null;
    }


}
