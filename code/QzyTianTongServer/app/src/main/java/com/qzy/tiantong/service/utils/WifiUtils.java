package com.qzy.tiantong.service.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.qzy.tiantong.lib.utils.LogUtils;
import com.qzy.tiantong.lib.utils.QzySystemUtils;

import java.lang.reflect.Method;

/**
 * Created by yj.zhang on 2018/7/9/009.
 */

public class WifiUtils {
    // wifi热点开关
    public static boolean setWifiApEnabled(Context context, String ssid, String passwd, boolean enabled) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (enabled) { // disable WiFi in any case
                //wifi和热点不能同时打开，所以打开热点的时候需要关闭wifi
                wifiManager.setWifiEnabled(false);
            }

            //热点的配置类
            WifiConfiguration apConfig = new WifiConfiguration();
            //配置热点的名称(可以在名字后面加点随机数什么的)
            apConfig.SSID = ssid;
            //配置热点的密码
            apConfig.preSharedKey = passwd;
            LogUtils.e("ssid = " + ssid + " passwd = " + passwd);
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


    public static String getSsidName() {
        /*String ssid = getSsidToSharedpref();
        if (TextUtils.isEmpty(ssid)) {
            ssid = QzyTtContants.WIFI_SSID + QzySystemUtils.getEmmcId();
            setSsidToSharedpref(ssid);
        }*/
        String ssid = Constant.WIFI_SSID + QzySystemUtils.getEmmcId();
        return ssid;
    }

    public static void setSsidToSharedpref(Context context,String ssid) {
        SharedPreferences sp = context.getSharedPreferences("tt_server_config", Context.MODE_PRIVATE);
        sp.edit().putString("wifi_ssid", ssid).commit();
    }

    public static String getSsidToSharedpref(Context context) {
        SharedPreferences sp = context.getSharedPreferences("tt_server_config", Context.MODE_PRIVATE);
        String ssid = sp.getString("wifi_ssid", "");
        return ssid;
    }

    public static void setWifiPasswdToSharedpref(Context context,String passwd) {
        SharedPreferences sp = context.getSharedPreferences("tt_server_config", Context.MODE_PRIVATE);
        sp.edit().putString("wifi_passwd", passwd).commit();
    }

    public static String getWifiPasswdToSharedpref(Context context) {
        SharedPreferences sp = context.getSharedPreferences("tt_server_config", Context.MODE_PRIVATE);
        String passwd = sp.getString("wifi_passwd", "");
        return passwd;
    }


}
