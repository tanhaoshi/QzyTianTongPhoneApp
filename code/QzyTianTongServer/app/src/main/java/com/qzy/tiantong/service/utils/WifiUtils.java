package com.qzy.tiantong.service.utils;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.lang.reflect.Method;

/**
 * Created by yj.zhang on 2018/7/9/009.
 */

public class WifiUtils {
    // wifi热点开关
    public static boolean setWifiApEnabled(Context context, boolean enabled) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (enabled) { // disable WiFi in any case
                //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
                wifiManager.setWifiEnabled(false);
            }

            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = "QzyTTWifi_zyj";
            //配置热点的密码
            apConfig.preSharedKey = "12345678";
            apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            //通过反射调用设置热点
            Method method = wifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, Boolean.TYPE);
            //返回热点打开状态
            return (Boolean) method.invoke(wifiManager, apConfig, enabled);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
