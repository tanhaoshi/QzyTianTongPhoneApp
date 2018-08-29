package com.tt.qzy.view.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

/**
 * Created by qzy009 on 2018/8/28.
 */

public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    /**
     * 判断移动网络是否开启
     *
     * @param context
     * @return
     */
    public static boolean isNetEnabled(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null&&networkInfo.length>0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.i(TAG, "移动网络已经开启");
                        return true;
                    }
                }
            }
        }
        Log.i(TAG, "移动网络还未开启");
        return false;
    }

    /**
     * 判断WIFI网络是否开启
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        WifiManager wm = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wm != null && wm.isWifiEnabled()) {
            Log.i(TAG, "Wifi网络已经开启");
            return true;
        }
        Log.i(TAG, "Wifi网络还未开启");
        return false;
    }

    /**
     * 判断移动网络是否连接成功
     *
     * @param context
     * @return
     */
    public static boolean isNetContected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (cm != null && info != null && info.isConnected()) {
            Log.i(TAG, "移动网络连接成功");
            return true;
        }
        Log.i(TAG, "移动网络连接失败");
        return false;
    }

    /**
     * 判断WIFI是否连接成功
     *
     * @param context
     * @return
     */
    public static boolean isWifiContected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null && info.isConnected()) {
            Log.i(TAG, "Wifi网络连接成功");
            return true;
        }
        Log.i(TAG, "Wifi网络连接失败");
        return false;
    }

    public static String getConnectWifiSsid(Context context){
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.d("wifiInfo", wifiInfo.toString());
        Log.d("SSID",wifiInfo.getSSID());
        return wifiInfo.getSSID();
    }
}
